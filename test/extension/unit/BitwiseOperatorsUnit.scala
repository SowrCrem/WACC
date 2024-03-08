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

class BitwiseOperatorsUnit extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "parse extensions/bitwise-operators/andExprBitwise.wacc" in {
    val path = constructPath(List("extensions", "bitwise-operators", "andExprBitwise.wacc"))
    parsesWithoutSyntaxError(path)
  }
}
