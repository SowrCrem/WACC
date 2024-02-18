package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Basic extends AnyFlatSpec {

  "WACC" should "run valid/basic/exit/exit-1.wacc" in {
    runSucceeds("valid/basic/exit/exit-1.wacc", "", 255)
  }

  it should "run valid/basic/exit/exitBasic.wacc" in {
    runSucceeds("valid/basic/exit/exitBasic.wacc", "", 7)
  }

  it should "run valid/basic/exit/exitBasic2.wacc" in {
    runSucceeds("valid/basic/exit/exitBasic2.wacc", "", 42)
  }

  it should "run valid/basic/exit/exitWrap.wacc" in {
    runSucceeds("valid/basic/exit/exitWrap.wacc")
  }

  it should "run valid/basic/skip/comment.wacc" ignore {
    runSucceeds("valid/basic/skip/comment.wacc")
  }

  it should "run valid/basic/skip/commentEoF.wacc" ignore {
    runSucceeds("valid/basic/skip/commentEoF.wacc")
  }

  it should "run valid/basic/skip/commentInLine.wacc" ignore {
    runSucceeds("valid/basic/skip/commentInLine.wacc")
  }

  it should "run valid/basic/skip/skip.wacc" ignore {
    runSucceeds("valid/basic/skip/skip.wacc")
  }
}
