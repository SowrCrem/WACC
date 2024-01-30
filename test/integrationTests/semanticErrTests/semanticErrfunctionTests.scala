import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrFunctionTests extends AnyFlatSpec {

  "semanticErr - function tests: callUndefFunction.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/callUndefFunction.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: doubleArgDef.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/doubleArgDef.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionAssign.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionAssign.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionBadArgUse.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionBadArgUse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionBadCall.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionBadCall.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionBadParam.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionBadParam.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionBadReturn.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionBadReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionOverArgs.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionOverArgs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionRedefine.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionSwapArgs.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionSwapArgs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: functionUnderArgs.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/functionUnderArgs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: funcVarAccess.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/funcVarAccess.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: invalidReturnsBranched.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/invalidReturnsBranched.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - function tests: mismatchingReturns.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Function/mismatchingReturns.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

}
