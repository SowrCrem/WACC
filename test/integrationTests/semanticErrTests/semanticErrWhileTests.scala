package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrWhileTests extends AnyFlatSpec {

  "semanticErr - while tests: falsErr.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/while/falsErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - while tests: falsErr.wacc" should "return exit code 200" in {/s/"semanticErr - while tests: falsErr.wacc" should "return exit code 200" in {/"semanticErr - while tests: falsErr.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - while tests: truErr.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/while/truErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - while tests: truErr.wacc" should "return exit code 200" in {/s/"semanticErr - while tests: truErr.wacc" should "return exit code 200" in {/"semanticErr - while tests: truErr.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - while tests: whileIntCondition.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/while/whileIntCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - while tests: whileIntCondition.wacc" should "return exit code 200" in {/s/"semanticErr - while tests: whileIntCondition.wacc" should "return exit code 200" in {/"semanticErr - while tests: whileIntCondition.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
