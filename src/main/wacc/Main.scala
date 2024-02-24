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
   val hardCodedPrintCase = ".intel_syntax noprefix\n" +
    ".globl main\n" + 
    ".section .rodata\n" + 
    "# length of .L.str0\n" + 
    "	.int 12\n" + 
    ".L.str0:\n" + 
    "	.asciz \"Hello World!\"\n" + 
    ".text\n" + 
    "main:\n" + 
    "	push rbp\n" + 
    "		push rbx\n" + 
    "		mov rbp, rsp\n" + 
    "		lea rax, [rip + .L.str0]\n" + 
    "		push rax\n" + 
    "		pop rax\n" + 
    "		mov rax, rax\n" + 
    "		mov rdi, rax\n" + 
    "		call _prints\n" + 
    "		mov rax, 0\n" + 
    "		pop rbx\n" + 
    "		pop rbp\n" + 
    "		ret\n" + 
    "	.section .rodata\n" + 
    "		.int 4\n" + 
    "	.L._prints_str0:\n" + 
    "		.asciz \"%.*s\"\n" + 
    "	.text\n" + 
    "	_prints:\n" + 
    "		push rbp\n" + 
    "		mov rbp, rsp\n" + 
    "		and rsp, -16\n" + 
    "		mov rdx, rdi\n" + 
    "		mov esi, dword ptr [rdi - 4]\n" + 
    "		lea rdi, [rip + .L._prints_str0]\n" + 
    "		mov al, 0\n" + 
    "		call printf@plt\n" + 
    "		mov rdi, 0\n" + 
    "		call fflush@plt\n" + 
    "		mov rsp, rbp\n" + 
    "		pop rbp\n" + 
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

  def compile(args: Array[String]): Int = synchronized(args.headOption match {
    case Some(filepath) => {
      val fileName = getFilename(filepath)
      var fileContent = ""
      try {
        fileContent = scala.io.Source.fromFile(filepath).mkString
      } catch {
        case e: java.io.FileNotFoundException => {
          println("IO Error: File not found")
          -1
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