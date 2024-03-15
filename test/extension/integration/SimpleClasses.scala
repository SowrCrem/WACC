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

class SimpleClasses extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run extensions/simple_classes/emptyClass.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "emptyClass.wacc"))
    runSucceeds(path)
  }

  it should "run extensions/simple_classes/classVoidMethod.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "classVoidMethod.wacc"))
    runSucceeds(path)
  }

  it should "run extensions/simple_classes/simpleClass.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "simpleClass.wacc"))
    runSucceeds(path)
  }

  it should "run extensions/simple_classes/publicMemberAccess.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "publicMemberAccess.wacc"))
    runSucceeds(path)
  }

  // TODO: Add more tests for simple classes
}

