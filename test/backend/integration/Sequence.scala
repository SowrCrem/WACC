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

class Sequence extends AnyFlatSpec with BeforeAndAfterEach{

  override def afterEach(): Unit = {
    semanticChecker.reset()
  }

  "WACC" should "run valid/basic/sequence/basicSeq.wacc" in {
    val path = constructPath(List("valid", "sequence", "basicSeq.wacc"))
    runSucceeds(path)
  }

  it  should "run valid/basic/sequence/basicSeq2.wacc" in {
    val path = constructPath(List("valid", "sequence", "basicSeq2.wacc"))
    runSucceeds(path)
  }

  it should "run valid/sequence/boolAssignment.wacc" in {
    val path = constructPath(List("valid", "sequence", "boolAssignment.wacc"))
    runSucceeds(path)
  }

  it should "run valid/sequence/charAssignment.wacc" in {
    val path = constructPath(List("valid", "sequence", "charAssignment.wacc"))
    runSucceeds(path)
  }

  it should "run valid/sequence/intAssignment.wacc" in {
    val path = constructPath(List("valid", "sequence", "intAssignment.wacc"))
    runSucceeds(path, "", 20)
  }

  it should "run valid/sequence/stringAssignment.wacc" in {
    val path = constructPath(List("valid", "sequence", "stringAssignment.wacc"))
    runSucceeds(path)
  }




  
}
