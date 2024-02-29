package test.frontend.integration.valid.While

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class While extends AnyFlatSpec {

  "valid - while tests: fibonacciFullIt.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/fibonacciFullIt.wacc")
  }

  "valid - while tests: fibonacciIterative.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/fibonacciIterative.wacc")
  }

  "valid - while tests: loopCharCondition.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/loopCharCondition.wacc")
  }

  "valid - while tests: loopIntCondition.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/loopIntCondition.wacc")
  }

  "valid - while tests: max.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/max.wacc")
  }

  "valid - while tests: min.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/min.wacc")
  }

  "valid - while tests: rmStyleAdd.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/rmStyleAdd.wacc")
  }

  "valid - while tests: rmStyleAddIO.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/rmStyleAddIO.wacc")
  }

  "valid - while tests: whileBasic.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/whileBasic.wacc")
  }

  "valid - while tests: whileBoolFlip.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/whileBoolFlip.wacc")
  }

  "valid - while tests: whileCount.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/whileCount.wacc")
  }

  "valid - while tests: whileFalse.wacc" should "return exit code 0" in {
    throwsNoError("valid/while/whileFalse.wacc")
  }

}
