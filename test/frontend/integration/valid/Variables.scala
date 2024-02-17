package test.frontend.integration.valid.Variables

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

  "valid - variables tests: _VarNames.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/_VarNames.wacc")
  }

  "valid - variables tests: boolDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/boolDeclaration.wacc")
  }

  "valid - variables tests: boolDeclaration2.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/boolDeclaration2.wacc")
  }

  "valid - variables tests: capCharDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/capCharDeclaration.wacc")
  }

  "valid - variables tests: charDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/charDeclaration.wacc")
  }

  "valid - variables tests: charDeclaration2.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/charDeclaration2.wacc")
  }

  "valid - variables tests: emptyStringDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/emptyStringDeclaration.wacc")
  }

  "valid - variables tests: intDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/intDeclaration.wacc")
  }

  "valid - variables tests: longVarNames.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/longVarNames.wacc")
  }

  "valid - variables tests: manyVariables.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/manyVariables.wacc")
  }

  "valid - variables tests: negIntDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/negIntDeclaration.wacc")
  }

  "valid - variables tests: puncCharDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/puncCharDeclaration.wacc")
  }

  "valid - variables tests: stringCarriageReturn.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/stringCarriageReturn.wacc")
  }

  "valid - variables tests: stringDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/stringDeclaration.wacc")
  }

  "valid - variables tests: zeroIntDeclaration.wacc" should "return exit code 0" in {
    throwsNoError("valid/variables/zeroIntDeclaration.wacc")
  }

}
