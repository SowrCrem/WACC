import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrFunctionTests extends AnyFlatSpec {

  "syntaxErr - function tests: badlyNamed.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/badlyNamed.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: badlyPlaced.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/badlyPlaced.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: funcExpr2.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/funcExpr2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: funcExpr.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/funcExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionConditionalNoReturn.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionConditionalNoReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionEndingNotReturn.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionEndingNotReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionLateDefine.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionLateDefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionMissingCall.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionMissingCall.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionMissingParam.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionMissingParam.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionMissingPType.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionMissingPType.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionMissingType.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionMissingType.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionNoReturn.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionNoReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionReturnInLoop.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionReturnInLoop.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: functionScopeDef.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/functionScopeDef.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: mutualRecursionNoReturn.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/mutualRecursionNoReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: noBodyAfterFuncs.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/noBodyAfterFuncs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - function tests: thisIsNotC.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Function/thisIsNotC.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
