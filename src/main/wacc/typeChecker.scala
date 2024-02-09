package wacc

import wacc.Errors.SemanticError
import wacc.Errors.TypeError
import wacc.Errors.ScopeError

class TypeChecker(initialSymbolTable: SymbolTable) {

  val errors: List[SemanticError] = List()

  def check(position: Position): Option[TypeNode] = {
    check(position, initialSymbolTable, None)
  }

  def check(
      position: Position,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[TypeNode] = position match {
    case Program(funcList, stat) =>
      println(Program(funcList, stat)(position.pos))
      funcList.foreach {
        case func @ Func(typeNode, ident, paramList, stat) => {
          (symbolTable.lookupAll(ident.value + "_f", Some(symbolTable))) match {
            case (Some(_)) => {
              throw new SemanticError(position.pos, s"Function ${ident.value} already defined")
            }
            case _ => {
              symbolTable.add(
                ident.value + "_f",
                Func(typeNode, ident, paramList, stat)(position.pos)
              )
            }
          }
        }
      }

      funcList.foreach { func =>
        {
          check(func, symbolTable, None)
        }
      }
      check(stat, symbolTable, None)
      None // Program has no type
    case Func(typeNode, ident, paramList, stat) =>
      (symbolTable.lookupAll(ident.value + "_f", Some(symbolTable))) match {
        case Some(_) =>
          val newSymbolTable = symbolTable.enterScope()
          paramList.paramList.foreach(param => {
            if (
              newSymbolTable
                .lookupAll(param.ident.value, Some(newSymbolTable)) != None
            ) {
              throw new SemanticError(position.pos, 
                s"Parameter ${param.ident.value} already defined"
              )
            }
            newSymbolTable.add(param.ident.value, param.typeNode)
          })
          symbolTable.add(ident.value, Func(typeNode, ident, paramList, stat)(position.pos))
          check(stat, newSymbolTable, Some(typeNode))
          Some(typeNode)
        case _ => {
          throw new SemanticError(position.pos, 
            s"Function ${ident.value} is not defined, SHOULD NOT REACH"
          )
        }
      }
    case IdentAsgn(typeNode, ident, expr) =>
      val exprType = check(expr, symbolTable, returnType)

      println(ident.value)
      println(expr)
      println(symbolTable.lookup(ident.value))
      symbolTable.lookup(ident.value) match {
        case Some(t) =>
          t match {
            case Func(_, _, _, _) =>
              symbolTable.add(ident.value, typeNode)
            case _  => {
              throw new ScopeError(position.pos, 
                s"Identifier ${ident.value} already defined"
              )
            }
          }
        case None => {
          symbolTable.add(ident.value, typeNode)
        }
      }

      var symbol: TypeNode = typeNode
      (exprType) match {
        case Some(t) if t == typeNode => {
          symbolTable.add(ident.value, symbol)
          None
        }
        case Some(PairTypeNode(x, y)) => {
          typeNode match {
            case PairTypeNode(expectedX, expectedY) => {
              val xCompat = (x, expectedX) match {
                case (Null(), PairTypeNode(a, b)) => {
                  symbol = PairTypeNode(expectedX, PairTypeNode(a, b)(position.pos))(position.pos)
                  true
                }
                case (PairTypeNode(a, b), Null()) => {
                  symbol = PairTypeNode(PairTypeNode(a, b)(position.pos), expectedX)(position.pos)
                  true
                }
                case (a, b) => a == b
                case _      => false
              }
              val yCompat = (y, expectedY) match {
                case (Null(), PairTypeNode(a, b)) => {
                  symbol = PairTypeNode(a, b)(position.pos)
                  true
                }
                case (PairTypeNode(_, _), Null()) => {
                  symbol = PairTypeNode(x, y)(position.pos)
                  true
                }
                case (a, b) => a == b
                case _      => false
              }
              if (xCompat && yCompat) {
                symbolTable.add(ident.value, symbol)
                None
              } else {
                throw new TypeError(position.pos, 
                  s"Type mismatch: Pair elements do not match expected types: expected $typeNode, got ${exprType.getOrElse("None").toString()}"
                )
              }
            }
          }
        }
        case _ =>
          if (
            (typeNode == StringTypeNode()(position.pos) && exprType == Some(
              ArrayTypeNode(CharTypeNode()(position.pos))(position.pos)
            ))
          ) {
            symbolTable.add(ident.value, symbol)
            None
          } else {
            if (expr != ArrayLiter(List())(position.pos) && expr != Null()(position.pos) && exprType.getOrElse(None) != Null()(position.pos)) {
              throw new TypeError(position.pos, 
                s"Type mismatch: expected $typeNode, got ${exprType.getOrElse("None").toString()}"
              )
            } else {
              symbolTable.add(ident.value, symbol)
              None
            }
          }

      }
      None
    case AsgnEq(lhs, rhs) =>
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)
      if (
        lhsType != rhsType && !compatiblePairTypes(
          lhsType.getOrElse(IntLiter(0)(position.pos)),
          rhsType.getOrElse(IntLiter(0)(position.pos))
        , position) && (rhs != Null()(position.pos))
      ) {
        println(lhs, rhs)
        if (rhsType.getOrElse(None) != None) {
          throw new TypeError(position.pos, 
            s"Type mismatch: expected ${lhsType.getOrElse("None").toString()}, got ${rhsType.getOrElse("None").toString()}"
          )
        }

      }

      (lhs) match {
        case Ident(value) => {
          (symbolTable.lookupAll(value, Some(symbolTable))) match {
            case Some(Func(_, _, _, _)) =>
              throw new TypeError(position.pos, 
                s"Type mismatch: cannot assign to function"
              )
            case _ =>
              None
          }
        }
        case _ => None
      }

    case Read(lhs) =>
      check(lhs, symbolTable, returnType) match {
        case Some(IntTypeNode()) | Some(CharTypeNode()) => None
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: expected int or char, got ${check(lhs, symbolTable, returnType)}"
          )
      }
    case Free(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(_, _)) | Some(ArrayTypeNode(_)) => None
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: Free expected pair or array, got ${check(expr, symbolTable, returnType).getOrElse("None").toString()}"
          )
      }
    case Return(expr) =>
      if (returnType == None) {
        throw new SemanticError(position.pos, 
          s"Return statement outside of function is not allowed"
        )
      }
      val exprType = check(expr, symbolTable, returnType)

      if (
        exprType != returnType && !(compatiblePairTypes(
          exprType.getOrElse(IntLiter(0)(position.pos)),
          returnType.getOrElse(IntLiter(0)(position.pos))
        , position))
      ) {

        throw new TypeError(position.pos, 
          s"Type mismatch: Function expected to return ${returnType
              .getOrElse("None")
              .toString()}, got ${exprType.getOrElse("None").toString()}"
        )
      }
      None
    case Exit(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(IntTypeNode()) => None
        case _ =>
          throw new SemanticError(position.pos, 
            s"Exit statement requires an int expression, got ${exprType.getOrElse(None).toString()}"
          )
      }
    case Print(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(t) =>
          None
        case _ =>
          throw new TypeError(position.pos, s"Type mismatch: Print expected a value got ${exprType.getOrElse(None).toString()}")
      }
    case Println(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(t) =>
          None
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: Println a value, got ${exprType.getOrElse(None).toString()}"
          )
      }
    case If(cond, ifStat, elseStat) =>
      val newSymbolTableTrue = symbolTable.enterScope()
      val newSymbolTableFalse = symbolTable.enterScope()
      val condType = check(cond, symbolTable, returnType)
      if (condType != Some(BoolTypeNode()(position.pos))) {
        throw new TypeError(position.pos,
          s"Type mismatch: If condition must be boolean, got ${condType}"
        )
      }
      check(ifStat, newSymbolTableTrue, returnType)
      check(elseStat, newSymbolTableFalse, returnType)
      None
    case While(cond, stat) =>
      val newSymbolTable = symbolTable.enterScope()
      val condType = check(cond, symbolTable, returnType)
      if (condType != Some(BoolTypeNode()(position.pos))) {
        throw new TypeError(position.pos,
          s"Type mismatch: While loop condition must be a boolean, got ${condType}"
        )
      }
      check(stat, newSymbolTable, returnType)
      None
    case BeginEnd(stat) =>
      val newSymbolTable = symbolTable.enterScope()
      val result = check(stat, newSymbolTable, returnType)
      result
    case StatJoin(statList) =>
      statList.foreach(stat => check(stat, symbolTable, returnType))
      None
    case NewPair(fst, snd) =>
      val fstType = check(fst, symbolTable, returnType)
      val sndType = check(snd, symbolTable, returnType)
      (fstType, sndType) match {
        case (Some(fst: PairElemTypeNode), Some(snd: PairElemTypeNode)) =>
          Some(PairTypeNode(fst, snd)(position.pos))
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: NewPair expected pair elements, got ${fstType
                .getOrElse("None")
                .toString()} and ${sndType.getOrElse("None").toString()}"
          )
      }
    case Call(ident, args) =>
      def checkArgList(
          argList: List[Expr],
          expected: List[TypeNode]
      ): Boolean = {
        if (argList.length != expected.length) {
          throw new SemanticError(position.pos, 
            s"Expected ${expected.length} arguments, got ${argList.length}"
          )
        }
        val argTypes = argList.map(arg => check(arg, symbolTable, returnType))
        println(argTypes)

        argTypes.zip(expected).forall { case (argType, expectedType) =>
          argType == Some(expectedType) || argType == Some(Null()(position.pos))
        }

      }
      symbolTable.lookupAll(ident.value + "_f", Some(symbolTable)) match {
        case Some(Func(typeNode, _, paramList, _)) =>
          if (checkArgList(args, paramList.paramList.map(_.typeNode))) {
            Some(typeNode)
          } else {
            throw new TypeError(position.pos, 
              s"Type mismatch: Call expected ${paramList.paramList
                  .map(_.typeNode)}, got ${args.map(arg => check(arg, symbolTable, returnType))}"
            )
          }
        case _ =>
          throw new SemanticError(position.pos, 
            s"Function ${ident.value + "_f"} not found or arg count doesnt match"
          )
      }
    case ArrayLiter(exprList) =>
      val exprType = exprList.map(expr => check(expr, symbolTable, returnType))
      if (exprType.distinct.length == 1) {
        Some(ArrayTypeNode(exprType.head.get)(position.pos))
      } else if (exprType.distinct.length == 0) {
        None
      } else {
        // need to allow weakening of char[] into string
        if (
          exprType.distinct.length == 2 && exprType.contains(
            Some(ArrayTypeNode(CharTypeNode()(position.pos))(position.pos))
          ) && exprType.contains(Some(StringTypeNode()(position.pos)))
        ) {
          Some(ArrayTypeNode(StringTypeNode()(position.pos))(position.pos))
        } else
          throw new TypeError(position.pos, 
            s"Type mismatch: ArrayLiter expected all elements to be of the same type"
          )
      }
    case FstNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(fst, _)) => Some(fst)
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: fst expected a pair, got ${check(expr, symbolTable, returnType).getOrElse("None").toString()}"
          )
      }
    case SndNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(_, snd)) => Some(snd)
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: SndNode expected a pair, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case ArrayElem(ident, eList) =>
      symbolTable.lookupAll(ident.value, Some(symbolTable)) match {
        case Some(positionl) =>
          positionl match {
            case ArrayTypeNode(elementType) =>
              // Check if all indices are integers
              val allIndicesAreInt = eList.forall(expr =>
                check(expr, symbolTable, returnType) == Some(IntTypeNode()(position.pos))
              )
              if (!allIndicesAreInt) {
                throw new TypeError(position.pos, 
                  "Type mismatch: ArrayElem expected all indices to be integers"
                )
              } else {
                // For a single-dimensional array accessed once, return the element type.
                // For multi-dimensional arrays or deeper accesses, we need to peel off the array layers accordingly.
                if (eList.length == 1) {
                  Some(elementType)
                } else {
                  val arrType = eList.foldLeft(elementType)((acc, _) =>
                    acc match {
                      case ArrayTypeNode(t) => t
                      case t                => t
                    }
                  )
                  val node = eList.foldLeft(elementType)((acc, _) =>
                    acc match {
                      case ArrayTypeNode(t) if t != arrType => t
                      case ArrayTypeNode(t)                 => ArrayTypeNode(t)(position.pos)
                      case t =>
                        throw new SemanticError(position.pos, 
                          s"Array access too deep"
                        )
                    }
                  )
                  Some(node)
                }
              }
            case _ =>
              throw new TypeError(position.pos, 
                s"Type mismatch: ${ident.value} is not an array"
              )
          }
        case None =>
          throw new ScopeError(position.pos, s"Identifier ${ident.value} not found")
      }
    case Mul(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case Div(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case Mod(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case Plus(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case Minus(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case GreaterThan(lhs, rhs) =>
      checkCompBinOp(position, lhs, rhs, symbolTable, returnType)
    case GreaterThanEq(lhs, rhs) =>
      checkCompBinOp(position, lhs, rhs, symbolTable, returnType)
    case LessThan(lhs, rhs) =>
      checkCompBinOp(position, lhs, rhs, symbolTable, returnType)
    case LessThanEq(lhs, rhs) =>
      checkCompBinOp(position, lhs, rhs, symbolTable, returnType)
    case Equals(lhs, rhs) => {
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)
      if (
        lhsType == rhsType || rhsType == Some(Null()(position.pos)) || lhsType == Some(Null()(position.pos))
      ) {
        Some(BoolTypeNode()(position.pos))
      } else {
        throw new TypeError(position.pos, 
          s"Type mismatch: Equals expected two of the same type, got ${lhsType} and ${rhsType}"
        )
      }
    }
    case NotEquals(lhs, rhs) => {
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)
      if (
        lhsType == rhsType || rhsType == Some(Null()(position.pos)) || lhsType == Some(Null()(position.pos))
      ) {
        Some(BoolTypeNode()(position.pos))
      } else {
        throw new TypeError(position.pos, 
          s"Type mismatch: Equals expected two of the same type, got ${lhsType} and ${rhsType}"
        )
      }
    }
    case And(lhs, rhs) =>
      checkBoolBinOp(position, lhs, rhs, symbolTable, returnType)
    case Or(lhs, rhs) =>
      checkBoolBinOp(position, lhs, rhs, symbolTable, returnType)
    case Not(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(BoolTypeNode()) => Some(BoolTypeNode()(position.pos))
        case _ =>
          throw new TypeError(position.pos,
            s"Type mismatch: Not expected a boolean, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Neg(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(IntTypeNode()(position.pos))
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: Neg expected an integer, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Len(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(ArrayTypeNode(_)) => Some(IntTypeNode()(position.pos))
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: Len expected an array, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Ord(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(CharTypeNode()) => Some(IntTypeNode()(position.pos))
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: Ord expected a char, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Chr(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(CharTypeNode()(position.pos))
        case _ =>
          throw new TypeError(position.pos, 
            s"Type mismatch: Chr expected an int, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case IntLiter(_)    => Some(IntTypeNode()(position.pos))
    case CharLiter(_)   => Some(CharTypeNode()(position.pos))
    case BoolLiter(_)   => Some(BoolTypeNode()(position.pos))
    case StringLiter(_) => Some(StringTypeNode()(position.pos))
    case Ident(value) =>
      symbolTable.lookupAll(value, Some(symbolTable)) match {
        case Some(t) =>
          t match {
            case (Func(typeNode, _, _, _)) => Some(typeNode)
            case _                         => Some(t.asInstanceOf[TypeNode])
          }
        case None =>
          throw new ScopeError(position.pos, 
            s"identifier does not exist: ${value}"
          )
      }
    case Brackets(expr) => check(expr, symbolTable, returnType)
    case Null()         => Some(Null()(position.pos))
    case Skip()         => None
    case x =>
      throw new SemanticError(position.pos, s"Type checking not implemented for $x")
  }

  def compatiblePairTypes(
      pair1: Position,
      pair2: Position,
      position: Position
  ): Boolean = {
    pair1 match {
      case PairTypeNode(x, y) =>
        pair2 match {
          case PairTypeNode(expectedX, expectedY) => {
            val xCompat = (x, expectedX) match {
              case (Null(), PairTypeNode(a, b)) => {
                true
              }
              case (PairTypeNode(a, b), Null()) => {
                true
              }
              case (PairTypeNode(a, b), PairTypeNode(c, d)) =>
                compatiblePairTypes(PairTypeNode(a, b)(position.pos), PairTypeNode(c, d)(position.pos), position)
              case (a, b) => a == b

              case _ => false
            }
            val yCompat = (y, expectedY) match {
              case (Null(), PairTypeNode(a, b)) => {
                true
              }
              case (PairTypeNode(_, _), Null()) => {
                true
              }
              case (PairTypeNode(a, b), PairTypeNode(c, d)) =>
                compatiblePairTypes(PairTypeNode(a, b)(position.pos), PairTypeNode(c, d)(position.pos), position)
              case (a, b) => a == b
              case _      => false
            }
            xCompat && yCompat
          }
          case _ => false
        }
      case _ => false
    }
  }

  def checkBoolBinOp(
      position: Position,
      lhs: Expr,
      rhs: Expr,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[BoolTypeNode] = {
    val lhsType = check(lhs, symbolTable, returnType)
    val rhsType = check(rhs, symbolTable, returnType)
    if (lhsType == Some(BoolTypeNode()(position.pos)) && rhsType == Some(BoolTypeNode()(position.pos))) {
      Some(BoolTypeNode()(position.pos))
    } else {
      throw new TypeError(position.pos, 
        s"Type mismatch: Binary bool operator expected two booleans, got ${lhsType
            .getOrElse("None")
            .toString()} and ${rhsType.getOrElse("None").toString()}"
      )
    }
  }

  def checkArithmBinOp(
      position: Position,
      lhs: Expr,
      rhs: Expr,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[IntTypeNode] = {
    val lhsType = check(lhs, symbolTable, returnType)
    val rhsType = check(rhs, symbolTable, returnType)
    if (lhsType == Some(IntTypeNode()(position.pos)) && rhsType == Some(IntTypeNode()(position.pos))) {
      Some(IntTypeNode()(position.pos))
    } else {
      throw new TypeError(position.pos, 
        s"Type mismatch: Binary arithmetic operator expected two integers, got ${lhsType
            .getOrElse("None")
            .toString()} and ${rhsType.getOrElse("None").toString()}"
      )
    }
  }

  def checkCompBinOp(
      position: Position,
      lhs: Expr,
      rhs: Expr,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[BoolTypeNode] = {
    val lhsType = check(lhs, symbolTable, returnType)
    val rhsType = check(rhs, symbolTable, returnType)

    if (lhsType == rhsType) {
      if (
        (lhsType == Some(IntTypeNode()(position.pos)) || lhsType == Some(
          CharTypeNode()(position.pos)
        )) && (rhsType == Some(IntTypeNode()(position.pos)) || rhsType == Some(
          CharTypeNode()(position.pos)
        ))
      ) {
        return Some(BoolTypeNode()(position.pos))
      } else {
        throw new TypeError(position.pos, 
          s"Type mismatch: Binary comparison operator expected two integers or two chars, got ${lhsType
              .getOrElse("None")
              .toString()} and ${rhsType.getOrElse("None").toString()}"
        )
      }
    } else {
      throw new TypeError(position.pos, 
        s"Type mismatch: expected two of the same type, got ${lhsType} and ${rhsType}"
      )
    }
  }

}
