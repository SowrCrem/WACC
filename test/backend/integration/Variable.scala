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

class Variable extends AnyFlatSpec {

  "WACC" should "run valid/basic/sequence/basicSeq.wacc" in {
    runSucceeds("../wacc/valid/variables/boolDeclaration.wacc", "", 0)
  }

  it should "run valid/variables/boolDeclaration2.wacc" in {
    runSucceeds("../wacc/valid/variables/boolDeclaration2.wacc", "", 0)
  }

  it should "run valid/variables/intDeclaration.wacc" in {
    runSucceeds("../wacc/valid/variables/intDeclaration.wacc", "", 0)
  }

  it should "run valid/variables/charDeclaration.wacc" in {
    runSucceeds("../wacc/valid/variables/charDeclaration.wacc", "", 0)
  }



  
}