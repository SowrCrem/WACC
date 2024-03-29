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

class ExceptionsBackend extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "Wacc" should "run extensions/exceptions/catchdivbyzero.wacc" in {
    val path = constructPath(List("extensions", "exceptions", "catchdivbyzero.wacc"))
    runSucceeds(path, "", 0)
  }

  it should "run extensions/exceptions/simpletrycatch.wacc" in {
    val path = constructPath(List("extensions", "exceptions", "simpletrycatch.wacc"))
    runSucceeds(path, "", 0)
  }

  it should "run extensions/exceptions/catchIndexOutOfBounds.wacc" in {
    val path = constructPath(List("extensions", "exceptions", "catchIndexOutOfBounds.wacc"))
    runSucceeds(path, "", 0)
  }
  
}