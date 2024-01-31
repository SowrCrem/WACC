import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrExpressionsTests extends AnyFlatSpec {

  "syntaxErr - expressions tests: missingOperand1.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/expressions/missingOperand1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - expressions tests: missingOperand2.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/expressions/missingOperand2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - expressions tests: printlnConcat.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/expressions/printlnConcat.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
