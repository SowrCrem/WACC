package test.frontend.integration.syntax

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

  "syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - variables tests: varNoName.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
