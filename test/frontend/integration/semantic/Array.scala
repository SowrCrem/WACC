package test.frontend.integration.semantic.Array

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Array extends AnyFlatSpec {

  "semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semanticErr - array tests: arrayIndexNotInt.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexNotInt.wacc")
  }

  "semanticErr - array tests: arrayMultipleIndexError.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayMultipleIndexError.wacc")
  }

  "semanticErr - array tests: badIndex.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/badIndex.wacc")
  }

  "semanticErr - array tests: indexUndefIdent.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/indexUndefIdent.wacc")
  }

  "semanticErr - array tests: mixingTypesInArrays.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/mixingTypesInArrays.wacc")
  }

  "semanticErr - array tests: noArrayCovariance.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/noArrayCovariance.wacc")
  }

  "semanticErr - array tests: noStringIndex.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/noStringIndex.wacc")
  }

  "semanticErr - array tests: nonMatchingArrays.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/nonMatchingArrays.wacc")
  }

  "semanticErr - array tests: wrongArrayDimension.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/wrongArrayDimension.wacc")
  }

  "semanticErr - array tests: wrongArrayType.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/wrongArrayType.wacc")
  }

}
