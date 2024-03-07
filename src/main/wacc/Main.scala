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

  var backendTests = false

  def setBackendTests(): Unit = backendTests = true

  def ROOT_DIR = new java.io.File("src").exists()

  def parentDirPath(path: String): String = ".." + java.io.File.separator + path

  def main(args: Array[String]): Unit = {
    val exitCode = compile(args)
    sys.exit(exitCode)
  }

  def syntaxError(msg: String = ""): Int = {
    println("Syntax analysis output: " + msg)
    100
  }

  def semanticCheck(prog: Program, fileName: String): Int = {
    semanticChecker.check(prog) match {
      case Right(exitCode) => {
        if (backendTests || ROOT_DIR ) {saveGeneratedCode(prog, fileName) }
        0
      }
      case Left(msg) => {
        println("Semantic analysis output: \n" + msg)
        200
      }
    }
  }

  def saveGeneratedCode(prog: Program, fileName: String = "X86Code"): Unit = {
    val content = X86CodeGenerator.generate(prog)
    var filename = fileName + ".s"
    if (!ROOT_DIR) { filename = parentDirPath(filename) }
    val file = new java.io.File(filename)
    val path = file.getAbsolutePath()
    val writer = new PrintWriter(new java.io.FileOutputStream(file, false)) // false to overwrite existing contents
    writer.write(content)
    writer.close()
  }

  def compile(args: Array[String]): Int = synchronized(args.headOption match {
    case Some(filename) => {
      val fileName = filename.split(java.io.File.separator).last.split('.').head
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
          case prog@Program(funcList, _) => parser.validFunctions(funcList) match {
            case true  => {
              semanticCheck(prog, fileName)
            }
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