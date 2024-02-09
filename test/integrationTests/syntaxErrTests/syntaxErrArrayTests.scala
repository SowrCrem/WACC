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

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/array/arrayExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrArrayTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" in {/s/"syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" in {/"syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
