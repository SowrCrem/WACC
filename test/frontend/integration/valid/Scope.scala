package test.frontend.integration.valid.Scope

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Scope extends AnyFlatSpec {

  "valid - scope tests: ifNested1.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/ifNested1.wacc")
  }

  "valid - scope tests: ifNested2.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/ifNested2.wacc")
  }

  "valid - scope tests: indentationNotImportant.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/indentationNotImportant.wacc")
  }

  "valid - scope tests: intsAndKeywords.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/intsAndKeywords.wacc")
  }

  "valid - scope tests: printAllTypes.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/printAllTypes.wacc")
  }

  "valid - scope tests: scopeBasic.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeBasic.wacc")
  }

  "valid - scope tests: scopeIfRedefine.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeIfRedefine.wacc")
  }

  "valid - scope tests: scopeRedefine.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeRedefine.wacc")
  }

  "valid - scope tests: scopeSimpleRedefine.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeSimpleRedefine.wacc")
  }

  "valid - scope tests: scopeVars.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeVars.wacc")
  }

  "valid - scope tests: scope.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scope.wacc")
  }

  "valid - scope tests: scopeWhileNested.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeWhileNested.wacc")
  }

  "valid - scope tests: scopeWhileRedefine.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/scopeWhileRedefine.wacc")
  }

  "valid - scope tests: splitScope.wacc" should "return exit code 0" in {
    throwsNoError("valid/scope/splitScope.wacc")
  }

}
