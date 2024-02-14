package test.frontend.integration.semantic

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
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr02.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr03.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr04.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr05.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr06.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr07.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr08.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr09.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr10.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr11.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: basicTypeErr12.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: caseMatters.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: doubleDeclare.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: undeclaredScopeVar.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: undeclaredVar.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - variables tests: undeclaredVarAccess.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
