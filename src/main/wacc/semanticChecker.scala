package wacc

object semanticChecker {
  // Initialise top-level symbol table with no parent
  private val topLevelST: symbolTable = new symbolTable(None) 

  // Should ideallly input a program node - fix parser output? s
  // TODO: Should parse always return a program node?
  def check(program: Node): Either[String, Int] = {
    // Initialise topLevelST with global scope
    initialiseTopLevelST()


    // TODO: Return value from semantic check
    topLevelST.semanticCheck(program)

    // TODO: Alter return value
    return Right(0)

    // If semantic error:
    // return Left("error message")
  }
  
  // Initialise top-level symbol table dictionary entries
  private def initialiseTopLevelST(): Unit = {
    // Add predefined types to top-level symbol table
    topLevelST.add("int", IntTypeNode())
    topLevelST.add("bool", BoolTypeNode())
    topLevelST.add("char", CharTypeNode())
    topLevelST.add("string", StringTypeNode())

    // TODO: Would I need to add pair and array types to top-level symbol table?

    // topLevelST.add("pair", PairTypeNode(new PairElemTypeNode, PairElemTypeNode))
    // topLevelST.add("array", ArrayTypeNode(TypeNode))
  }
}