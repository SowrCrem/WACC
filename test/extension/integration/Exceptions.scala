package test.integration.extension.Exceptions

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Exceptions extends AnyFlatSpec {
  /* Front end syntax tests */
  "valid - exceptions tests: simpletrycatch.wacc" should "return exit code 0" in {
    throwsNoError("extensions/exceptions/simpletrycatch.wacc")
  }

  "syntax - exceptions tests: nocurlybraces.wacc" should "return exit code 100" in {
    throwsSyntaxError("extensions/exceptions/nocurlybraces.wacc")
  } 

  "valid - exceptions tests: nestedtrycatch.wacc" should "return exit code 0" in {
    throwsNoError("extensions/exceptions/nestedtrycatch.wacc")
  }

  "valid - exceptions tests: catchdivbyzero.wacc" should "return exit code 0" in {
    throwsNoError("extensions/exceptions/catchdivbyzero.wacc")
  }
  
  "valid - exceptions tests: multiplecatch.wacc" should "return exit code 0" in {
    throwsNoError("extensions/exceptions/multiplecatch.wacc")
  }

  "semantic - exceptions tests: repeatedcatch.wacc" should "return exit code 200" in {
    throwsSemanticError("extensions/exceptions/repeatedcatch.wacc")
  }
}
