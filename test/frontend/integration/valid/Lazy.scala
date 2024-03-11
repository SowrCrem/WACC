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
import org.scalatest.BeforeAndAfter
import org.scalatest.BeforeAndAfterAll
import wacc.semanticChecker
import org.scalatest.BeforeAndAfterEach

class Lazy extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
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
