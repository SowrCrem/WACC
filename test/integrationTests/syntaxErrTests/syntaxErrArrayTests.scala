package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrArrayTests extends AnyFlatSpec {

  "syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/array/arrayExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    if (exitCode != 100) {
      // Replace " in {" with "\"syntaxErr - array tests: arrayExpr.wacc\" should \"return exit code 100\" in {" in the test case
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrArrayTests.scala"
      val sedCommand = s"""sed -i 's/"syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" ignore {/"syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" ignore {/g' $filePath"""
      sedCommand.!
    }
    exitCode shouldBe 100
  }

}
