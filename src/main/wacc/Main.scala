package wacc

import parsley.{Success, Failure}
import sys.process._
import java.io.PrintWriter
import scala.annotation.varargs

/* TODO:
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

  def semanticCheck(prog: Program): Int = {
    semanticChecker.check(prog) match {
      case Right(exitCode) => {
        saveGeneratedCode(prog)
        0
      }
      case Left(msg) => {
        println("Semantic analysis output: \n" + msg)
        200
      }
    }
  }

  def saveGeneratedCode(prog: Program): Unit = {
    val content = X86CodeGenerator.generate(prog)
    val file = new java.io.File(".." + java.io.File.separator + "X86Code.s")
    val writer = new PrintWriter(new java.io.FileOutputStream(file, false)) // false to overwrite existing contents
    writer.write(content)
    writer.close()
  }

  def compile(args: Array[String]): Int = synchronized(args.headOption match {
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
        case Success(prog) => prog match {
          case Program(funcList, _) => parser.validFunctions(funcList) match {
            case true  => semanticCheck(prog)
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
  })
}