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
      typeChecker.check(position)

      return Right(0)
    } catch {
      case e: Throwable => return Left(e.getMessage())
    }
  }

}