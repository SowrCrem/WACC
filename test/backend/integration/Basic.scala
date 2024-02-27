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
    val path = constructPath(List("valid", "basic", "exit", "exit-1.wacc"))
    runSucceeds(path, "", 255)
  }

  it should "run valid/basic/exit/exitBasic.wacc" in {
    val path = constructPath(List("valid", "basic", "exit", "exitBasic.wacc"))
    runSucceeds(path, "", 7)
  }

  it should "run valid/basic/exit/exitBasic2.wacc" in {
    val path = constructPath(List("valid", "basic", "exit", "exitBasic2.wacc"))
    runSucceeds(path, "", 42)
  }

  it should "run valid/basic/exit/exitWrap.wacc" in {
    // runSucceeds("valid/basic/exit/exitWrap.wacc")
    val path = constructPath(List("valid", "basic", "exit", "exitWrap.wacc"))
    runSucceeds(path)
  }

  it should "run valid/basic/skip/comment.wacc" in {
    val path = constructPath(List("valid", "basic", "skip", "comment.wacc"))

    runSucceeds("valid/basic/skip/comment.wacc")
  }

  it should "run valid/basic/skip/commentEoF.wacc" in{
    runSucceeds("valid/basic/skip/commentEoF.wacc")
  }

  it should "run valid/basic/skip/commentInLine.wacc" in {
    runSucceeds("valid/basic/skip/commentInLine.wacc")
  }

  it should "run valid/basic/skip/skip.wacc" in {
    runSucceeds("valid/basic/skip/skip.wacc")
  }
}
