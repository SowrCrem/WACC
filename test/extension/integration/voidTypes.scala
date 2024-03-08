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

  "WACC" should "compile extensions/voidTypes/simpleVoid.wacc" in {
    val path = constructPath(List("extensions", "voidTypes", "simpleVoid.wacc"))
    // runSucceeds(path, "Hello!")
    parsesWithoutSyntaxError(path)
       semanticChecker.reset()
    X86IRGenerator.reset()
    parsesWithoutSemanticError(path)
  }
}
