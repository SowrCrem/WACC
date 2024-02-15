package test.frontend.integration.syntax.Pairs

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Pairs extends AnyFlatSpec {

  "syntaxErr - pairs tests: badLookup01.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/pairs/wacc/invalid/syntaxErr/pairs//badLookup01.wacc")
  }

  "syntaxErr - pairs tests: badLookup02.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/pairs/wacc/invalid/syntaxErr/pairs//badLookup02.wacc")
  }

  "syntaxErr - pairs tests: elemOfNonPair.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/pairs/wacc/invalid/syntaxErr/pairs//elemOfNonPair.wacc")
  }

  "syntaxErr - pairs tests: fstNull.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/pairs/wacc/invalid/syntaxErr/pairs//fstNull.wacc")
  }

  "syntaxErr - pairs tests: noNesting.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/pairs/wacc/invalid/syntaxErr/pairs//noNesting.wacc")
  }

  "syntaxErr - pairs tests: sndNull.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/pairs/wacc/invalid/syntaxErr/pairs//sndNull.wacc")
  }

}
