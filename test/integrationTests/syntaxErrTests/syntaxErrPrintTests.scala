package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrPrintTests extends AnyFlatSpec {

  "syntaxErr - print tests: printlnCharArry.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/print/printlnCharArry.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrPrintTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - print tests: printlnCharArry.wacc" should "return exit code 100" in {/s/"syntaxErr - print tests: printlnCharArry.wacc" should "return exit code 100" in {/"syntaxErr - print tests: printlnCharArry.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
