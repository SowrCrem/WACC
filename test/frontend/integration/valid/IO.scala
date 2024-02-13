package test.frontend.integration.valid

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
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - IO tests: IOSequence.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: hashInProgram.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: multipleStringsAssignment.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: print-backspace.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: print.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: printBool.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: printChar.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: printCharArray.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: printCharAsString.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: printEscChar.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: printInt.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - print tests: println.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: echoBigInt.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: echoBigNegInt.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: echoChar.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: echoInt.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: echoNegInt.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: echoPuncChar.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: read.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "valid - read tests: readAtEof.wacc" should "return exit code 0" in {
    throwsNoError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
