package wacc

/* TODOs:
   [ ] Make error messages more descriptive (e.g. function, variable, etc.)
   
   */

object Errors {

  class NotDefinedError(
    position: Position, 
    val message: String
  ) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Not defined: ", Seq(message))
  }

  class AlreadyDefinedError(
    position: Position, 
    val message: String
  ) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Already defined: ", Seq(message))
  }

  // For repeated catches of the same exception
  class ExceptionAlreadyCaughtError(
    position: Position, 
    val message: String
  ) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Exception already caught: ", Seq("Exception already caught: ", message))
  }

  // Given expected type and actual type
  class TypeMismatchError(
    position: Position, 
    val expectedType: String,
    val actualType: String
  ) extends SemanticError(position, Seq(expectedType ++ actualType)) {
    override def getErrMessage() = 
      (s"Type mismatch: expected type $expectedType " +
       s"but found type $actualType", Seq(expectedType ++ actualType))
  }

  // Array elements should all be of the same type, given the List[String]
  class ArrayElemTypeError(
    position: Position, 
    val types: List[String]
  ) extends SemanticError(position, types) {
    override def getErrMessage() = 
      (s"Type mismatch: array elements should all be of the same type, got ${types.mkString(", ")}", 
      Seq(types.mkString(", ")))
  }

  // Array index should be of type int, given the actual type
  class ArrayIndexError(
    position: Position, 
    val msg: String
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = (s"Type mismatch: expected int, got $msg", Seq(msg))
  }

  // Array access too deep
  class ArrayDepthError(
    position: Position, 
    val msg: String
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = (s"Array access too deep: ${msg}", Seq(msg))
  }

  // Type mismatch cannot assign to function
  // MIGHT NOT NEED THIS
  class FunctionAssignError(
    position: Position, 
    val message: String
  ) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Type mismatch: cannot assign to function", Seq(message))
  }

  // Type mismatch for read statements
  class ReadError(
    position: Position, 
    val msg: String
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = (s"Type mismatch: expected int or char, got $msg", Seq(msg))
  }

  // Type mismatch for free statements, expectede pair or array
  class FreeError(
    position: Position, 
    val msg: String
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = (s"Type mismatch: expected pair or array, got $msg", Seq(msg))
  }

  // Return outside of function not permitted
  class ReturnError(
    position: Position, 
    val msg: String = "Return outside of function not permitted"
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = (msg, Seq(msg))
  }

  // Return type mismatch
  class ReturnTypeMismatchError(
    position: Position, 
    val expectedType: String,
    val actualType: String
  ) extends SemanticError(position, Seq(expectedType ++ actualType)) {
    override def getErrMessage() = 
      (s"Type mismatch: function return expected type $expectedType " +
       s"but found type $actualType", Seq(expectedType ++ actualType))
  }

  // Expected two of the same type but found two different types
  class TypeMatchError(
    position: Position, 
    val type1: String,
    val type2: String
  ) extends SemanticError(position, Seq(type1 ++ type2)) {
    override def getErrMessage() = 
      (s"Type mismatch: expected two of the same type, got $type1 and $type2", 
       Seq(type1 ++ type2))
  }

  // Exit statement requires int argument
  class ExitError(
    position: Position, 
    val msg: String
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = (s"Type mismatch: expected int, got $msg", Seq(msg))
  }

  // Print statement expected a type but found another
  class PrintError(
    position: Position, 
    val msg: String
  ) extends SemanticError(position, Seq(msg)) {
    override def getErrMessage() = 
      (s"Type mismatch: expected int, bool, char, or string, got $msg", Seq(msg))
  }

  // Expected number of arguments for function but found another
  class ArgNumError(
    position: Position, 
    val expected: Int,
    val actual: Int
  ) extends SemanticError(position, Seq(expected.toString ++ actual.toString)) {
    override def getErrMessage() = 
      (s"Argument number mismatch: expected $expected arguments, got $actual", 
       Seq(expected.toString ++ actual.toString))
  }

  // Expected type of arguments for function but found another
  class ArgTypeError(
    position: Position, 
    val expected: String,
    val actual: String
  ) extends SemanticError(position, Seq(expected ++ actual)) {
    override def getErrMessage() = 
      (s"Argument type mismatch: expected $expected but got $actual", 
       Seq(expected ++ actual))
  }

  class ScopeError(
    position: Position, 
    val message: String
  ) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Out of scope: ", Seq(message))
  }

  abstract class SemanticError(val position: Position, val messages: Seq[String]) extends Throwable {
    def getPos(): (Int, Int) = position.pos
    def getErrMessage(): (String, Seq[String]) = ("Semantic Error", messages)
    def formatError(): String = {
      val (errType, details) = getErrMessage()
      val (line, col) = (getPos()._1, getPos()._2)
      val sb = new StringBuilder()
      sb.append("    " + errType + " at (line " + line + ", col " + col + ")" + ":\n")
      for (detail <- details) {
        sb.append("        " + detail + "\n")
      }
      sb.toString()
    }
    def generateError(): String = formatError()
    override def getMessage(): String = generateError()
  }

  type ErrorInfoLines = Seq[String]
}