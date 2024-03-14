package test.frontend.integration.valid.Lazy

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Lazy extends AnyFlatSpec {

  "valid - lazy tests: evaluationDivByZeroFails.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/evaluationDivByZeroFails.wacc")
  }

  "valid - lazy tests: lazyDoesntCookTheCodebase.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/lazyDoesntCookTheCodebase.wacc")
  }

  "valid - lazy tests: lazyInt2.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/lazyInt2.wacc")
  }

  "valid - lazy tests: lazyIntDivZero.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/lazyIntDivZero.wacc")
  }

  "valid - lazy tests: lazyIntUsed.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/lazyIntUsed.wacc")
  }

  "valid - lazy tests: lazyInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/lazyInt.wacc")
  }

  "valid - lazy tests: lazynessCaches.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/lazynessCaches.wacc")
  }

  "valid - lazy tests: superLazy.wacc" should "return exit code 0" in {
    throwsNoError("valid/lazy/superLazy.wacc")
  }

}
