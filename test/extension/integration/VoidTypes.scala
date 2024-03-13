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

class VoidTypes extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run extensions/void_types/simpleVoid.wacc" in {
    val path = constructPath(List("extensions", "void_types", "simpleVoid.wacc"))
    runSucceeds(path, "Hello!")
  }

  it should "run extensions/void_types/voidWithParams.wacc" in {
    val path = constructPath(List("extensions", "void_types", "voidWithParams.wacc"))
    runSucceeds(path)
  }

  it should "not parse extensions/void_types/invalidVoidSyntax.wacc" in {
    val path = constructPath(List("extensions", "void_types", "invalidVoidSyntax.wacc"))
    throwsSyntaxError(path)
  }

  // TODO: Add more tests for void types

  //

}
