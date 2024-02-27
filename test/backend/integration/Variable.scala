package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import sys.process._
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Variable extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()

  }

  // "WACC" should "run valid/basic/sequence/basicSeq.wacc" in {
  //   runSucceeds("../wacc/valid/variables/boolDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/boolDeclaration2.wacc" in {
  //   runSucceeds("../wacc/valid/variables/boolDeclaration2.wacc", "", 0)
  // }

  // it should "run valid/variables/intDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/intDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/charDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/charDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/stringDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/stringDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/longVarNames.wacc" in {
  //   runSucceeds("../wacc/valid/variables/longVarNames.wacc", "", 5)
  // }

  // it should "run valid/variables.stringCarriageReturn.wacc" in {
  //   runSucceeds("../wacc/valid/variables/stringCarriageReturn.wacc", "", 0)
  // }

  // it should "run valid/variables/negIntDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/negIntDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/emptyStringDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/emptyStringDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/zeroIntDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/zeroIntDeclaration.wacc", "", 0)
  // }

  // it should "run valid/manyVariables.wacc" in {
  //   runSucceeds("../wacc/valid/variables/manyVariables.wacc", "", 0)
  // }

  // it should "run valid/variables/puncCharDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/puncCharDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables/capCharDeclaration.wacc" in {
  //   runSucceeds("../wacc/valid/variables/capCharDeclaration.wacc", "", 0)
  // }

  // it should "run valid/variables._VarNames.wacc" in {
  //   runSucceeds("../wacc/valid/variables/_VarNames.wacc", "", 19)
  // }


}
  
