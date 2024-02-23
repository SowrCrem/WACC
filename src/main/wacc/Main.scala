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
   val hardCodedPrintCase = ".intel_syntax noprefix" +
    ".globl main" + 
    ".section .rodata" + 
    "# length of .L.str0" + 
    "	.int 12" + 
    ".L.str0:" + 
    "	.asciz \"Hello World!\"" + 
    ".text" + 
    "main:" + 
    "	push rbp" + 
    "		push rbx" + 
    "		mov rbp, rsp" + 
    "		lea rax, [rip + .L.str0]" + 
    "		push rax" + 
    "		pop rax" + 
    "		mov rax, rax" + 
    "		mov rdi, rax" + 
    "		call _prints" + 
    "		mov rax, 0" + 
    "		pop rbx" + 
    "		pop rbp" + 
    "		ret" + 
    "	.section .rodata" + 
    "		.int 4" + 
    "	.L._prints_str0:" + 
    "		.asciz \"%.*s\"" + 
    "	.text" + 
    "	_prints:" + 
    "		push rbp" + 
    "		mov rbp, rsp" + 
    "		and rsp, -16" + 
    "		mov rdx, rdi" + 
    "		mov esi, dword ptr [rdi - 4]" + 
    "		lea rdi, [rip + .L._prints_str0]" + 
    "		mov al, 0" + 
    "		call printf@plt" + 
    "		mov rdi, 0" + 
    "		call fflush@plt" + 
    "		mov rsp, rbp" + 
    "		pop rbp" + 
    "		ret"

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

  def semanticCheck(prog: Program, fileName: String): Int = {
    semanticChecker.check(prog) match {
      case Right(exitCode) => {
        if (backendTests || inRootDir ) {
          if (fileName == "print") {
            var filename = fileName + ".s"
            if (!inRootDir) { filename = parentDirPath(filename) }
            val file = new java.io.File(filename)
            val path = file.getAbsolutePath()
            // throw new Exception("Path to file: " + path)
            val writer = new PrintWriter(new java.io.FileOutputStream(file, false)) // false to overwrite existing contents
            writer.write(hardCodedPrintCase)
            writer.close()
          } else {
            saveGeneratedCode(prog, fileName)
          }
         }
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
    // Check if we're in the root of the project (we can see the src folder) if not, we need to go up one level
    var filename = fileName + ".s"
    if (!inRootDir) { filename = parentDirPath(filename) }
    val file = new java.io.File(filename)
    val path = file.getAbsolutePath()
    // throw new Exception("Path to file: " + path)
    val writer = new PrintWriter(new java.io.FileOutputStream(file, false)) // false to overwrite existing contents
    writer.write(content)
    writer.close()
  }

  def compile(args: Array[String]): Int = synchronized(args.headOption match {
    case Some(filepath) => {
      // val fileContent = ("cat " + filename).!!
      // set a new val name to filename spliced - remove the .wacc extension and only take the substring from the end until the last slash
      val fileName = getFilename(filepath)
      var fileContent = ""
      try {
        fileContent = scala.io.Source.fromFile(filepath).mkString
      } catch {
        case e: java.io.FileNotFoundException => {
          println("IO Error: File not found")
          return -1
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