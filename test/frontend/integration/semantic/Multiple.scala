package test.frontend.integration.semantic.Multiple

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Multiple extends AnyFlatSpec {

  "semanticErr - multiple tests: funcMess.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/multiple/wacc/invalid/semanticErr/multiple//funcMess.wacc")
  }

  "semanticErr - multiple tests: ifAndWhileErrs.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/multiple/wacc/invalid/semanticErr/multiple//ifAndWhileErrs.wacc")
  }

  "semanticErr - multiple tests: messyExpr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/multiple/wacc/invalid/semanticErr/multiple//messyExpr.wacc")
  }

  "semanticErr - multiple tests: multiCaseSensitivity.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/multiple/wacc/invalid/semanticErr/multiple//multiCaseSensitivity.wacc")
  }

  "semanticErr - multiple tests: multiTypeErrs.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/multiple/wacc/invalid/semanticErr/multiple//multiTypeErrs.wacc")
  }

  "semanticErr - multiple tests: obfuscatingReturnsWithWhile.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/multiple/wacc/invalid/semanticErr/multiple//obfuscatingReturnsWithWhile.wacc")
  }

}
