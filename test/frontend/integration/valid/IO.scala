package test.frontend.integration.valid.IO

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class IO extends AnyFlatSpec {

  "valid - IO tests: IOLoop.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/IOLoop.wacc")
  }

  "valid - IO tests: IOSequence.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/IOSequence.wacc")
  }

  "valid - print tests: hashInProgram.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/hashInProgram.wacc")
  }

  "valid - print tests: multipleStringsAssignment.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/multipleStringsAssignment.wacc")
  }

  "valid - print tests: print.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/print.wacc")
  }

  "valid - print tests: print-backspace.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/print-backspace.wacc")
  }

  "valid - print tests: printBool.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/printBool.wacc")
  }

  "valid - print tests: printChar.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/printChar.wacc")
  }

  "valid - print tests: printCharArray.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/printCharArray.wacc")
  }

  "valid - print tests: printCharAsString.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/printCharAsString.wacc")
  }

  "valid - print tests: printEscChar.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/printEscChar.wacc")
  }

  "valid - print tests: printInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/printInt.wacc")
  }

  "valid - print tests: println.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/print/println.wacc")
  }

  "valid - read tests: echoBigInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/echoBigInt.wacc")
  }

  "valid - read tests: echoBigNegInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/echoBigNegInt.wacc")
  }

  "valid - read tests: echoChar.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/echoChar.wacc")
  }

  "valid - read tests: echoInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/echoInt.wacc")
  }

  "valid - read tests: echoNegInt.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/echoNegInt.wacc")
  }

  "valid - read tests: echoPuncChar.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/echoPuncChar.wacc")
  }

  "valid - read tests: read.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/read.wacc")
  }

  "valid - read tests: readAtEof.wacc" should "return exit code 0" in {
    throwsNoError("valid/IO/read/readAtEof.wacc")
  }

}
