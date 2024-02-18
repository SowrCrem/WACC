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

class Basic extends AnyFlatSpec {

  "WACC" should "run valid/basic/exit/exit-1.wacc" in {
    runSucceeds("valid/basic/exit/exit-1.wacc", "", 255)
  }

  it should "run valid/basic/exitBasic.wacc" in {
    runSucceeds("valid/basic/exit/exitBasic.wacc", "", 7)
  }

  it should "run valid/if/if1.wacc" ignore {
    runSucceeds("valid/if/if1.wacc", "correct")
  }
}
