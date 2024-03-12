package test.frontend.integration.valid.Macros

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Macros extends AnyFlatSpec {

  "valid - macros tests: intCalcMacro.wacc" should "return exit code 0" in {
    throwsNoError("valid/macros/intCalcMacro.wacc")
  }

  "valid - macros tests: intMacro.wacc" should "return exit code 0" in {
    throwsNoError("valid/macros/intMacro.wacc")
  }

  "valid - macros tests: unusedIntMacro.wacc" should "return exit code 0" in {
    throwsNoError("valid/macros/unusedIntMacro.wacc")
  }

}
