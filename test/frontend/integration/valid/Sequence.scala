package test.frontend.integration.valid.Sequence

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Sequence extends AnyFlatSpec {

  "valid - sequence tests: basicSeq2.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/basicSeq2.wacc")
  }

  "valid - sequence tests: basicSeq.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/basicSeq.wacc")
  }

  "valid - sequence tests: boolAssignment.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/boolAssignment.wacc")
  }

  "valid - sequence tests: charAssignment.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/charAssignment.wacc")
  }

  "valid - sequence tests: exitSimple.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/exitSimple.wacc")
  }

  "valid - sequence tests: intAssignment.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/intAssignment.wacc")
  }

  "valid - sequence tests: intLeadingZeros.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/intLeadingZeros.wacc")
  }

  "valid - sequence tests: stringAssignment.wacc" should "return exit code 0" in {
    throwsNoError("valid/sequence/stringAssignment.wacc")
  }

}
