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

class SimpleFunctions extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run valid/function/simple_functions/functionSimple.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionSimple.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/function/simple_functions/functionDeclaration.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionDeclaration.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run run valid/function/simple_functions/functionDoubleReturn.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionDoubleReturn.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/function/simple_functions/functionIfReturns.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionIfReturns.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/function/simple_functions/functionManyArguments.wacc" ignore {
    val path = constructPath(List("valid", "function", "simple_functions", "functionManyArguments.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/function/simple_functions/functionSimpleLoop.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionSimpleLoop.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/function/simple_functions/functionUpdateParameter.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "functionUpdateParameter.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/function/simple_functions/incFunction.wacc" in {
    val path = constructPath(List("valid", "function", "simple_functions", "incFunction.wacc"))
    runSucceeds(path, "Hello World!", 0)
  } 
  
}
