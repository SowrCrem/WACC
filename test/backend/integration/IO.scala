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
    X86IRGenerator.reset()
  }

  it should "run valid/IO/print/print.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "print.wacc"))
    runSucceeds(path, "Hello World!", 0)
  }


  it should "run valid/IO/print/println.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "println.wacc"))
    runSucceeds(path, "Hello World!\n", 0)

  }


  it should "run valid/IO/print/printInt.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "printInt.wacc"))
    runSucceeds(path, "123", 0)

  }

  it should "run valid/IO/print/printBool.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "printBool.wacc"))
    runSucceeds(path, "true", 0)

  }

  it should "run valid/IO/print/printChar.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "printChar.wacc"))
    runSucceeds(path, "a", 0)

  }

  it should "run valid/IO/print/printCharAsString.wacc" ignore {
    val path = constructPath(List("valid", "IO", "print", "printCharAsString.wacc"))
    runSucceeds(path, "a", 0)
  }

  


}