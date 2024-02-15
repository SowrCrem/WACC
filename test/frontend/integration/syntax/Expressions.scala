package test.frontend.integration.syntax.Expressions

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Expressions extends AnyFlatSpec {

  "syntaxErr - expressions tests: missingOperand1.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/expressions/wacc/invalid/syntaxErr/expressions//missingOperand1.wacc")
  }

  "syntaxErr - expressions tests: missingOperand2.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/expressions/wacc/invalid/syntaxErr/expressions//missingOperand2.wacc")
  }

  "syntaxErr - expressions tests: printlnConcat.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/expressions/wacc/invalid/syntaxErr/expressions//printlnConcat.wacc")
  }

}
