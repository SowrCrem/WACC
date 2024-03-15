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

class SimpleClassesUnit extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  // Syntax tests

  "WACC" should "parse extensions/simple_classes/emptyClass.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "emptyClass.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/simple_classes/classVoidMethod.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "classVoidMethod.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/simple_classes/simpleClass.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "simpleClass.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/simple_classes/invalidClassMethodCall.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "invalidClassMethodCall.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/simple_classes/publicMemberAccess.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "publicMemberAccess.wacc"))
    parsesWithoutSyntaxError(path)
  }

  it should "parse extensions/simple_classes/privateMemberAccess.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "privateMemberAccess.wacc"))
    parsesWithoutSyntaxError(path)
  }


  // Semantic tests

  it should "analyze extensions/simple_classes/emptyClass.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "emptyClass.wacc"))
    parsesWithoutSyntaxError(path)
    afterEach()
    parsesWithoutSemanticError(path)
  }

  it should "analyze extensions/simple_classes/classVoidMethod.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "classVoidMethod.wacc"))
    parsesWithoutSyntaxError(path)
    afterEach()
    parsesWithoutSemanticError(path)
  }

  it should "analyze extensions/simple_classes/simpleClass.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "simpleClass.wacc"))
    parsesWithoutSyntaxError(path)
    afterEach()
    parsesWithoutSemanticError(path)
  }

  it should "fail analyzing extensions/simple_classes/invalidClassMethodCall.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "invalidClassMethodCall.wacc"))
    throwsSemanticError(path)
  }

  it should "analyze extensions/simple_classes/publicMemberAccess.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "publicMemberAccess.wacc"))
    parsesWithoutSyntaxError(path)
    afterEach()
    parsesWithoutSemanticError(path)
  }

  it should "fail analyzing extensions/simple_classes/privateMemberAccess.wacc" in {
    val path = constructPath(List("extensions", "simple_classes", "privateMemberAccess.wacc"))
    throwsSemanticError(path)
  }
  

}
