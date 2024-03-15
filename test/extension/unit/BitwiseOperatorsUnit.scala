package test.backend.unit

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

class BitwiseOperatorsUnit extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "parse extensions/bitwise_operators/simpleAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleAnd.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/bitwise_operators/simpleOr.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleOr.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/bitwise_operators/simpleNot.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleNot.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/bitwise_operators/varAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "varAnd.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "analyse extensions/bitwise_operators/simpleAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleAnd.wacc"))
    parsesWithoutSyntaxError(path)
    semanticChecker.reset()
    X86IRGenerator.reset()
    parsesWithoutSemanticError(path)
  }

  it should "analyse extensions/bitwise_operators/simpleOr.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleOr.wacc"))
    parsesWithoutSyntaxError(path)
    semanticChecker.reset()
    X86IRGenerator.reset()
    parsesWithoutSemanticError(path)
  }

  it should "analyse extensions/bitwise_operators/simpleNot.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "simpleNot.wacc"))
    parsesWithoutSyntaxError(path)
    semanticChecker.reset()
    X86IRGenerator.reset()
    parsesWithoutSemanticError(path)
  }

  it should "analyse extensions/bitwise_operators/varAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwise_operators", "varAnd.wacc"))
    parsesWithoutSyntaxError(path)
    semanticChecker.reset()
    X86IRGenerator.reset()
    parsesWithoutSemanticError(path)
  }

}
