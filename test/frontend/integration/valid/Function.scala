package test.frontend.integration.valid.Function

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

  "valid - nested_functions tests: fibonacciFullRec.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/fibonacciFullRec.wacc")
  }

  "valid - nested_functions tests: fibonacciRecursive.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/fibonacciRecursive.wacc")
  }

  "valid - nested_functions tests: fixedPointRealArithmetic.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/fixedPointRealArithmetic.wacc")
  }

  "valid - nested_functions tests: functionConditionalReturn.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/functionConditionalReturn.wacc")
  }

  "valid - nested_functions tests: mutualRecursion.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/mutualRecursion.wacc")
  }

  "valid - nested_functions tests: printInputTriangle.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/printInputTriangle.wacc")
  }

  "valid - nested_functions tests: printTriangle.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/printTriangle.wacc")
  }

  "valid - nested_functions tests: simpleRecursion.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/nested_functions/simpleRecursion.wacc")
  }

  "valid - simple_functions tests: argScopeCanBeShadowed.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/argScopeCanBeShadowed.wacc")
  }

  "valid - simple_functions tests: asciiTable.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/asciiTable.wacc")
  }

  "valid - simple_functions tests: functionDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionDeclaration.wacc")
  }

  "valid - simple_functions tests: functionDoubleReturn.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionDoubleReturn.wacc")
  }

  "valid - simple_functions tests: functionIfReturns.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionIfReturns.wacc")
  }

  "valid - simple_functions tests: functionManyArguments.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionManyArguments.wacc")
  }

  "valid - simple_functions tests: functionMultiReturns.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionMultiReturns.wacc")
  }

  "valid - simple_functions tests: functionReturnPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionReturnPair.wacc")
  }

  "valid - simple_functions tests: functionSimpleLoop.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionSimpleLoop.wacc")
  }

  "valid - simple_functions tests: functionSimple.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionSimple.wacc")
  }

  "valid - simple_functions tests: functionUpdateParameter.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/functionUpdateParameter.wacc")
  }

  "valid - simple_functions tests: incFunction.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/incFunction.wacc")
  }

  "valid - simple_functions tests: lotsOfLocals.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/lotsOfLocals.wacc")
  }

  "valid - simple_functions tests: manyArgumentsChar.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/manyArgumentsChar.wacc")
  }

  "valid - simple_functions tests: manyArgumentsInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/manyArgumentsInt.wacc")
  }

  "valid - simple_functions tests: negFunction.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/negFunction.wacc")
  }

  "valid - simple_functions tests: punning.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/punning.wacc")
  }

  "valid - simple_functions tests: sameArgName2.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/sameArgName2.wacc")
  }

  "valid - simple_functions tests: sameArgName.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/sameArgName.wacc")
  }

  "valid - simple_functions tests: sameNameAsVar.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/sameNameAsVar.wacc")
  }

  "valid - simple_functions tests: usesArgumentWhilstMakingArgument.wacc" should "return exit code 0" in {
    throwsNoError("valid/function/simple_functions/usesArgumentWhilstMakingArgument.wacc")
  }

}
