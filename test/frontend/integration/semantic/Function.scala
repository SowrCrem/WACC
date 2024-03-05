package test.frontend.integration.semantic.Function

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Function extends AnyFlatSpec {

  "semanticErr - function tests: callUndefFunction.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/callUndefFunction.wacc")
  }

  "semanticErr - function tests: doubleArgDef.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/doubleArgDef.wacc")
  }

  "semanticErr - function tests: funcVarAccess.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/funcVarAccess.wacc")
  }

  "semanticErr - function tests: functionAssign.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionAssign.wacc")
  }

  "semanticErr - function tests: functionBadArgUse.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionBadArgUse.wacc")
  }

  "semanticErr - function tests: functionBadCall.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionBadCall.wacc")
  }

  "semanticErr - function tests: functionBadParam.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionBadParam.wacc")
  }

  "semanticErr - function tests: functionBadReturn.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionBadReturn.wacc")
  }

  "semanticErr - function tests: functionOverArgs.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionOverArgs.wacc")
  }

  "semanticErr - function tests: functionRedefine.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionRedefine.wacc")
  }

  "semanticErr - function tests: functionSwapArgs.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionSwapArgs.wacc")
  }

  "semanticErr - function tests: functionUnderArgs.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/functionUnderArgs.wacc")
  }

  "semanticErr - function tests: invalidReturnsBranched.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/invalidReturnsBranched.wacc")
  }

  "semanticErr - function tests: mismatchingReturns.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/function/mismatchingReturns.wacc")
  }

}
