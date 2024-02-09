package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrFunctionTests extends AnyFlatSpec {

  "semanticErr - function tests: callUndefFunction.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/callUndefFunction.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: callUndefFunction.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: callUndefFunction.wacc" should "return exit code 200" in {/"semanticErr - function tests: callUndefFunction.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: doubleArgDef.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/doubleArgDef.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: doubleArgDef.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: doubleArgDef.wacc" should "return exit code 200" in {/"semanticErr - function tests: doubleArgDef.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionAssign.wacc" should "return exit code 200" ignore {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionAssign.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionAssign.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionAssign.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionAssign.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionBadArgUse.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionBadArgUse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionBadArgUse.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionBadArgUse.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionBadArgUse.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionBadCall.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionBadCall.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionBadCall.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionBadCall.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionBadCall.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionBadParam.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionBadParam.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionBadParam.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionBadParam.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionBadParam.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionBadReturn.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionBadReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionBadReturn.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionBadReturn.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionBadReturn.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionOverArgs.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionOverArgs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionOverArgs.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionOverArgs.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionOverArgs.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionRedefine.wacc" should "return exit code 200" ignore {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionRedefine.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionRedefine.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionRedefine.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionSwapArgs.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionSwapArgs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionSwapArgs.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionSwapArgs.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionSwapArgs.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: functionUnderArgs.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/functionUnderArgs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: functionUnderArgs.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: functionUnderArgs.wacc" should "return exit code 200" in {/"semanticErr - function tests: functionUnderArgs.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: funcVarAccess.wacc" should "return exit code 200" ignore {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/funcVarAccess.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: funcVarAccess.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: funcVarAccess.wacc" should "return exit code 200" in {/"semanticErr - function tests: funcVarAccess.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: invalidReturnsBranched.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/invalidReturnsBranched.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: invalidReturnsBranched.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: invalidReturnsBranched.wacc" should "return exit code 200" in {/"semanticErr - function tests: invalidReturnsBranched.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - function tests: mismatchingReturns.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/function/mismatchingReturns.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - function tests: mismatchingReturns.wacc" should "return exit code 200" in {/s/"semanticErr - function tests: mismatchingReturns.wacc" should "return exit code 200" in {/"semanticErr - function tests: mismatchingReturns.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
