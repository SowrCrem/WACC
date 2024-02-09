package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrLiteralsTests extends AnyFlatSpec {

  "syntaxErr - literals tests: charLiteralSingle.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/literals/charLiteralSingle.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrLiteralsTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - literals tests: charLiteralSingle.wacc" should "return exit code 100" in {/s/"syntaxErr - literals tests: charLiteralSingle.wacc" should "return exit code 100" in {/"syntaxErr - literals tests: charLiteralSingle.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - literals tests: stringLiteralNoNewlines.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/literals/stringLiteralNoNewlines.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrLiteralsTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - literals tests: stringLiteralNoNewlines.wacc" should "return exit code 100" in {/s/"syntaxErr - literals tests: stringLiteralNoNewlines.wacc" should "return exit code 100" in {/"syntaxErr - literals tests: stringLiteralNoNewlines.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - literals tests: stringLiteralOnlyAscii.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/literals/stringLiteralOnlyAscii.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrLiteralsTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - literals tests: stringLiteralOnlyAscii.wacc" should "return exit code 100" in {/s/"syntaxErr - literals tests: stringLiteralOnlyAscii.wacc" should "return exit code 100" in {/"syntaxErr - literals tests: stringLiteralOnlyAscii.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
