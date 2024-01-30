import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidExpressionsTests extends AnyFlatSpec {

  "valid - expressions tests: andExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/andExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: andOverOrExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/andOverOrExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: boolCalc.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/boolCalc.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: boolExpr1.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/boolExpr1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: charComparisonExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/charComparisonExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: divExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/divExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: equalsExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/equalsExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: equalsOverAnd.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/equalsOverAnd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: equalsOverBool.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/equalsOverBool.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: equalsOverOr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/equalsOverOr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: greaterEqExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/greaterEqExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: greaterExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/greaterExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: intCalc.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/intCalc.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: intExpr1.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/intExpr1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: lessCharExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/lessCharExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: lessEqExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/lessEqExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: lessExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/lessExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: longExpr2.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/longExpr2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: longExpr3.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/longExpr3.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: longExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/longExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: longSplitExpr2.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/longSplitExpr2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: longSplitExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/longSplitExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: minusExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/minusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: minusMinusExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/minusMinusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: minusNoWhitespaceExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/minusNoWhitespaceExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: minusPlusExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/minusPlusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: modExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/modExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: multExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/multExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: multNoWhitespaceExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/multNoWhitespaceExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negBothDiv.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negBothDiv.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negBothMod.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negBothMod.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negDividendDiv.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negDividendDiv.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negDividendMod.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negDividendMod.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negDivisorDiv.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negDivisorDiv.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negDivisorMod.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negDivisorMod.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: negExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/negExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: notequalsExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/notequalsExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: notExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/notExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: ordAndchrExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/ordAndchrExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: orExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/orExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: plusExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/plusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: plusMinusExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/plusMinusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: plusNoWhitespaceExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/plusNoWhitespaceExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: plusPlusExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/plusPlusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: sequentialCount.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/sequentialCount.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - expressions tests: stringEqualsExpr.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Expressions/stringEqualsExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

}
