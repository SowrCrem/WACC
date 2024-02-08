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
      funcList.foreach(func => check(func, symbolTable, None))
      check(stat, symbolTable, None)
      None // Program has no type
    case Func(typeNode, ident, paramList, stat) =>
      val newSymbolTable = symbolTable.enterScope()
      paramList.paramList.foreach(param =>
        newSymbolTable.add(param.ident.value, param.typeNode)
      )
      check(stat, newSymbolTable, Some(typeNode))
      symbolTable.add(ident.value, Func(typeNode, ident, paramList, stat))
      Some(typeNode)
    case IdentAsgn(typeNode, ident, expr) =>
      val exprType = check(expr, symbolTable, returnType)
      (exprType) match {
        case Some(t) if t == typeNode => None
        case _ =>
          throw new SemanticError(
            s"Type mismatch: expected $typeNode, got $exprType"
          )
      }
      symbolTable.add(ident.value, typeNode)
      Some(typeNode)

    case AsgnEq(lhs, rhs) =>
      val lhsType = check(lhs, symbolTable, returnType)
      val rhsType = check(rhs, symbolTable, returnType)
      if (lhsType != rhsType) {
        throw new SemanticError(
          s"Type mismatch: expected $lhsType, got $rhsType"
        )
      }
      None
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
            s"Type mismatch: expected pair or array, got ${check(expr, symbolTable, returnType)}"
          )
      }
    case Return(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      if (exprType != returnType) {
        throw new SemanticError(
          s"Type mismatch: expected $returnType, got $exprType"
        )
      }
      None
    case Exit(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(IntTypeNode()) => None
        case _ =>
          throw new SemanticError(
            s"Exit statement requires an int expression, got ${exprType}"
          )
      }
    case Print(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(IntTypeNode()) | Some(CharTypeNode()) | Some(
              StringTypeNode()
            ) =>
          None
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Print expected int, char or string, got ${exprType}"
          )
      }
    case Println(expr) =>
      val exprType = check(expr, symbolTable, returnType)
      exprType match {
        case Some(IntTypeNode()) | Some(CharTypeNode()) | Some(
              StringTypeNode()
            ) =>
          None
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Println expected int, char or string, got ${exprType}"
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
      // ensure that fstType and sndType conform to PairElemTypeNode
      (fstType, sndType) match {
        case (Some(fst: PairElemTypeNode), Some(snd: PairElemTypeNode)) =>
          Some(PairTypeNode(fst, snd))
        case _ =>
          throw new SemanticError(
            s"Type mismatch: NewPair expected pair elements, got ${fstType} and ${sndType}"
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
        argTypes.zip(expected).forall { case (argType, expectedType) =>
          argType == Some(expectedType)
        }
      }
      symbolTable.lookupAll(ident.value, Some(symbolTable)) match {
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
            s"Function ${ident.value} not found or arg count doesnt match"
          )
      }
    case ArrayLiter(exprList) =>
      val exprType = exprList.map(expr => check(expr, symbolTable, returnType))
      if (exprType.distinct.length == 1) {
        Some(ArrayTypeNode(exprType.head.get))
      } else {
        throw new SemanticError(
          s"Type mismatch: ArrayLiter expected all elements to be of the same type, got ${exprType}"
        )
      }
    case FstNode(expr) =>
      check(expr, symbolTable, returnType) match {
        case Some(PairTypeNode(fst, _)) => Some(fst)
        case _ =>
          throw new SemanticError(
            s"Type mismatch: FstNode expected a pair, got ${check(expr, symbolTable, returnType)}"
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
        case Some(ArrayTypeNode(t)) =>
          val allIndicesAreInt = eList.forall(expr =>
            check(expr, symbolTable, returnType).contains(IntTypeNode())
          )
          if (allIndicesAreInt) {
            Some(t)
          } else {
            throw new SemanticError(
              s"Type mismatch: ArrayElem expected all indices to be integers"
            )
          }
        case _ =>
          throw new SemanticError(
            s"Type mismatch: ArrayElem expected an array, got ${symbolTable
                .lookup(ident.value)}"
          )
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
    case Equals(lhs, rhs) =>
      checkCompBinOp(lhs, rhs, symbolTable, returnType)
    case NotEquals(lhs, rhs) =>
      checkCompBinOp(lhs, rhs, symbolTable, returnType)
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
        case _ =>
          throw new SemanticError(
            s"Type mismatch: Ident expected a type, got ${symbolTable.lookup(value)}"
          )
      }
    case Brackets(expr) => check(expr, symbolTable, returnType)
    case x =>
      throw new SemanticError(s"Type checking not implemented for $x")
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
        s"Type mismatch: expected two booleans, got ${lhsType} and ${rhsType}"
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
      Some(BoolTypeNode())
    } else {
      throw new SemanticError(
        s"Type mismatch: expected two of the same type, got ${lhsType} and ${rhsType}"
      )
    }
  }

}
