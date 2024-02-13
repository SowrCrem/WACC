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

class If extends AnyFlatSpec {

  "semanticErr - if tests: ifIntCondition.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
