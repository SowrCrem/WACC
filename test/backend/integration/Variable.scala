package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Variable extends AnyFlatSpec with BeforeAndAfterEach{

  override def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run valid/basic/sequence/basicSeq.wacc" in {
    val path = constructPath(List("valid", "sequence", "basicSeq.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/boolDeclaration2.wacc" in {
    val path = constructPath(List("valid", "variables", "boolDeclaration2.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/intDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "intDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/charDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "charDeclaration.wacc"))
    runSucceeds(path)
  } 

  it should "run valid/variables/stringDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "stringDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/longVarNames.wacc" in {
    val path = constructPath(List("valid", "variables", "longVarNames.wacc"))
    runSucceeds(path, 5)
  }

  it should "run valid/variables.stringCarriageReturn.wacc" in {
    val path = constructPath(List("valid", "variables", "stringCarriageReturn.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/negIntDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "negIntDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/emptyStringDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "emptyStringDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/zeroIntDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "zeroIntDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/puncCharDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "puncCharDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables/capCharDeclaration.wacc" in {
    val path = constructPath(List("valid", "variables", "capCharDeclaration.wacc"))
    runSucceeds(path)
  }

  it should "run valid/variables._VarNames.wacc" in {
    val path = constructPath(List("valid", "variables", "_VarNames.wacc"))
    runSucceeds(path, 19)
  }
}
  
