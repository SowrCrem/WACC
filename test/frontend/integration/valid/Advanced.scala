package test.frontend.integration.valid.Advanced

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Advanced extends AnyFlatSpec {

  "valid - advanced tests: binarySortTree.wacc" should "return exit code 0" in {
    throwsNoError("valid/advanced/binarySortTree.wacc")
  }

  "valid - advanced tests: hashTable.wacc" should "return exit code 0" in {
    throwsNoError("valid/advanced/hashTable.wacc")
  }

  "valid - advanced tests: ticTacToe.wacc" should "return exit code 0" in {
    throwsNoError("valid/advanced/ticTacToe.wacc")
  }

}
