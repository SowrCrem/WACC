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

  val sep = java.io.File.separator
  var backendTests = false

  def setBackendTests(): Unit = backendTests = true

  def inRootDir = new java.io.File("src").exists()

  def constructPath(paths: List[String]): String = paths.mkString(sep)

  def parentDirPath(path: String): String = constructPath(List("..", path))

  def getFilename(path: String): String = path.split(sep).last.split('.').head

  def adaptedPath(path: String): String = {
    var newPath = constructPath(List("test", "wacc", path))
    if (!inRootDir) { newPath = parentDirPath(newPath) }
    newPath
  }

  def main(args: Array[String]): Unit = {
    val exitCode = compile(args)
    sys.exit(exitCode)
  }

  def syntaxError(msg: String = ""): Int = {
    println("Syntax analysis output: " + msg)
    100
  }

  def saveGeneratedCode(prog: Program, fileName: String = "X86Code"): Unit = {
    val content = X86CodeGenerator.generate(prog)
    var filename = fileName + ".s"
    if (!inRootDir) { filename = parentDirPath(filename) }
    val file = new java.io.File(filename)
    val path = file.getAbsolutePath()
    val writer = new PrintWriter(new java.io.FileOutputStream(file, false))
    writer.write(content)
    writer.close()
  }

  def semanticCheck(prog: Program, fileName: String): Int = {
    semanticChecker.check(prog) match {
      case Right(exitCode) => {
        if (backendTests || inRootDir ) { saveGeneratedCode(prog, fileName) }
        0
      }
      case Left(msg) => {
        println("Semantic analysis output: \n" + msg)
        200
      }
    }
  }

  def compile(args: Array[String]): Int = synchronized(args.headOption match {
    case Some(filepath) => {
      val fileName = getFilename(filepath)
      var fileContent = ""
      try {
        fileContent = scala.io.Source.fromFile(filepath).mkString
      } catch {
        case e: java.io.FileNotFoundException => {
          println("IO Error: File not found")
          sys.exit(-1)
        }
      }
      parser.parse(fileContent) match {
        case Success(prog) => prog match {
          case Program(funcList, _) => parser.validFunctions(funcList) match {
            case true  => semanticCheck(prog, fileName)
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