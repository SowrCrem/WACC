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

class Sequence extends AnyFlatSpec {

  "WACC" should "run valid/basic/sequence/basicSeq.wacc" ignore {
    val path = constructPath(List("valid", "sequence", "basicSeq.wacc"))
    runSucceeds(path)
  }

  it  should "run valid/basic/sequence/basicSeq2.wacc" ignore {
    val path = constructPath(List("valid", "sequence", "basicSeq2.wacc"))
    runSucceeds(path)
  }
  
}
