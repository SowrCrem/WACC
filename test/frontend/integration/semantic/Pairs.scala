package test.frontend.integration.semantic.Pairs

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Pairs extends AnyFlatSpec {

  "semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/badPairAssign.wacc")
  }

  "semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/badPairExchange.wacc")
  }

  "semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/freeNonPair.wacc")
  }

  "semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/mismatchedPair.wacc")
  }

  "semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/noPairCovariance.wacc")
  }

  "semanticErr - pairs tests: nonMatchingPairs.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/nonMatchingPairs.wacc")
  }

  "semanticErr - pairs tests: readUnknown.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/readUnknown.wacc")
  }

  "semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/pairs/wrongTypeInParameterlessPair.wacc")
  }

}
