package test.frontend.integration.valid.Expressions

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Expressions extends AnyFlatSpec {

  "valid - expressions tests: andExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/andExpr.wacc")
  }

  "valid - expressions tests: andOverOrExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/andOverOrExpr.wacc")
  }

  "valid - expressions tests: boolCalc.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/boolCalc.wacc")
  }

  "valid - expressions tests: boolExpr1.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/boolExpr1.wacc")
  }

  "valid - expressions tests: charComparisonExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/charComparisonExpr.wacc")
  }

  "valid - expressions tests: divExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/divExpr.wacc")
  }

  "valid - expressions tests: equalsExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/equalsExpr.wacc")
  }

  "valid - expressions tests: equalsOverAnd.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/equalsOverAnd.wacc")
  }

  "valid - expressions tests: equalsOverBool.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/equalsOverBool.wacc")
  }

  "valid - expressions tests: equalsOverOr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/equalsOverOr.wacc")
  }

  "valid - expressions tests: greaterEqExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/greaterEqExpr.wacc")
  }

  "valid - expressions tests: greaterExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/greaterExpr.wacc")
  }

  "valid - expressions tests: intCalc.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/intCalc.wacc")
  }

  "valid - expressions tests: intExpr1.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/intExpr1.wacc")
  }

  "valid - expressions tests: lessCharExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/lessCharExpr.wacc")
  }

  "valid - expressions tests: lessEqExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/lessEqExpr.wacc")
  }

  "valid - expressions tests: lessExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/lessExpr.wacc")
  }

  "valid - expressions tests: longExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/longExpr.wacc")
  }

  "valid - expressions tests: longExpr2.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/longExpr2.wacc")
  }

  "valid - expressions tests: longExpr3.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/longExpr3.wacc")
  }

  "valid - expressions tests: longSplitExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/longSplitExpr.wacc")
  }

  "valid - expressions tests: longSplitExpr2.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/longSplitExpr2.wacc")
  }

  "valid - expressions tests: minusExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/minusExpr.wacc")
  }

  "valid - expressions tests: minusMinusExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/minusMinusExpr.wacc")
  }

  "valid - expressions tests: minusNoWhitespaceExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/minusNoWhitespaceExpr.wacc")
  }

  "valid - expressions tests: minusPlusExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/minusPlusExpr.wacc")
  }

  "valid - expressions tests: modExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/modExpr.wacc")
  }

  "valid - expressions tests: multExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/multExpr.wacc")
  }

  "valid - expressions tests: multNoWhitespaceExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/multNoWhitespaceExpr.wacc")
  }

  "valid - expressions tests: negBothDiv.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negBothDiv.wacc")
  }

  "valid - expressions tests: negBothMod.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negBothMod.wacc")
  }

  "valid - expressions tests: negDividendDiv.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negDividendDiv.wacc")
  }

  "valid - expressions tests: negDividendMod.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negDividendMod.wacc")
  }

  "valid - expressions tests: negDivisorDiv.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negDivisorDiv.wacc")
  }

  "valid - expressions tests: negDivisorMod.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negDivisorMod.wacc")
  }

  "valid - expressions tests: negExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/negExpr.wacc")
  }

  "valid - expressions tests: notExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/notExpr.wacc")
  }

  "valid - expressions tests: notequalsExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/notequalsExpr.wacc")
  }

  "valid - expressions tests: orExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/orExpr.wacc")
  }

  "valid - expressions tests: ordAndchrExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/ordAndchrExpr.wacc")
  }

  "valid - expressions tests: plusExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/plusExpr.wacc")
  }

  "valid - expressions tests: plusMinusExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/plusMinusExpr.wacc")
  }

  "valid - expressions tests: plusNoWhitespaceExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/plusNoWhitespaceExpr.wacc")
  }

  "valid - expressions tests: plusPlusExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/plusPlusExpr.wacc")
  }

  "valid - expressions tests: sequentialCount.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/sequentialCount.wacc")
  }

  "valid - expressions tests: stringEqualsExpr.wacc" should "return exit code 0" in {
    throwsNoError("valid/expressions/stringEqualsExpr.wacc")
  }

}
