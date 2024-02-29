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

class If extends AnyFlatSpec with BeforeAndAfterEach {
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run valid/if/if1.wacc" in {
    val path = constructPath(List("valid", "if", "if1.wacc"))
    runSucceeds(path, "Hello World!", 0)
  } 

  it should "run valid/if/if2.wacc" in {
    val path = constructPath(List("valid", "if", "if2.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/if3.wacc" in {
    val path = constructPath(List("valid", "if", "if3.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/if4.wacc" in {
    val path = constructPath(List("valid", "if", "if4.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/if5.wacc" in {
    val path = constructPath(List("valid", "if", "if5.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/if6.wacc" in {
    val path = constructPath(List("valid", "if", "if6.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/ifBasic.wacc" in {
    val path = constructPath(List("valid", "if", "ifBasic.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should  "run valid/if/ifTrue.wacc" in {
    val path = constructPath(List("valid", "if", "ifTrue.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/ifFalse.wacc" in {
    val path = constructPath(List("valid", "if", "ifFalse.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }

  it should "run valid/if/whitespace.wacc" in {
    val path = constructPath(List("valid", "if", "whitespace.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }


}