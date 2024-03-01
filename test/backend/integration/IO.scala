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

  // Tried to Fix - Still Hanging (Use ./run.sh filepath inputs to run instead - Command written as comments in each test)

  "WACC" should "run valid/IO/read/read.wacc" in {
    // run.sh valid/IO/read/read.wacc 3
    pending
    val path = constructPath(List("valid", "IO", "read", "read.wacc"))
    val expectedOut = "3"
    val inputs = List("3")
    runSucceedsWithInputs(path, inputs, expectedOut)
  }

  "WACC" should "run valid/IO/IOLoop.wacc" in {
    // run.sh valid/IO/IOLoop.wacc 1 Y 2 Y 3 Y 4 Y 5 Y 142 N
    pending
    val path = constructPath(List("valid", "IO", "IOLoop.wacc"))
    val expectedOut = "3"
    val inputs = List("1", "Y", "2", "Y", "3", "Y", "4", "Y", "5", "Y", "142", "N")
    runSucceedsWithInputs(path, inputs, expectedOut)
  }

  // echoBigInt.wacc  echoBigNegInt.wacc  echoChar.wacc  echoInt.wacc  echoNegInt.wacc  echoPuncChar.wacc  read.wacc  readAtEof.wacc

  // it should "run valid/IO/print/multipleStringAssignment.wacc" in {
  //   val path = constructPath(List("valid", "IO", "print", "multipleStringAssignment.wacc"))
  //   val expectedOut = "s1 is Hi\ns2 is Hello\nThey are not the same string.\nNow make s1 = s2" +
  //                     "\ns1 is Hello\ns2 is Hello\nThey are the same string."
  //   runSucceeds(path, expectedOut, 0)
  // }

      // ARRAYS NOT YET IMPLEMENTED

  it should "run valid/IO/print/printCharAsString.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "printCharAsString.wacc"))
    val expectedOut = "foo\nbar"
    runSucceeds(path, expectedOut, 0)
  }

  it should "run valid/IO/print/printCharArray.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "printCharArray.wacc"))
    runSucceeds(path, "hi!", 0)
  }

  it should "run valid/IO/print/printEscChar.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "printEscChar.wacc"))
    runSucceeds(path, "An escaped character example is \"", 0)
  }

  it should "run valid/IO/print/print-backspace.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "print-backspace.wacc"))
    val expectedOut = "Hello\b World!"
    runSucceeds(path, expectedOut, 0)
  }

  it should "run valid/IO/print/hashInProgram.wacc" in {
    val path = constructPath(List("valid", "IO", "print", "hashInProgram.wacc"))
    val expectedOut = "We can print the hash character: #" +
                      "\nWe can also print # when its in a string."
    runSucceeds(path, expectedOut, 0)
  }

  "WACC" should "run valid/IO/print/print.wacc" in {
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

}