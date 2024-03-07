package wacc

/* TODOs:
   [ ] Match on typeChecker.check() output instead of sequential 

   */

object semanticChecker {

  var topLevelSymbolTable: SymbolTable = new SymbolTable(None)
  var typeChecker: TypeChecker = new TypeChecker(topLevelSymbolTable)

  def check(position: Position): Either[String, Int] =  {
    // Check that no exception is thrown for a valid type check
    try {
      typeChecker.check(position) match {
        case Left(errorList) => {
          // Print all the errors
          // errorList.foreach(error => println(error.getMessage()))
          println(errorList.head.getMessage())
          println("As well as " + (errorList.length - 1) + " other semantic error(s) found")
          Left("Semantic error(s) found")
        }
        case Right(_)   => return Right(0)
      }
    } catch {
      // Should not reach this case after refactoring
      case e: Throwable => return Left(e.getMessage())
      
    }
  }

  def reset() : Unit = {
    topLevelSymbolTable = new SymbolTable(None)
    typeChecker = new TypeChecker(topLevelSymbolTable)
  }
}