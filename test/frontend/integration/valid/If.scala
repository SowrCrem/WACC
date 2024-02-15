package test.frontend.integration.valid.If

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class If extends AnyFlatSpec {

  "valid - if tests: if1.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/if1.wacc")
  }

  "valid - if tests: if2.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/if2.wacc")
  }

  "valid - if tests: if3.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/if3.wacc")
  }

  "valid - if tests: if4.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/if4.wacc")
  }

  "valid - if tests: if5.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/if5.wacc")
  }

  "valid - if tests: if6.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/if6.wacc")
  }

  "valid - if tests: ifBasic.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/ifBasic.wacc")
  }

  "valid - if tests: ifFalse.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/ifFalse.wacc")
  }

  "valid - if tests: ifTrue.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/ifTrue.wacc")
  }

  "valid - if tests: whitespace.wacc" should "return exit code 0" in {
    throwsNoError("valid/if/whitespace.wacc")
  }

}
