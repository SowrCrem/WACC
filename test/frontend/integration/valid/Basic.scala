package test.frontend.integration.valid

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Basic extends AnyFlatSpec {

  "valid - exit tests: exit-1.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - exit tests: exitBasic.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - exit tests: exitBasic2.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - exit tests: exitWrap.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - skip tests: comment.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - skip tests: commentEoF.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - skip tests: commentInLine.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - skip tests: skip.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
