package wacc

object Errors {

    case class SemanticError(position: Position, val message: String) extends Throwable with Error {
        override def getPos() = position
        override def getErrMessage() = ("Semantic Error", Seq(message))
        def formatError(fileName: String): String = {
            val (errType, details) = getErrMessage()
            val (line, col) = (getPos()._1, getPos()._2)
            val sb = new StringBuilder()
            sb.append("    " + errType + " at (line " + line +", col " + col + ")"  + ":\n")
            for (detail <- details) {
                sb.append("        " + detail + "\n")
            }
            sb.toString()
        }
        override def generateError() : String = formatError()
        override def getMessage(): String = generateError()
    }

    case class TypeError (position: Position, val message: String) extends Throwable with Error {
        override def getPos() = position
        override def getErrMessage() = ("Type Error", Seq(message))
        def formatError(fileName: String): String = {
            val (errType, details) = getErrMessage()
            val (line, col) = (getPos()._1, getPos()._2)
            val sb = new StringBuilder()
            sb.append("    " + errType + " at (line " + line +", col " + col + ")"  + ":\n")
            for (detail <- details) {
                sb.append("        " + detail + "\n")
            }
            sb.toString()
        }
        
        override def generateError() : String = formatError()
        override def getMessage(): String = generateError()
    }

    case class ScopeError (position: Position, val message: String) extends Throwable with Error {
        override def getPos() = position
        override def getErrMessage() = ("Scope Error", Seq(message))
        def formatError(fileName: String): String = {
            val (errType, details) = getErrMessage()
            val (line, col) = (getPos()._1, getPos()._2)
            val sb = new StringBuilder()
            sb.append("    " + errType + " at (line " + line +", col " + col + ")"  + ":\n")
            for (detail <- details) {
                sb.append("        " + detail + "\n")
            }
            sb.toString()
        }
        
        override def generateError() : String = formatError()
        override def getMessage(): String = generateError()
    }

    type ErrorInfoLines = Seq[String]
    type Position = (Int, Int)

    trait Error {
        def getPos(): Position
        def getErrMessage(): (String, Seq[String])
        def formatError(): String = {
            val (errType, details) = getErrMessage()
            val (line, col) = (getPos()._1, getPos()._2)
            val sb = new StringBuilder()
            sb.append(errType + " at (line " + line +", col " + col + ")"  + ":\n")
            for (detail <- details) {
                sb.append(detail + "\n")
            }
            sb.toString()
        }
        def generateError() : String = formatError()
    }
}


