package frontend.integration.semantic

import Utils._
import wacc.Main
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class CheckArrays extends AnyFlatSpec {

  "semantic array test: arrayIndexComplexNotInt.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "semantic array test: arrayIndexNotInt.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexNotInt.wacc")
  }

  "semantic array test: arrayMultipleIndexError.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/arrayMultipleIndexError.wacc")
  }

  "semantic array test: badIndex.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/badIndex.wacc")
  }

  "semantic array test: indexUndefIdent.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/indexUndefIdent.wacc")
  }

  "semantic array test: mixingTypesInArrays.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/mixingTypesInArrays.wacc")
  }

  "semantic array test: noArrayCovariance.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/noArrayCovariance.wacc")
  }

  "semantic array test: noStringIndex.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/noStringIndex.wacc")
  }

  "semantic array test: nonMatchingArrays.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/nonMatchingArrays.wacc")
  }

  "semantic array test: wrongArrayDimension.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/wrongArrayDimension.wacc")
  }

  "semantic array test: wrongArrayType.wacc" should "throw a semantic error" in {
    throwsSemanticError("invalid/semanticErr/array/wrongArrayType.wacc")
  }

}
