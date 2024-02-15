package test.frontend.integration.syntax.Variables

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

  "syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/variables/wacc/invalid/syntaxErr/variables//badintAssignments1.wacc")
  }

  "syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/variables/wacc/invalid/syntaxErr/variables//badintAssignments2.wacc")
  }

  "syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/variables/wacc/invalid/syntaxErr/variables//badintAssignments.wacc")
  }

  "syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/variables/wacc/invalid/syntaxErr/variables//bigIntAssignment.wacc")
  }

  "syntaxErr - variables tests: varNoName.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/variables/wacc/invalid/syntaxErr/variables//varNoName.wacc")
  }

}
