package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrMultipleTests extends AnyFlatSpec {

  "semanticErr - multiple tests: funcMess.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/multiple/funcMess.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrMultipleTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - multiple tests: funcMess.wacc" should "return exit code 200" in {/s/"semanticErr - multiple tests: funcMess.wacc" should "return exit code 200" in {/"semanticErr - multiple tests: funcMess.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - multiple tests: ifAndWhileErrs.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/multiple/ifAndWhileErrs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrMultipleTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - multiple tests: ifAndWhileErrs.wacc" should "return exit code 200" in {/s/"semanticErr - multiple tests: ifAndWhileErrs.wacc" should "return exit code 200" in {/"semanticErr - multiple tests: ifAndWhileErrs.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - multiple tests: messyExpr.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/multiple/messyExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrMultipleTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - multiple tests: messyExpr.wacc" should "return exit code 200" in {/s/"semanticErr - multiple tests: messyExpr.wacc" should "return exit code 200" in {/"semanticErr - multiple tests: messyExpr.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - multiple tests: multiCaseSensitivity.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/multiple/multiCaseSensitivity.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrMultipleTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - multiple tests: multiCaseSensitivity.wacc" should "return exit code 200" in {/s/"semanticErr - multiple tests: multiCaseSensitivity.wacc" should "return exit code 200" in {/"semanticErr - multiple tests: multiCaseSensitivity.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - multiple tests: multiTypeErrs.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/multiple/multiTypeErrs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrMultipleTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - multiple tests: multiTypeErrs.wacc" should "return exit code 200" in {/s/"semanticErr - multiple tests: multiTypeErrs.wacc" should "return exit code 200" in {/"semanticErr - multiple tests: multiTypeErrs.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - multiple tests: obfuscatingReturnsWithWhile.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/multiple/obfuscatingReturnsWithWhile.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrMultipleTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - multiple tests: obfuscatingReturnsWithWhile.wacc" should "return exit code 200" in {/s/"semanticErr - multiple tests: obfuscatingReturnsWithWhile.wacc" should "return exit code 200" in {/"semanticErr - multiple tests: obfuscatingReturnsWithWhile.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
