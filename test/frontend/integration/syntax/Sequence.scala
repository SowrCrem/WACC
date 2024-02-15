package test.frontend.integration.syntax.Sequence

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Sequence extends AnyFlatSpec {

  "syntaxErr - sequence tests: doubleSeq.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/sequence/wacc/invalid/syntaxErr/sequence//doubleSeq.wacc")
  }

  "syntaxErr - sequence tests: emptySeq.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/sequence/wacc/invalid/syntaxErr/sequence//emptySeq.wacc")
  }

  "syntaxErr - sequence tests: endSeq.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/sequence/wacc/invalid/syntaxErr/sequence//endSeq.wacc")
  }

  "syntaxErr - sequence tests: extraSeq.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/sequence/wacc/invalid/syntaxErr/sequence//extraSeq.wacc")
  }

  "syntaxErr - sequence tests: missingSeq.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/sequence/wacc/invalid/syntaxErr/sequence//missingSeq.wacc")
  }

}
