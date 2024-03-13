package test.extension.unit

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

class VoidTypesUnit extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "parse extensions/void_types/simpleVoid.wacc" in {
    val path = constructPath(List("extensions", "void_types", "simpleVoid.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "compile extensions/void_types/simpleVoid.wacc" in {
    val path = constructPath(List("extensions", "void_types", "simpleVoid.wacc"))
    parsesWithoutSyntaxError(path)
    semanticChecker.reset()
    X86IRGenerator.reset()
    parsesWithoutSemanticError(path)
  }
  
}

