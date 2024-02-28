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

class Loop extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

    it should "run valid/while/loopIntCondition.wacc" in {
        val path = constructPath(List("valid", "while", "loopIntCondition.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/while/loopCharCondition.wacc" in {
        val path = constructPath(List("valid", "while", "loopCharCondition.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/while/whileBasic.wacc" in {
        val path = constructPath(List("valid", "while", "whileBasic.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/while/whileFalse.wacc" in {
        val path = constructPath(List("valid", "while", "whileFalse.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/while/whileCount.wacc" in {
        val path = constructPath(List("valid", "while", "whileCount.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    // test/wacc/valid/while/rmStyleAdd.wacc
    it should "run valid/while/rmStyleAdd.wacc" in {
        val path = constructPath(List("valid", "while", "rmStyleAdd.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }


}
  
