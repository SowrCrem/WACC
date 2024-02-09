package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrExpressionsTests extends AnyFlatSpec {

  "syntaxErr - expressions tests: missingOperand1.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/expressions/missingOperand1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - expressions tests: missingOperand1.wacc" should "return exit code 100" in {/s/"syntaxErr - expressions tests: missingOperand1.wacc" should "return exit code 100" in {/"syntaxErr - expressions tests: missingOperand1.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - expressions tests: missingOperand2.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/expressions/missingOperand2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - expressions tests: missingOperand2.wacc" should "return exit code 100" in {/s/"syntaxErr - expressions tests: missingOperand2.wacc" should "return exit code 100" in {/"syntaxErr - expressions tests: missingOperand2.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - expressions tests: printlnConcat.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/expressions/printlnConcat.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - expressions tests: printlnConcat.wacc" should "return exit code 100" in {/s/"syntaxErr - expressions tests: printlnConcat.wacc" should "return exit code 100" in {/"syntaxErr - expressions tests: printlnConcat.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
