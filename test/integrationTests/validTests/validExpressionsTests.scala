package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class ValidExpressionsTests extends AnyFlatSpec {

  "valid - expressions tests: andExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/andExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: andExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: andExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: andExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: andOverOrExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/andOverOrExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: andOverOrExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: andOverOrExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: andOverOrExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: boolCalc.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/boolCalc.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: boolCalc.wacc" should "return exit code 0" in {/s/"valid - expressions tests: boolCalc.wacc" should "return exit code 0" in {/"valid - expressions tests: boolCalc.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: boolExpr1.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/boolExpr1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: boolExpr1.wacc" should "return exit code 0" in {/s/"valid - expressions tests: boolExpr1.wacc" should "return exit code 0" in {/"valid - expressions tests: boolExpr1.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: charComparisonExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/charComparisonExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: charComparisonExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: charComparisonExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: charComparisonExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: divExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/divExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: divExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: divExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: divExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: equalsExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/equalsExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: equalsExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: equalsExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: equalsExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: equalsOverAnd.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/equalsOverAnd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: equalsOverAnd.wacc" should "return exit code 0" in {/s/"valid - expressions tests: equalsOverAnd.wacc" should "return exit code 0" in {/"valid - expressions tests: equalsOverAnd.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: equalsOverBool.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/equalsOverBool.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: equalsOverBool.wacc" should "return exit code 0" in {/s/"valid - expressions tests: equalsOverBool.wacc" should "return exit code 0" in {/"valid - expressions tests: equalsOverBool.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: equalsOverOr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/equalsOverOr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: equalsOverOr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: equalsOverOr.wacc" should "return exit code 0" in {/"valid - expressions tests: equalsOverOr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: greaterEqExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/greaterEqExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: greaterEqExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: greaterEqExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: greaterEqExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: greaterExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/greaterExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: greaterExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: greaterExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: greaterExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: intCalc.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/intCalc.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: intCalc.wacc" should "return exit code 0" in {/s/"valid - expressions tests: intCalc.wacc" should "return exit code 0" in {/"valid - expressions tests: intCalc.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: intExpr1.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/intExpr1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: intExpr1.wacc" should "return exit code 0" in {/s/"valid - expressions tests: intExpr1.wacc" should "return exit code 0" in {/"valid - expressions tests: intExpr1.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: lessCharExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/lessCharExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: lessCharExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: lessCharExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: lessCharExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: lessEqExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/lessEqExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: lessEqExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: lessEqExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: lessEqExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: lessExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/lessExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: lessExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: lessExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: lessExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: longExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/longExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: longExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: longExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: longExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: longExpr2.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/longExpr2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: longExpr2.wacc" should "return exit code 0" in {/s/"valid - expressions tests: longExpr2.wacc" should "return exit code 0" in {/"valid - expressions tests: longExpr2.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: longExpr3.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/longExpr3.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: longExpr3.wacc" should "return exit code 0" in {/s/"valid - expressions tests: longExpr3.wacc" should "return exit code 0" in {/"valid - expressions tests: longExpr3.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: longSplitExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/longSplitExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: longSplitExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: longSplitExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: longSplitExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: longSplitExpr2.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/longSplitExpr2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: longSplitExpr2.wacc" should "return exit code 0" in {/s/"valid - expressions tests: longSplitExpr2.wacc" should "return exit code 0" in {/"valid - expressions tests: longSplitExpr2.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: minusExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/minusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: minusExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: minusExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: minusExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: minusMinusExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/minusMinusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: minusMinusExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: minusMinusExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: minusMinusExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: minusNoWhitespaceExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/minusNoWhitespaceExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: minusNoWhitespaceExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: minusNoWhitespaceExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: minusNoWhitespaceExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: minusPlusExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/minusPlusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: minusPlusExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: minusPlusExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: minusPlusExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: modExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/modExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: modExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: modExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: modExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: multExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/multExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: multExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: multExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: multExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: multNoWhitespaceExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/multNoWhitespaceExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: multNoWhitespaceExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: multNoWhitespaceExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: multNoWhitespaceExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negBothDiv.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negBothDiv.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negBothDiv.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negBothDiv.wacc" should "return exit code 0" in {/"valid - expressions tests: negBothDiv.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negBothMod.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negBothMod.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negBothMod.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negBothMod.wacc" should "return exit code 0" in {/"valid - expressions tests: negBothMod.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negDividendDiv.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negDividendDiv.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negDividendDiv.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negDividendDiv.wacc" should "return exit code 0" in {/"valid - expressions tests: negDividendDiv.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negDividendMod.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negDividendMod.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negDividendMod.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negDividendMod.wacc" should "return exit code 0" in {/"valid - expressions tests: negDividendMod.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negDivisorDiv.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negDivisorDiv.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negDivisorDiv.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negDivisorDiv.wacc" should "return exit code 0" in {/"valid - expressions tests: negDivisorDiv.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negDivisorMod.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negDivisorMod.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negDivisorMod.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negDivisorMod.wacc" should "return exit code 0" in {/"valid - expressions tests: negDivisorMod.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: negExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/negExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: negExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: negExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: negExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: notExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/notExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: notExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: notExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: notExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: notequalsExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/notequalsExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: notequalsExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: notequalsExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: notequalsExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: orExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/orExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: orExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: orExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: orExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: ordAndchrExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/ordAndchrExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: ordAndchrExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: ordAndchrExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: ordAndchrExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: plusExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/plusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: plusExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: plusExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: plusExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: plusMinusExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/plusMinusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: plusMinusExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: plusMinusExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: plusMinusExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: plusNoWhitespaceExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/plusNoWhitespaceExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: plusNoWhitespaceExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: plusNoWhitespaceExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: plusNoWhitespaceExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: plusPlusExpr.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/expressions/plusPlusExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: plusPlusExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: plusPlusExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: plusPlusExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: sequentialCount.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/sequentialCount.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: sequentialCount.wacc" should "return exit code 0" in {/s/"valid - expressions tests: sequentialCount.wacc" should "return exit code 0" in {/"valid - expressions tests: sequentialCount.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - expressions tests: stringEqualsExpr.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/expressions/stringEqualsExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validExpressionsTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - expressions tests: stringEqualsExpr.wacc" should "return exit code 0" in {/s/"valid - expressions tests: stringEqualsExpr.wacc" should "return exit code 0" in {/"valid - expressions tests: stringEqualsExpr.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

}
