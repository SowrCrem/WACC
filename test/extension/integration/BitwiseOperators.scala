package test.extension.integration

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

class BitwiseOperators extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run extensions/bitwise_operators/simpleAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleAnd.wacc"))
    runSucceeds(path)
  }

  it should "run extensions/bitwise_operators/simpleOr.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleOr.wacc"))
    runSucceeds(path)
  }

  it should "run extensions/bitwise_operators/simpleNot.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleNot.wacc"))
    runSucceeds(path)
  }

  it should "run extensions/bitwise_operators/varAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "varAnd.wacc"))
    runSucceeds(path)
  }

  // TODO: Add more tests for bitwise operators
  
}
