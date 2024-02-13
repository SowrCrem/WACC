package test.frontend.integration.semantic

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Exit extends AnyFlatSpec {

  "semanticErr - exit tests: badCharExit.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - exit tests: exitNonInt.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - exit tests: globalReturn.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - exit tests: returnsInMain.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
