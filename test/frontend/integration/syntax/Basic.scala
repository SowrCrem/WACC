package test.frontend.integration.syntax.Basic

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Basic extends AnyFlatSpec {

  "syntaxErr - basic tests: badComment2.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//badComment2.wacc")
  }

  "syntaxErr - basic tests: badComment.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//badComment.wacc")
  }

  "syntaxErr - basic tests: badEscape.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//badEscape.wacc")
  }

  "syntaxErr - basic tests: beginNoend.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//beginNoend.wacc")
  }

  "syntaxErr - basic tests: bgnErr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//bgnErr.wacc")
  }

  "syntaxErr - basic tests: multipleBegins.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//multipleBegins.wacc")
  }

  "syntaxErr - basic tests: noBody.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//noBody.wacc")
  }

  "syntaxErr - basic tests: skpErr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//skpErr.wacc")
  }

  "syntaxErr - basic tests: unescapedChar.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/basic/wacc/invalid/syntaxErr/basic//unescapedChar.wacc")
  }

}
