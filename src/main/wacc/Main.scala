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
  val EXIT_SUCCESS = 0
  val SYNTAX_ERR_CODE = 100
  val SEMANTIC_ERR_CODE = 200
  val UNDEFINED_EXIT = -1
  
  def setBackendTests(): Unit = backendTests = true

  def ROOT_DIR = new java.io.File("src").exists()

  def parentDirPath(path: String): String = ".." + java.io.File.separator + path

  def main(args: Array[String]): Unit = {
    val exitCode = compile(args)
    sys.exit(exitCode)
  }

  def syntaxError(msg: String = ""): Int = {
    println("Syntax analysis output: " + msg)
    SYNTAX_ERR_CODE
  }

  def semanticCheck(prog: Program, fileName: String): Int = {
    semanticChecker.check(prog) match {
      case Right(exitCode) => {
        if (backendTests || ROOT_DIR ) {saveGeneratedCode(prog, fileName) }
        EXIT_SUCCESS
      }
      case Left(msg) => {
        println("Semantic analysis output: \n" + msg)
        SEMANTIC_ERR_CODE
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

  // TODO: REMOVE MAGIC NUMBERS nd Deal with any general uncaught exceptions with a top-level try
  def compile(args: Array[String]): Int = synchronized(args.headOption match {
    case Some(filename) => {
      val fileName = filename.split(java.io.File.separator).last.split('.').head
      var fileContent = ""
      try {
        val fileBuffer = scala.io.Source.fromFile(filename)
        fileContent = try fileBuffer.mkString finally fileBuffer.close()
      } catch {
        case e: java.io.FileNotFoundException => {
          println("IO Error: File not found") 
          return UNDEFINED_EXIT
        }
      }
      macroPreProcessor.preprocess(fileContent) match {
        case Right(macroSubstitutedFileContent) => {
          fileContent = macroSubstitutedFileContent
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
        case Left(msg) => syntaxError(msg)
      }
    }
    case None => {
      println("IO Error: No file path provided")
      UNDEFINED_EXIT
    }
  })
}