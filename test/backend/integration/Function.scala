package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import sys.process._
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Function extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  // Simple Function Tests ---------------------------------------------------------------------------

  "WACC" should "run valid/function/simple_functions/functionSimple.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionSimple.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/functionDeclaration.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run run valid/function/simple_functions/functionDoubleReturn.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionDoubleReturn.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/functionIfReturns.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionIfReturns.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/functionManyArguments.wacc" ignore {
    val path = constructPath(List("valid", "function", "simple_functions", "functionManyArguments.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/functionSimpleLoop.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionSimpleLoop.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/functionUpdateParameter.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionUpdateParameter.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/incFunction.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "incFunction.wacc"))
    runSucceeds(path)
  } 

  it should "run valid/function/simple_functions/lotsOfLocals.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "lotsOfLocals.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/argScopeCanBeShadowed.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "argScopeCanBeShadowed.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/usesArgumentWhilstMakingArgument.wacc" in {
      val path = constructPath(List("valid", "function", "simple_functions", "usesArgumentWhilstMakingArgument.wacc"))
      runSucceeds(path)
  }

  it should "run valid/function/simple_functions/functionMultiReturns.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionMultiReturns.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/sameNameAsVar.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "sameNameAsVar.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/sameArgName.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "sameArgName.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/sameArgName2.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "sameArgName2.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/simple_functions/manyArgumentsChar.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "manyArgumentsChar.wacc"))
    runSucceeds(path)
  }

  // Nested Function Tests ---------------------------------------------------------------------------

  // !!! MAKE SURE THESE PASS OR KEEP THEM IGNORED !!! THEY CAN CAUSE THE TEST SCRIPT TO GET STUCK !!!

  it should "run valid/function/nested_functions/fibonacciFullRec.wacc" in {
    val path = constructPath(List("valid", "function", "nested_functions", "fibonacciFullRec.wacc"))
    runSucceeds(path)
  } 

  it should "run valid/function/nested_functions/fibonacciRecursive.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "fibonacciRecursive.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/nested_functions/fixedPointRealArithmetic.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "fixedPointRealArithmetic.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/nested_functions/functionConditionalReturn.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "functionConditionalReturn.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/nested_functions/mutualRecursion.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "mutualRecursion.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/nested_functions/printInputTriangle.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "printInputTriangle.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/nested_functions/printTriangle.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "printTriangle.wacc"))
    runSucceeds(path)
  }

  it should "run valid/function/nested_functions/simpleRecursion.wacc" ignore {
    val path = constructPath(List("valid", "function", "nested_functions", "simpleRecursion.wacc"))
    runSucceeds(path)
  }
}
