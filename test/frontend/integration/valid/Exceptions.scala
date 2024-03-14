package test.frontend.integration.valid.Exceptions

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Exceptions extends AnyFlatSpec {

  "valid - exceptions tests: simpletrycatch.wacc" should "return exit code 0" in {
    throwsNoError("valid/exceptions/simpletrycatch.wacc")
  }

}
