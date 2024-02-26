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

class IO extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()

  }

  "WACC" should "run valid/basic/exit/exit-1.wacc" in {
    val path = constructPath(List("valid", "basic", "exit", "exit-1.wacc"))
    runSucceeds(path, "", 255)
  }

  it should "run valid/IO/print/print.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "print.wacc"))
    runSucceeds(path, "Hello World!", 0)

  }


}