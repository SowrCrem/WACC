package test.frontend.integration.syntax.Exceptions

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

  "syntaxErr - exceptions tests: nocurlybraces.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/exceptions/nocurlybraces.wacc")
  }

}
