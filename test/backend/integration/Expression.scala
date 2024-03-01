package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import sys.process._
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Expression extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run valid/expressions/andExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "andExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/andOverOrExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "andOverOrExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/boolCalc.wacc" in {
      val path = constructPath(List("valid", "expressions", "boolCalc.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/boolExpr1.wacc" in {
      val path = constructPath(List("valid", "expressions", "boolExpr1.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/charComparisonExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "charComparisonExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/divExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "divExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/equalsExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "equalsExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/equalsOverAnd.wacc" in {
      val path = constructPath(List("valid", "expressions", "equalsOverAnd.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/equalsOverBool.wacc" in {
      val path = constructPath(List("valid", "expressions", "equalsOverBool.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/equalsOverOr.wacc" in {
      val path = constructPath(List("valid", "expressions", "equalsOverOr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/greaterEqExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "greaterEqExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/greaterExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "greaterExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/intCalc.wacc" in {
      val path = constructPath(List("valid", "expressions", "intCalc.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/intExpr1.wacc" in {
      val path = constructPath(List("valid", "expressions", "intExpr1.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/lessCharExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "lessCharExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/lessEqExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "lessEqExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/lessExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "lessExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/longExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "longExpr.wacc"))
      runSucceeds(path, 153)
  }

  it should "run valid/expressions/longExpr2.wacc" in {
      val path = constructPath(List("valid", "expressions", "longExpr2.wacc"))
      runSucceeds(path, 9)
  }

  it should "run valid/expressions/longExpr3.wacc" in {
      val path = constructPath(List("valid", "expressions", "longExpr3.wacc"))
      runSucceeds(path, 9)
  }

  it should "run valid/expressions/longSplitExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "longSplitExpr.wacc"))
      runSucceeds(path, 153)
  }

  it should "run valid/expressions/longSplitExpr2.wacc" in {
      val path = constructPath(List("valid", "expressions", "longSplitExpr2.wacc"))
      runSucceeds(path, 128)
  }

  it should "run valid/expressions/minusExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "minusExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/minusMinusExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "minusMinusExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/minusNoWhitespaceExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "minusNoWhitespaceExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/minusPlusExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "minusPlusExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/modExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "modExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/multExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "multExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/multNoWhitespaceExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "multNoWhitespaceExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negBothDiv.wacc" in {
      val path = constructPath(List("valid", "expressions", "negBothDiv.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negBothMod.wacc" in {
      val path = constructPath(List("valid", "expressions", "negBothMod.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negDividendDiv.wacc" in {
      val path = constructPath(List("valid", "expressions", "negDividendDiv.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negDividendMod.wacc" in {
      val path = constructPath(List("valid", "expressions", "negDividendMod.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negDivisorDiv.wacc" in {
      val path = constructPath(List("valid", "expressions", "negDivisorDiv.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negDivisorMod.wacc" in {
      val path = constructPath(List("valid", "expressions", "negDivisorMod.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/negExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "negExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/notExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "notExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/notequalsExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "notequalsExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/orExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "orExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/ordAndchrExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "ordAndchrExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/plusExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "plusExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/plusMinusExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "plusMinusExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/plusNoWhitespaceExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "plusNoWhitespaceExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/plusPlusExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "plusPlusExpr.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/sequentialCount.wacc" in {
      val path = constructPath(List("valid", "expressions", "sequentialCount.wacc"))
      runSucceeds(path)
  }

  it should "run valid/expressions/stringEqualsExpr.wacc" in {
      val path = constructPath(List("valid", "expressions", "stringEqualsExpr.wacc"))
      runSucceeds(path)
  }
}
  
