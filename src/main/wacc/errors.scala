package wacc

object Errors {

  case class AlreadyDefinedError(position: Position, val message: String) extends ScopeError(position, message) {
    override def getErrMessage() = ("Already Defined Error", Seq(message))
  }

  class TypeError(position: Position, val message: String) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Type Error", Seq(message))
  }

  class ScopeError(position: Position, val message: String) extends SemanticError(position, Seq(message)) {
    override def getErrMessage() = ("Scope Error", Seq(message))
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
    override def generateError(): String = formatError()
    override def getMessage(): String = generateError()
  }

  type ErrorInfoLines = Seq[String]
}