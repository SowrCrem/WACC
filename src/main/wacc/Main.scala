package wacc

import parsley.{Success, Failure}
import sys.process._

/* TODOs:
   [ ] Use Scala built-in file management instead of shell command
   [ ] Add common error printer; and extract to I/O file, NOT main
   [ ] Remove magic numbers   

   */

object Main {
  def main(args: Array[String]): Unit = {
    val exitCode = compile(args)
    sys.exit(exitCode)
  }

  def syntaxError(msg: String = ""): Int = {
    println("Syntax analysis output: " + msg)
    100
  }

  def semanticCheck(node: Position): Int = {
    semanticChecker.check(node) match {
      case Right(exitCode) => {
        0
      }
      case Left(msg) => {
        println("Semantic analysis output: \n" + msg)
        200
      }
    }
  }

  def compile(args: Array[String]): Int = args.headOption match {
    case Some(filename) => {
      // val fileContent = ("cat " + filename).!!
      var fileContent = ""
      try {
        fileContent = scala.io.Source.fromFile(filename).mkString
      } catch {
        case e: java.io.FileNotFoundException => {
          println("IO Error: File not found")
          return -1
        }
      }
      parser.parse(fileContent) match {
        case Success(node) => node match {
          case Program(funcList, _) => parser.validFunctions(funcList) match {
            case true  => semanticCheck(node)
            case false => syntaxError("Non-terminating branches found")
          }
          case _ => syntaxError("Program is not Well-Formed")
        }
        case Failure(msg) => syntaxError(msg)
      }
    }
    case None => {
      println("IO Error: No file path provided")
      -1
    }
  }
}