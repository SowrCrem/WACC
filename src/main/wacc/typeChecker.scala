package wacc

class SemanticError(val message: String) extends Throwable {
  override def getMessage: String = message
}

class TypeChecker(initialSymbolTable: SymbolTable) {

  val errors: List[SemanticError] = List()

  def check(node: Node): Option[TypeNode] = {
    check(node, initialSymbolTable, None)
  }

  def check(
      node: Node,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[TypeNode] = node match {
    case Program(funcList, stat) =>
      println(Program(funcList, stat))
      funcList.foreach {
        case func @ Func(typeNode, ident, paramList, stat) => {
          (symbolTable.lookupAll(ident.value + "_f", Some(symbolTable))) match {
            case (Some(_)) => {
              throw new SemanticError(
                s"Function ${ident.value} already defined"
              )
            }
            case _ => {
              symbolTable.add(
                ident.value + "_f",
                Func(typeNode, ident, paramList, stat)
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
              throw new SemanticError(
                s"Parameter ${param.ident.value} already defined"
              )
            }
            newSymbolTable.add(param.ident.value, param.typeNode)
          })
          symbolTable.add(ident.value, Func(typeNode, ident, paramList, stat))
          check(stat, newSymbolTable, Some(typeNode))
          Some(typeNode)
        case _ => {
          throw new SemanticError(
            s"Function ${ident.value} is not defined, SHOULD NOT REACH"
          )
        }
      }
    case IdentAsgn(typeNode, ident, expr) =>
      val exprType = check(expr, symbolTable, returnType)

      symbolTable.lookup(ident.value) match {
        case Some(t) =>
          t match {
            case Func(_, _, _, _) =>
              symbolTable.add(ident.value, typeNode)
            case _ =>
              symbolTable.add(ident.value, typeNode)
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
                  symbol = PairTypeNode(expectedX, PairTypeNode(a, b))
                  true
                }
                case (PairTypeNode(a, b), Null()) => {
                  symbol = PairTypeNode(PairTypeNode(a, b), expectedX)
                  true
                }
                case (a, b) => a == b
                case _      => false
              }
              val yCompat = (y, expectedY) match {
                case (Null(), PairTypeNode(a, b)) => {
                  symbol = PairTypeNode(a, b)
                  true
                }
                case (PairTypeNode(_, _), Null()) => {
                  symbol = PairTypeNode(x, y)
                  true
                }
                case (a, b) => a == b
                case _      => false
              }
              if (xCompat && yCompat) {
                symbolTable.add(ident.value, symbol)
                None
              } else {
                throw new SemanticError(
                  s"Type mismatch: Pair elements do not match expected types: expected $typeNode, got ${exprType.getOrElse("None").toString()}"
                )
              }
            }
          }
        }
        case _ =>
          if (
            (typeNode == StringTypeNode() && exprType == Some(
              ArrayTypeNode(CharTypeNode())
            ))
          ) {
            symbolTable.add(ident.value, symbol)
            None
          } else {
            if (expr != ArrayLiter(List()) && expr != Null() && exprType.getOrElse(None) != Null()) {
              throw new SemanticError(
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
          lhsType.getOrElse(IntLiter(0)),
          rhsType.getOrElse(IntLiter(0))
        ) && (rhs != Null())
      ) {
        println(lhs, rhs)
        if (rhsType.getOrElse(None) != None) {
          throw new SemanticError(
            s"Type mismatch: expected ${lhsType.getOrElse("None").toString()}, got ${rhsType.getOrElse("None").toString()}"
          )
        }

      }

      (lhs) match {
        case Ident(value) => {
          (symbolTable.lookupAll(value, Some(symbolTable))) match {
            case Some(Func(_, _, _, _)) =>
              throw new SemanticError(
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
          throw new SemanticError(
            s"Type mismatch: expected int or char, got ${check(lhs, symbolTable, returnType)}"
          )
      }
    case Free(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(_, _)) | Some(ArrayTypeNode(_)) => None
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Free expected pair or array, got ${check(expr, symbolTable, returnType).getOrElse("None").toString()}"
          )
      }
    case Return(expr) =>
      if (returnType == None) {
        throw new SemanticError(
          s"Return statement outside of function is not allowed"
        )
      }
      val exprType = check(expr, symbolTable, returnType)

      if (
        exprType != returnType && !(compatiblePairTypes(
          exprType.getOrElse(IntLiter(0)),
          returnType.getOrElse(IntLiter(0))
        ))
      ) {

        throw new SemanticError(
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
          throw new SemanticError(
            s"Exit statement requires an int expression, got ${exprType.getOrElse(None).toString()}"
          )
      }
    case Print(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(t) =>
          None
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Print expected a value got ${exprType.getOrElse(None).toString()}"
          )
      }
    case Println(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(t) =>
          None
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Println a value, got ${exprType.getOrElse(None).toString()}"
          )
      }
    case If(cond, ifStat, elseStat) =>
      val condType = check(cond, symbolTable, returnType)
      if (condType != Some(BoolTypeNode())) {
        throw new SemanticError(
          s"Type mismatch: If condition must be boolean, got ${condType}"
        )
      }
      check(ifStat, symbolTable, returnType)
      check(elseStat, symbolTable, returnType)
      None
    case While(cond, stat) =>
      val condType = check(cond, symbolTable, returnType)
      if (condType != Some(BoolTypeNode())) {
        throw new SemanticError(
          s"Type mismatch: While loop condition must be a boolean, got ${condType}"
        )
      }
      check(stat, symbolTable, returnType)
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
          Some(PairTypeNode(fst, snd))
        case _ =>
          throw new SemanticError(
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
          throw new SemanticError(
            s"Expected ${expected.length} arguments, got ${argList.length}"
          )
        }
        val argTypes = argList.map(arg => check(arg, symbolTable, returnType))
        println(argTypes)

        argTypes.zip(expected).forall { case (argType, expectedType) =>
          argType == Some(expectedType) || argType == Some(Null())
        }

      }
      symbolTable.lookupAll(ident.value + "_f", Some(symbolTable)) match {
        case Some(Func(typeNode, _, paramList, _)) =>
          if (checkArgList(args.argList, paramList.paramList.map(_.typeNode))) {
            Some(typeNode)
          } else {
            throw new SemanticError(
              s"Type mismatch: Call expected ${paramList.paramList
                  .map(_.typeNode)}, got ${args.argList.map(arg => check(arg, symbolTable, returnType))}"
            )
          }
        case _ =>
          throw new SemanticError(
            s"Function ${ident.value + "_f"} not found or arg count doesnt match"
          )
      }
    case ArrayLiter(exprList) =>
      val exprType = exprList.map(expr => check(expr, symbolTable, returnType))
      if (exprType.distinct.length == 1) {
        Some(ArrayTypeNode(exprType.head.get))
      } else if (exprType.distinct.length == 0) {
        None
      } else {
        // need to allow weakening of char[] into string
        if (
          exprType.distinct.length == 2 && exprType.contains(
            Some(ArrayTypeNode(CharTypeNode()))
          ) && exprType.contains(Some(StringTypeNode()))
        ) {
          Some(ArrayTypeNode(StringTypeNode()))
        } else
          throw new SemanticError(
            s"Type mismatch: ArrayLiter expected all elements to be of the same type"
          )
      }
    case FstNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(fst, _)) => Some(fst)
        case _ =>
          throw new SemanticError(
            s"Type mismatch: fst expected a pair, got ${check(expr, symbolTable, returnType).getOrElse("None").toString()}"
          )
      }
    case SndNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(_, snd)) => Some(snd)
        case _ =>
          throw new SemanticError(
            s"Type mismatch: SndNode expected a pair, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case ArrayElem(ident, eList) =>
      symbolTable.lookupAll(ident.value, Some(symbolTable)) match {
        case Some(node) =>
          node match {
            case ArrayTypeNode(elementType) =>
              // Check if all indices are integers
              val allIndicesAreInt = eList.forall(expr =>
                check(expr, symbolTable, returnType) == Some(IntTypeNode())
              )
              if (!allIndicesAreInt) {
                throw new SemanticError(
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
                      case ArrayTypeNode(t)                 => ArrayTypeNode(t)
                      case t =>
                        throw new SemanticError(
                          s"Array access too deep"
                        )
                    }
                  )
                  Some(node)
                }
              }
            case _ =>
              throw new SemanticError(
                s"Type mismatch: ${ident.value} is not an array"
              )
          }
        case None =>
          throw new SemanticError(s"Identifier ${ident.value} not found")
      }
    case Mul(lhs, rhs) =>
      checkArithmBinOp(lhs, rhs, symbolTable, returnType)
    case Div(lhs, rhs) =>
      checkArithmBinOp(lhs, rhs, symbolTable, returnType)
    case Mod(lhs, rhs) =>
      checkArithmBinOp(lhs, rhs, symbolTable, returnType)
    case Plus(lhs, rhs) =>
      checkArithmBinOp(lhs, rhs, symbolTable, returnType)
    case Minus(lhs, rhs) =>
      checkArithmBinOp(lhs, rhs, symbolTable, returnType)
    case GreaterThan(lhs, rhs) =>
      checkCompBinOp(lhs, rhs, symbolTable, returnType)
    case GreaterThanEq(lhs, rhs) =>
      checkCompBinOp(lhs, rhs, symbolTable, returnType)
    case LessThan(lhs, rhs) =>
      checkCompBinOp(lhs, rhs, symbolTable, returnType)
    case LessThanEq(lhs, rhs) =>
      checkCompBinOp(lhs, rhs, symbolTable, returnType)
    case Equals(lhs, rhs) => {
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)
      if (
        lhsType == rhsType || rhsType == Some(Null()) || lhsType == Some(Null())
      ) {
        Some(BoolTypeNode())
      } else {
        throw new SemanticError(
          s"Type mismatch: Equals expected two of the same type, got ${lhsType} and ${rhsType}"
        )
      }
    }
    case NotEquals(lhs, rhs) => {
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)
      if (
        lhsType == rhsType || rhsType == Some(Null()) || lhsType == Some(Null())
      ) {
        Some(BoolTypeNode())
      } else {
        throw new SemanticError(
          s"Type mismatch: Equals expected two of the same type, got ${lhsType} and ${rhsType}"
        )
      }
    }
    case And(lhs, rhs) =>
      checkBoolBinOp(lhs, rhs, symbolTable, returnType)
    case Or(lhs, rhs) =>
      checkBoolBinOp(lhs, rhs, symbolTable, returnType)
    case Not(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(BoolTypeNode()) => Some(BoolTypeNode())
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Not expected a boolean, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Neg(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(IntTypeNode())
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Neg expected an integer, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Len(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(ArrayTypeNode(_)) => Some(IntTypeNode())
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Len expected an array, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Ord(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(CharTypeNode()) => Some(IntTypeNode())
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Ord expected a char, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Chr(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(CharTypeNode())
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Chr expected an int, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case IntLiter(_)    => Some(IntTypeNode())
    case CharLiter(_)   => Some(CharTypeNode())
    case BoolLiter(_)   => Some(BoolTypeNode())
    case StringLiter(_) => Some(StringTypeNode())
    case Ident(value) =>
      symbolTable.lookupAll(value, Some(symbolTable)) match {
        case Some(t) =>
          t match {
            case (Func(typeNode, _, _, _)) => Some(typeNode)
            case _                         => Some(t.asInstanceOf[TypeNode])
          }
        case None =>
          throw new SemanticError(
            s"identifier does not exist: ${value}"
          )
      }
    case Brackets(expr) => check(expr, symbolTable, returnType)
    case Null()         => Some(Null())
    case Skip()         => None
    case x =>
      throw new SemanticError(s"Type checking not implemented for $x")
  }

  def compatiblePairTypes(
      pair1: Node,
      pair2: Node
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
                compatiblePairTypes(PairTypeNode(a, b), PairTypeNode(c, d))
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
                compatiblePairTypes(PairTypeNode(a, b), PairTypeNode(c, d))
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
      lhs: Expr,
      rhs: Expr,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[BoolTypeNode] = {
    val lhsType = check(lhs, symbolTable, returnType)
    val rhsType = check(rhs, symbolTable, returnType)
    if (lhsType == Some(BoolTypeNode()) && rhsType == Some(BoolTypeNode())) {
      Some(BoolTypeNode())
    } else {
      throw new SemanticError(
        s"Type mismatch: Binary bool operator expected two booleans, got ${lhsType
            .getOrElse("None")
            .toString()} and ${rhsType.getOrElse("None").toString()}"
      )
    }
  }

  def checkArithmBinOp(
      lhs: Expr,
      rhs: Expr,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[IntTypeNode] = {
    val lhsType = check(lhs, symbolTable, returnType)
    val rhsType = check(rhs, symbolTable, returnType)
    if (lhsType == Some(IntTypeNode()) && rhsType == Some(IntTypeNode())) {
      Some(IntTypeNode())
    } else {
      throw new SemanticError(
        s"Type mismatch: Binary arithmetic operator expected two integers, got ${lhsType
            .getOrElse("None")
            .toString()} and ${rhsType.getOrElse("None").toString()}"
      )
    }
  }

  def checkCompBinOp(
      lhs: Expr,
      rhs: Expr,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[BoolTypeNode] = {
    val lhsType = check(lhs, symbolTable, returnType)
    val rhsType = check(rhs, symbolTable, returnType)

    if (lhsType == rhsType) {
      if (
        (lhsType == Some(IntTypeNode()) || lhsType == Some(
          CharTypeNode()
        )) && (rhsType == Some(IntTypeNode()) || rhsType == Some(
          CharTypeNode()
        ))
      ) {
        return Some(BoolTypeNode())
      } else {
        throw new SemanticError(
          s"Type mismatch: Binary comparison operator expected two integers or two chars, got ${lhsType
              .getOrElse("None")
              .toString()} and ${rhsType.getOrElse("None").toString()}"
        )
      }
    } else {
      throw new SemanticError(
        s"Type mismatch: expected two of the same type, got ${lhsType} and ${rhsType}"
      )
    }
  }

}
