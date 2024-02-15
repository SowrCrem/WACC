package test.frontend.integration.semantic.Expressions

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

  "semanticErr - expressions tests: boolOpTypeErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/boolOpTypeErr.wacc")
  }

  "semanticErr - expressions tests: exprTypeErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/exprTypeErr.wacc")
  }

  "semanticErr - expressions tests: intOpTypeErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/intOpTypeErr.wacc")
  }

  "semanticErr - expressions tests: lessPairExpr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/lessPairExpr.wacc")
  }

  "semanticErr - expressions tests: mixedOpTypeErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/mixedOpTypeErr.wacc")
  }

  "semanticErr - expressions tests: moreArrExpr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/moreArrExpr.wacc")
  }

  "semanticErr - expressions tests: stringElemErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/expressions/stringElemErr.wacc")
  }

}
