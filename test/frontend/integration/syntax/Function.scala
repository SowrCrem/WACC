package test.frontend.integration.syntax

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

  "syntaxErr - function tests: badlyNamed.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: badlyPlaced.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: funcExpr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: funcExpr2.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionConditionalNoReturn.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionEndingNotReturn.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionLateDefine.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionMissingCall.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionMissingPType.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionMissingParam.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionMissingType.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionNoReturn.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionReturnInLoop.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: functionScopeDef.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: mutualRecursionNoReturn.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: noBodyAfterFuncs.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - function tests: thisIsNotC.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
