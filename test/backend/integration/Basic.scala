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
  "The compiler" should "compile valid/basic/exit/exit-1.wacc" ignore {
    runSucceeds("valid/basic/exit-1.wacc", "", "255")
  }

  it should "compile valid/basic/exitBasic.wacc" ignore {
    runSucceeds("valid/basic/exit/exitBasic.wacc", "", "7")
  }
}
