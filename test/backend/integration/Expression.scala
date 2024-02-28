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
  }

    "WACC" should "run valid/expressions/andExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "andExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/orExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "orExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/boolCalc.wacc" in {
        val path = constructPath(List("valid", "expressions", "boolCalc.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }
 
    it should "run valid/expressions/equalsExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "equalsExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/andOverOrExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "andOverOrExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/plusExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "plusExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expression/minusExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "minusExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expression/multExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "multExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expression/divExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "divExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/modExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "modExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expression/longExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "longExpr.wacc"))
        runSucceeds(path, "Hello World!", 153)
    }

    it should "run valid/expressions/boolExpr1.wacc" ignore {
        val path = constructPath(List("valid", "expressions", "boolExpr1.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/equalsOverBool.wacc" in {
        val path = constructPath(List("valid", "expressions", "equalsOverBool.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/expressions/lessEqExpr.wacc" in {
        val path = constructPath(List("valid", "expressions", "lessEqExpr.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }



}
  
