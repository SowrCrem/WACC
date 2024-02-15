package test.frontend.integration.syntax.Literals

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Literals extends AnyFlatSpec {

  "syntaxErr - literals tests: charLiteralSingle.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/literals/charLiteralSingle.wacc")
  }

  "syntaxErr - literals tests: stringLiteralNoNewlines.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/literals/stringLiteralNoNewlines.wacc")
  }

  "syntaxErr - literals tests: stringLiteralOnlyAscii.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/literals/stringLiteralOnlyAscii.wacc")
  }

}
