import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrExpressionsTests extends AnyFlatSpec {

  "semanticErr - expressions tests: boolOpTypeErr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/boolOpTypeErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - expressions tests: exprTypeErr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/exprTypeErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - expressions tests: intOpTypeErr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/intOpTypeErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - expressions tests: lessPairExpr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/lessPairExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - expressions tests: mixedOpTypeErr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/mixedOpTypeErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - expressions tests: moreArrExpr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/moreArrExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - expressions tests: stringElemErr.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Expressions/stringElemErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
