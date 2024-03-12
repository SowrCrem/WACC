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

  "WACC" should "parse extensions/bitwiseOps/simpleAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwiseOps", "simpleAnd.wacc"))
    runSucceeds(path)
  }

  it should "parse extensions/bitwiseOps/simpleOr.wacc" in {
    val path = constructPath(List("extensions", "bitwiseOps", "simpleOr.wacc"))
    runSucceeds(path)
  }

  it should "parse extensions/bitwiseOps/simpleNot.wacc" in {
    val path = constructPath(List("extensions", "bitwiseOps", "simpleNot.wacc"))
    runSucceeds(path)
  }

  it should "parse extensions/bitwiseOps/varAnd.wacc" in {
    val path = constructPath(List("extensions", "bitwiseOps", "varAnd.wacc"))
    runSucceeds(path)
  }
}
