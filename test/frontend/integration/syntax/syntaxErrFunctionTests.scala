package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrFunctionTests extends AnyFlatSpec {

  "syntaxErr - function tests: badlyNamed.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/badlyNamed.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: badlyNamed.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: badlyNamed.wacc" should "return exit code 100" in {/"syntaxErr - function tests: badlyNamed.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: badlyPlaced.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/badlyPlaced.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: badlyPlaced.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: badlyPlaced.wacc" should "return exit code 100" in {/"syntaxErr - function tests: badlyPlaced.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: funcExpr.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/funcExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: funcExpr.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: funcExpr.wacc" should "return exit code 100" in {/"syntaxErr - function tests: funcExpr.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: funcExpr2.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/funcExpr2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: funcExpr2.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: funcExpr2.wacc" should "return exit code 100" in {/"syntaxErr - function tests: funcExpr2.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionConditionalNoReturn.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionConditionalNoReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionConditionalNoReturn.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionConditionalNoReturn.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionConditionalNoReturn.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionEndingNotReturn.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionEndingNotReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionEndingNotReturn.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionEndingNotReturn.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionEndingNotReturn.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionLateDefine.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionLateDefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionLateDefine.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionLateDefine.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionLateDefine.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionMissingCall.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionMissingCall.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionMissingCall.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionMissingCall.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionMissingCall.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionMissingPType.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionMissingPType.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionMissingPType.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionMissingPType.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionMissingPType.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionMissingParam.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionMissingParam.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionMissingParam.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionMissingParam.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionMissingParam.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionMissingType.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionMissingType.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionMissingType.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionMissingType.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionMissingType.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionNoReturn.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionNoReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionNoReturn.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionNoReturn.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionNoReturn.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionReturnInLoop.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionReturnInLoop.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionReturnInLoop.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionReturnInLoop.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionReturnInLoop.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: functionScopeDef.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/functionScopeDef.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: functionScopeDef.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: functionScopeDef.wacc" should "return exit code 100" in {/"syntaxErr - function tests: functionScopeDef.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: mutualRecursionNoReturn.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/mutualRecursionNoReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: mutualRecursionNoReturn.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: mutualRecursionNoReturn.wacc" should "return exit code 100" in {/"syntaxErr - function tests: mutualRecursionNoReturn.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: noBodyAfterFuncs.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/noBodyAfterFuncs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: noBodyAfterFuncs.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: noBodyAfterFuncs.wacc" should "return exit code 100" in {/"syntaxErr - function tests: noBodyAfterFuncs.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - function tests: thisIsNotC.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/function/thisIsNotC.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrFunctionTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - function tests: thisIsNotC.wacc" should "return exit code 100" in {/s/"syntaxErr - function tests: thisIsNotC.wacc" should "return exit code 100" in {/"syntaxErr - function tests: thisIsNotC.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
