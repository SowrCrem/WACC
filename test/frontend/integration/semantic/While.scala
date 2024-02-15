package test.frontend.integration.semantic.While

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class While extends AnyFlatSpec {

  "semanticErr - while tests: falsErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/while/falsErr.wacc")
  }

  "semanticErr - while tests: truErr.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/while/truErr.wacc")
  }

  "semanticErr - while tests: whileIntCondition.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/while/whileIntCondition.wacc")
  }

}
