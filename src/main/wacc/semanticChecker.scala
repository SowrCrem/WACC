package wacc

object semanticChecker {


  var symbolTable: SymbolTable = new SymbolTable(None)
  var typeChecker: TypeChecker = new TypeChecker(symbolTable)


  def check(node: Node): Either[String, Int] = {
    // Check that no exception is thrown for a valid type check
    try {
      typeChecker.check(node)
      return Right(0)
    } catch {
      case e: Throwable => return Left(e.getMessage)
    }
  }

  // Initialise top-level symbol table with no parent
  // private val topLevelST: SymbolTable = new SymbolTable(None) 

  // // Should ideallly input a program node - fix parser output? s
  // // TODO: Should parse always return a program node?
  // def check(program: Node): Either[String, Int] = {
  //   // Initialise topLevelST with global scope
  //   initialiseTopLevelST()


  //   // TODO: Return value from semantic check
  //   topLevelST.semanticCheck(program)

  //   // TODO: Alter return value
  //   return Right(0)

  //   // If semantic error:
  //   // return Left("error message")
  // }
  
  // // Initialise top-level symbol table dictionary entries
  // private def initialiseTopLevelST(): Unit = {
  //   // Add predefined types to top-level symbol table
  //   topLevelST.add("int", IntTypeNode())
  //   topLevelST.add("bool", BoolTypeNode())
  //   topLevelST.add("char", CharTypeNode())
  //   topLevelST.add("string", StringTypeNode())

  //   // TODO: Would I need to add pair and array types to top-level symbol table?

  //   // topLevelST.add("pair", PairTypeNode(new PairElemTypeNode, PairElemTypeNode))
  //   // topLevelST.add("array", ArrayTypeNode(TypeNode))
  // }
}