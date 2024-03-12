package wacc

import wacc.Errors._
import scala.collection.mutable.ListBuffer

class TypeChecker(var initialSymbolTable: SymbolTable) {

  var errors: ListBuffer[SemanticError] = ListBuffer()

  def check(position: Position): Either[ListBuffer[SemanticError], SymbolTable] = {
    check(position, initialSymbolTable, None)

    if (errors.isEmpty) {
      Right(initialSymbolTable)
    } else { 
      Left(errors)
    } 
  
  }

  // Refactor check return type
  def check(
      position: Position,
      symbolTable: SymbolTable,
      returnType: Option[TypeNode]
  ): Option[TypeNode] = position match {
    // PROGRAMS
    case prog@Program(funcList, statList) => //checkProgram(position, funcList, stat)
      {


        funcList.foreach {
          case func@Func(typeNode, ident, paramList, statList) => {
            symbolTable.lookupAll(ident.value + "_f", Some(symbolTable)) match {
              case Some(_) => {
                errors += new AlreadyDefinedError(position, ident.value)
              }
              case None => symbolTable.add(ident.value + "_f", func)
            }
          }
        }

        // Check functions
        funcList.foreach(func => check(func, symbolTable, None))
        // Check statements
        statList.foreach(stat => check(stat, symbolTable, None))


        // Set the symbol table of the program to the current (root) symbol table
        prog.symbolTable = symbolTable

        None // Program has no type


      }
    
    // FUNCTIONS
    case func@Func(typeNode, ident, paramList, statList) =>
      {
        symbolTable.lookupAll(ident.value + "_f", Some(symbolTable)) match {
        case Some(_) =>
          val newSymbolTable = symbolTable.enterScope()
          paramList.paramList.foreach(param => {
            newSymbolTable.lookupAll(param.ident.value, Some(newSymbolTable)) match {
              case Some(_) =>
                errors += new AlreadyDefinedError(position, param.ident.value)
              case None =>
                param.typeNode.isParam = true;
                param.ident.typeNode = param.typeNode
                newSymbolTable.add(param.ident.value, param.typeNode)
            }
          })
          symbolTable.add(ident.value, func)

          // Set the symbol table of the function to the new (scoped) symbol table

          // Check statements
          statList.foreach(stat => check(stat, newSymbolTable, Some(typeNode)))
          func.symbolTable = newSymbolTable

          Some(typeNode)
        case None => errors += new NotDefinedError(position, ident.value)
          None
      }
    }

    // VARIABLE DECLARATIONS
    case IdentAsgn(typeNode, ident, rvalue) => {
      
      // Check rvalue (expr)
      val exprType = check(rvalue, symbolTable, returnType)

      symbolTable.lookup(ident.value) match {
        case Some(t) =>
          t match {
            case Func(_, _, _, _) =>
              ident.typeNode = typeNode
              symbolTable.add(ident.value, typeNode)
            case x if x != typeNode =>
              ident.typeNode = typeNode
              symbolTable.add(ident.value, typeNode)
            case _  => errors += new AlreadyDefinedError(position, ident.value)
          }
        case None => {
          ident.typeNode = typeNode
          symbolTable.add(ident.value, typeNode)
        }
      }

      var symbol: TypeNode = typeNode
      exprType match {
        // See if the type of the expression matches the type of the variable
        case Some(t) if t == typeNode => {
          symbol = ident.typeNode
          symbolTable.add(ident.value, symbol)
          None
        }
        case Some(PairTypeNode(x, y)) =>
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
                symbol = ident.typeNode //= symbol
                symbolTable.add(ident.value, symbol)
                None
              } else {
                errors += new TypeMismatchError(
                  position, 
                  typeNode.toString(), exprType.getOrElse("none").toString()
                )
              }
            }
          }
        
        case _ =>
          if (
            (typeNode == ArrayTypeNode(StringTypeNode()(position.pos))(position.pos) && 
            exprType == Some(
              ArrayTypeNode(CharTypeNode()(position.pos))(position.pos)
            ))
          ) {
            ident.typeNode = symbol
            symbolTable.add(ident.value, symbol)
            None
          } else {
            if (rvalue != ArrayLiter(List())(position.pos) && 
              rvalue != Null()(position.pos) && 
              exprType.getOrElse(None) != Null()(position.pos)) {
              errors += new TypeMismatchError(position, 
                typeNode.toString(), exprType.getOrElse(" none1").toString())
            } else {
              ident.typeNode = symbol
              symbolTable.add(ident.value, symbol)
              None
            }
          }
        
      }
      None
    }

    // EQUALITY ASSIGNMENTS
    case AsgnEq(lhs, rhs) =>
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)

      if (
        lhsType != rhsType && !compatiblePairTypes(
          lhsType.getOrElse(IntLiter(0)(position.pos)),
          rhsType.getOrElse(IntLiter(0)(position.pos)), 
          position) && (rhs != Null()(position.pos))
      ) {
        if (rhsType.getOrElse(None) != None) {
          errors += new TypeMismatchError(position, 
            lhsType.getOrElse("none").toString(), rhsType.getOrElse("none").toString())
        }
      }

      // We might not need this anymore - test variables asgneq with same name as function
      lhs match {
        case Ident(value) => {
          (symbolTable.lookupAll(value, Some(symbolTable))) match {
            case Some(Func(_, _, _, _)) =>
              errors += new FunctionAssignError(position, value)
              None
            case _ =>
              None
          }
        }
        
        case _ => None
      }

    // READ STATEMENTS
    case Read(lhs) =>
      check(lhs, symbolTable, returnType) match {
        case Some(IntTypeNode()) | Some(CharTypeNode()) => None
        case x =>
          errors += new ReadError(position, x.getOrElse("none").toString())
          None
      }

    // FREE STATEMENTS
    case Free(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(_, _)) | Some(ArrayTypeNode(_)) => None
        case x =>
          errors += new FreeError(position, x.getOrElse("none").toString())
          None
      }

    // RETURN STATEMENTS
    case Return(expr) =>
      if (returnType == None) {
        errors += new ReturnError(position)
      }
      val exprType = check(expr, symbolTable, returnType)

      if (
        exprType != returnType && !(compatiblePairTypes(
          exprType.getOrElse(IntLiter(0)(position.pos)),
          returnType.getOrElse(IntLiter(0)(position.pos))
        , position))
      ) {

        errors += new ReturnTypeMismatchError(position, 
          returnType.getOrElse("none").toString(), exprType.getOrElse("none").toString())
      }
      None

    // EXIT STATEMENTS
    case Exit(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(IntTypeNode()) => Some(IntTypeNode()(position.pos))
        case _ =>
          errors += new ExitError(position, exprType.getOrElse("none").toString())
          None
      }

    // PRINT STATEMENTS
    case Print(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(t) =>
          None
        case None =>
          errors += new PrintError(position, exprType.getOrElse("none").toString()) 
          None
      }

    // PRINTLN STATEMENTS
    case Println(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(t) =>
          None
        case _ =>
          errors += new PrintError(position, exprType.getOrElse("none").toString())
          None
      }


    // IF STATEMENTS
    case i@If(cond, ifStats, elseStats) =>
      val newSymbolTableTrue = symbolTable.enterScope()
      val newSymbolTableFalse = symbolTable.enterScope()
      val condType = check(cond, symbolTable, returnType)
      if (condType != Some(BoolTypeNode()(position.pos))) {
        errors += new TypeMismatchError(position, "bool", condType.getOrElse("none").toString())
      }

      ifStats.foreach(stat => check(stat, newSymbolTableTrue, returnType))
      elseStats.foreach(stat => check(stat, newSymbolTableFalse, returnType))

      i.symbolTableTrue = newSymbolTableTrue
      i.symbolTableFalse = newSymbolTableFalse
      None

    // WHILE STATEMENTS
    case w@While(cond, stats) =>
      val newSymbolTable = symbolTable.enterScope()
      val condType = check(cond, symbolTable, returnType)
      if (condType != Some(BoolTypeNode()(position.pos))) {
        errors += new TypeMismatchError(position, "bool", condType.getOrElse("none").toString())
      }


      stats.foreach(stat => check(stat, newSymbolTable, returnType))

      w.symbolTable = newSymbolTable
      None

    // BEGIN-END STATEMENTS
    case be@BeginEnd(stats) =>
      val newSymbolTable = symbolTable.enterScope()
      stats.foreach(stat => check(stat, newSymbolTable, returnType))

      be.symbolTable = newSymbolTable
      None


    // NEWPAIR STATEMENTS
    case NewPair(fst, snd) =>
      val fstType = check(fst, symbolTable, returnType)
      val sndType = check(snd, symbolTable, returnType)
      (fstType, sndType) match {
        case (Some(fst: PairElemTypeNode), Some(snd: PairElemTypeNode)) =>
          Some(PairTypeNode(fst, snd)(position.pos))
        case _ =>
          errors += new TypeMatchError(position, 
            fstType.getOrElse("none").toString(),
            sndType.getOrElse("none").toString()
          )
          None
      }
    
    // FUNCTION CALLS
    case Call(ident, args) =>
      def checkArgList(
          argList: List[Expr],
          expected: List[TypeNode]
      ): Boolean = {
        // Check if the number of arguments matches the number of parameters
        if (argList.length != expected.length) {
          errors += new ArgNumError(position, expected.length, argList.length)
          false
        }
        
        val argTypes = argList.map(arg => check(arg, symbolTable, returnType))

        argTypes.zip(expected).forall { 
          case (argType, expectedType) => 
            argType == Some(expectedType) || argType == Some(Null()(position.pos))
        }
      }

      symbolTable.lookupAll(ident.value + "_f", Some(symbolTable)) match {
        case Some(Func(typeNode, _, paramList, _)) =>
          val expectedTypes = paramList.paramList.map(_.typeNode)
          if (checkArgList(args, expectedTypes)) {
            Some(typeNode)
          } else {
            // Expected types do not match actual types
            var argTypes = args.map(arg => check(arg, symbolTable, returnType))
            errors += new ArgTypeError(position, 
              expectedTypes.map(_.toString()).mkString(", "), 
              argTypes.map(_.getOrElse("none").toString()).mkString(", ")
            )
            None
          }
        case _ =>
          // Function identifier not found
          errors += new NotDefinedError(position, ident.value)
          None
      }
    
    // ARRAY LITERAL
    case ArrayLiter(exprList) =>
      // Check if all elements are of the same type
      val exprType = exprList.map(expr => check(expr, symbolTable, returnType))

      if (exprType.distinct.length == 1) {
        val arrtype = ArrayTypeNode(exprType.head.get)(position.pos)
        arrtype.length = exprList.length
        Some(arrtype)
      } else if (exprType.distinct.length == 0) {
        None
      } else {
        // need to allow weakening of char[] into string
        if (
          exprType.distinct.length == 2 && exprType.contains(
            Some(ArrayTypeNode(CharTypeNode()(position.pos))(position.pos))
          ) && exprType.contains(Some(StringTypeNode()(position.pos)))
        ) {
          val arrtype = ArrayTypeNode(StringTypeNode()(position.pos))(position.pos)
          arrtype.length = exprList.length
          Some(arrtype)
        } else
          errors += new ArrayElemTypeError(position, 
            List(exprType.map(_.getOrElse("none").toString()).mkString(", "))
          )
          None
      }

    // FIRST PAIR ELEMENT of expr
    case FstNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(fst, _)) => Some(fst)
        case x =>
          errors += new TypeMismatchError(position, "pair type", x.getOrElse("none").toString())
          None
      }

    // SECOND PAIR ELEMENT of expr
    case SndNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(_, snd)) => Some(snd)
        case x =>
          errors += new TypeMismatchError(position, "pair type", x.getOrElse("none").toString())
          None
      }

    // ARRAY ELEMENT
    case arrElem@ArrayElem(ident, eList) =>
      symbolTable.lookupAll(ident.value, Some(symbolTable)) match {
        case Some(positionl) =>

          positionl match {
            case ArrayTypeNode(elementType) =>
              // Check if all indices are integers

              val allIndicesAreInt = eList.forall(expr =>
                check(expr, symbolTable, returnType) == Some(IntTypeNode()(position.pos))
              )
              if (!allIndicesAreInt) {
                // Expected all array indices to be integers
                errors += new ArrayIndexError(position, 
                  eList.map(expr => check(expr, symbolTable, returnType).getOrElse("none")).mkString(", ")
                )
                None
              } else {
                // For a single-dimensional array accessed once, return the element type.
                // For multi-dimensional arrays or deeper accesses, we need to peel off the array layers accordingly.
                if (eList.length == 1) {
                  arrElem.typeNode = elementType
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
                      case t => errors += new ArrayDepthError(position, t.toString())
                        t
                    }
                  )
                  arrElem.typeNode = node
                  printf("arr type is %s at linenumber %s\n", node, position.pos.toString())
                  printf("node is %s at linenumber %s\n", node, position.pos.toString())
                  Some(arrType)
                }
              }
            case _ =>
              // Expected an array
              errors += new TypeMismatchError(position, "array", positionl.toString())
              None
          }
        case None =>
          // Identifier not found#
          errors += new ScopeError(position, ident.value)
          None
      }

    // BINARY OPERATIONS
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
        // Expected same type
        errors += new TypeMatchError(position, 
          lhsType.getOrElse("none").toString(), rhsType.getOrElse("none").toString())
        None
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
        // Expected same type
        errors += new TypeMatchError(position, 
          lhsType.getOrElse("none").toString(), rhsType.getOrElse("none").toString())
        None
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
          // Expected boolean
          errors += new TypeMismatchError(position, 
            "bool", check(expr, symbolTable, returnType).getOrElse("none").toString())
          None
      }
    case Neg(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(IntTypeNode()(position.pos))
        case _ =>
          // Expected integer
          errors += new TypeMismatchError(position, 
            "int", check(expr, symbolTable, returnType).getOrElse("none").toString())
          None
      }
    case Len(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(ArrayTypeNode(_)) => Some(IntTypeNode()(position.pos))
        case _ =>
          // Expected array
          errors += new TypeMismatchError(position, 
            "array", check(expr, symbolTable, returnType).getOrElse("none").toString())
          None
      }
    case Ord(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(CharTypeNode()) => Some(IntTypeNode()(position.pos))
        case _ =>
          // Expected char
          errors += new TypeMismatchError(position, 
            "char", check(expr, symbolTable, returnType).getOrElse("none").toString())
          None
      }
    case Chr(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(CharTypeNode()(position.pos))
        case _ =>
          // Expected int
          errors += new TypeMismatchError(position, 
            "int", check(expr, symbolTable, returnType).getOrElse("none").toString())
          None
      }

    /* EXTENSION - Bitwise Operators */
    case BitAnd(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case BitOr(lhs, rhs) =>
      checkArithmBinOp(position, lhs, rhs, symbolTable, returnType)
    case BitNot(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(IntTypeNode()) => Some(IntTypeNode()(position.pos))
        case _ =>
          // Expected int
          errors += new TypeMismatchError(position, 
            "int", check(expr, symbolTable, returnType).getOrElse("none").toString())
          None
      }

    case IntLiter(_)    => Some(IntTypeNode()(position.pos))
    case CharLiter(_)   => Some(CharTypeNode()(position.pos))
    case BoolLiter(_)   => Some(BoolTypeNode()(position.pos))
    case StringLiter(_) => Some(StringTypeNode()(position.pos))
    case i@Ident(value) =>
      symbolTable.lookupAll(value, Some(symbolTable)) match {
        case Some(t) =>
          t match {
            case (Func(typeNode, _, _, _)) => Some(typeNode)
            case _                         => {
              i.typeNode = t.asInstanceOf[TypeNode]
              Some(t.asInstanceOf[TypeNode])
            }
          }
        case None =>
          // Identifier not found
          errors += new NotDefinedError(position, value)
          None
      }
    case b@Brackets(expr) =>{
      val x = check(expr, symbolTable, returnType)
      b.typeNode = x.getOrElse(StringTypeNode()(position.pos))
      x
    }
    case Null()         => Some(Null()(position.pos))
    case Skip()         => None
    case _ =>
      println("Type checking not implemented for " + position)
      None
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
      errors += new TypeMatchError(position, 
                                   lhsType.getOrElse("none").toString(), 
                                   rhsType.getOrElse("none").toString())
      
      None
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
      errors += new TypeMatchError(position, 
                                   lhsType.getOrElse("none").toString(), 
                                   rhsType.getOrElse("none").toString())
      
      None
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

    if ((lhsType == rhsType) &&
        (lhsType == Some(IntTypeNode()(position.pos)) || lhsType == Some(
          CharTypeNode()(position.pos)
        )) && (rhsType == Some(IntTypeNode()(position.pos)) || rhsType == Some(
          CharTypeNode()(position.pos)
        ))
    ) {
      return Some(BoolTypeNode()(position.pos))
    } else {
      errors += new TypeMatchError(position, 
                                    lhsType.getOrElse("none").toString(), 
                                    rhsType.getOrElse("none").toString())
      
      None
    }
  }
}
