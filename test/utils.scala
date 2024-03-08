package test

import wacc._
import sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

object Utils {

  val sep = java.io.File.separator
  def constructPath(paths: List[String]): String = paths.mkString(sep)

  abstract class SemanticUnitTester extends AnyFlatSpec with BeforeAndAfterEach {
    var symbolTable: SymbolTable = _
    var typeChecker: TypeChecker = _

    override def beforeEach(): Unit = {
      symbolTable = new SymbolTable(None)
      typeChecker = new TypeChecker(symbolTable)
    }

    def checkSucceeds(func: Func): Assertion = checkSucceeds(List(func))

    def checkSucceeds(stat: Stat): Assertion = checkSucceedsStats(List(stat))

    def checkSucceeds(func:Func, stat:Stat): Assertion = checkSucceeds(List(func), List(stat))

    def checkSucceeds(funcList: List[Func]): Assertion = checkSucceeds(funcList, List())

    def checkSucceedsStats(statList: List[Stat]): Assertion = checkSucceeds(List(), statList)

    def checkSucceeds(funcList: List[Func], statList: List[Stat]): Assertion = checkSucceeds(Program(funcList, statList)(pos))

    def checkSucceeds(node: Position): Assertion = noException should be thrownBy typeChecker.check(node)

    def checkFails(node: Position, errorMessage: String = ""): Assertion = typeChecker.check(node) match {
      case Left(_) => succeed
      case _ => fail("No Semantic Errors were Thrown")
    }
  }

  val pos = (0, 0)

  def parseSucceeds(input: String, expected: Stat): Assertion = parseSucceeds(input, List(expected))

  def parseSucceeds(input: String, expected: List[Stat]): Assertion =
    parser.parse("begin " + input + " end") shouldBe Success(Program(List(), expected)(pos))

  def parseSucceeds(input: String, expected: Expr): Assertion =
    parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), List(Exit(expected)(pos)))(pos))

  def parseFails(input: String, errorMessage: String = ""): Assertion = errorMessage match {
    case "" => parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
    // Match on a Failure with the specific error message
    case _ => parser.parse("begin exit " + input + " end") match {
      case Failure (msg) => msg shouldBe errorMessage
      case _ => fail("Wrong Error Message")
    }
  }

  def throwsSemanticError(path: String): Assertion = {
    exitsWithCode(path, 200)
  }

  def throwsSyntaxError(path: String): Assertion = {
    exitsWithCode(path, 100)
  }

  def throwsIOError(path: String): Assertion = {
    exitsWithCode(path, -1)
  }

  def throwsNoError(path: String): Assertion = synchronized({
    exitsWithCode(path, 0)
  })

  def assemble(path: String): String = {
    val exeName = path.split(sep).last.split('.').head
    var exePath = s"$exeName.s"
    if (!Main.ROOT_DIR) { exePath = Main.parentDirPath(exePath) }
    var assembledPath = exeName
    if (!Main.ROOT_DIR) { assembledPath = Main.parentDirPath(assembledPath) }
    val gccCommand = s"gcc -o " + s"$assembledPath -z noexecstack " + s"$exePath"
    gccCommand.!
    "pwd".!
    assembledPath
  }

  def runSucceedsWithInputs(path: String, inputs: List[String], expOutput: String = "", expReturn: Int = 0): Assertion = {
    runSucceeds(path, expOutput, expReturn, Some(inputs.mkString(" ")))
  }

  def runSucceeds(path: String, expReturn: Int): Assertion = {
    runSucceeds(path, "", expReturn)
  }

  def runSucceeds(path: String, expOutput: String = "", expReturn: Int = 0, inputs: Option[String] = None): Assertion = synchronized {
    Main.setBackendTests()
    try {
      throwsNoError(path)
    } catch {
      case e: Throwable => fail("Compilation Error: Main.compile returned non-zero exit code: " + getExitCode(path) + ". Error Message: " + e.getMessage)
    }
    val exeName = assemble(path)
    val exeCommand = inputs match {
      case Some(inputStream) => s"./$exeName <<< $inputStream"
      case _                 => s"./$exeName"
    }
    // Command being run
    println("Running Command: " + exeCommand)

    val exeReturn = exeCommand.!
    try {
      val exeOutput = exeCommand.!!
      // TODO: Use IO Streams for this isntead fo trying to compare strings
      // exeOutput shouldBe expOutput
      printf("\nTest-output: \n" + exeOutput)
    } catch {
      case e: Throwable => exeReturn match {
        case 0 => println("Execution Error: " + e.getMessage)
        case _ => println("Non-Zero exit code as expected")
      }
    }
    exeReturn shouldBe expReturn
  }

  def parsesWithoutSyntaxError(path: String): Assertion = {
    exitsWithoutCode(path, 100)
  }

  private def exitsWithoutCode(path: String, code: Int): Assertion = synchronized({
    // check if getExitCode doesn't throw any error. If it does, fail saying Main.compile threw an error
    val exitCode = code
    try {
      val exitCode = getExitCode(path)
    } catch {
      case e: Throwable => fail("Compilation Error: Main.compile threw an error: " + e.getMessage)
    }
    println("Exit Code: " + exitCode)
    exitCode should not be code
  })

  private def exitsWithCode(path: String, code: Int): Assertion = synchronized({
    val exitCode = getExitCode(path)
    println("Exit Code: " + exitCode + " Expected: " + code)
    exitCode shouldBe code
  })

  private def getExitCode(path: String): Int = synchronized({
    // If current directory is not the root of the project, then add a ../ to the start of the path
    var newPath = constructPath(List("test", "wacc", path))
    if (!(new java.io.File(constructPath(List("src", "main", "wacc", "Main.scala")))).exists) {
      newPath = constructPath(List("..", newPath))
    }
    Main.compile(Array(newPath))
  })
}
