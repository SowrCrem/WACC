package test

import wacc.Main
import wacc.parser._
import wacc.Errors._
import wacc.{
  Position,
  Program,
  Func,
  Expr,
  Stat,
  Exit,
  SymbolTable,
  TypeChecker
}
import parsley.{Failure, Result, Success}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

object Utils {
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

  private def exitsWithCode(path: String, code: Int): Assertion = synchronized({
    // If current directory is not the root of the project, then add a ../ to the start of the path
    var newPath = "test/wacc/" + path
    if (!new java.io.File("src/main/wacc/Main.scala").exists) {
      newPath = "../" + newPath
    }
    val exitCode = Main.compile(Array(newPath))
    println("Exit Code: " + exitCode)
    // if (exitCode != 200) {
    //   val filePath = "test/integration/semantic/checkArrays.scala"
    //   val sedCommand = s"""sed -i '0,/"semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" in {/s/"semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" in {/"semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" ignore {/' $filePath"""
    //   sedCommand.!
    // }
    exitCode shouldBe code
  })
}
