package test.frontend.integration.semantic.Variables

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Variables extends AnyFlatSpec {

  "semanticErr - variables tests: basicTypeErr01.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr01.wacc")
  }

  "semanticErr - variables tests: basicTypeErr02.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr02.wacc")
  }

  "semanticErr - variables tests: basicTypeErr03.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr03.wacc")
  }

  "semanticErr - variables tests: basicTypeErr04.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr04.wacc")
  }

  "semanticErr - variables tests: basicTypeErr05.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr05.wacc")
  }

  "semanticErr - variables tests: basicTypeErr06.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr06.wacc")
  }

  "semanticErr - variables tests: basicTypeErr07.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr07.wacc")
  }

  "semanticErr - variables tests: basicTypeErr08.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr08.wacc")
  }

  "semanticErr - variables tests: basicTypeErr09.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr09.wacc")
  }

  "semanticErr - variables tests: basicTypeErr10.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr10.wacc")
  }

  "semanticErr - variables tests: basicTypeErr11.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr11.wacc")
  }

  "semanticErr - variables tests: basicTypeErr12.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/basicTypeErr12.wacc")
  }

  "semanticErr - variables tests: caseMatters.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/caseMatters.wacc")
  }

  "semanticErr - variables tests: doubleDeclare.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/doubleDeclare.wacc")
  }

  "semanticErr - variables tests: undeclaredScopeVar.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/undeclaredScopeVar.wacc")
  }

  "semanticErr - variables tests: undeclaredVarAccess.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/undeclaredVarAccess.wacc")
  }

  "semanticErr - variables tests: undeclaredVar.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/variables/undeclaredVar.wacc")
  }

}
