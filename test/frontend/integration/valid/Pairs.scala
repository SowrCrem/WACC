package test.frontend.integration.valid.Pairs

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Pairs extends AnyFlatSpec {

  "valid - pairs tests: checkRefPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//checkRefPair.wacc")
  }

  "valid - pairs tests: createPair02.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//createPair02.wacc")
  }

  "valid - pairs tests: createPair03.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//createPair03.wacc")
  }

  "valid - pairs tests: createPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//createPair.wacc")
  }

  "valid - pairs tests: createRefPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//createRefPair.wacc")
  }

  "valid - pairs tests: free.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//free.wacc")
  }

  "valid - pairs tests: linkedList.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//linkedList.wacc")
  }

  "valid - pairs tests: nestedPairLeftAssign.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//nestedPairLeftAssign.wacc")
  }

  "valid - pairs tests: nestedPairRightExtract.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//nestedPairRightExtract.wacc")
  }

  "valid - pairs tests: nestedPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//nestedPair.wacc")
  }

  "valid - pairs tests: null.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//null.wacc")
  }

  "valid - pairs tests: pairarray.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//pairarray.wacc")
  }

  "valid - pairs tests: pairExchangeArrayOk.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//pairExchangeArrayOk.wacc")
  }

  "valid - pairs tests: printNullPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//printNullPair.wacc")
  }

  "valid - pairs tests: printNull.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//printNull.wacc")
  }

  "valid - pairs tests: printPairOfNulls.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//printPairOfNulls.wacc")
  }

  "valid - pairs tests: printPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//printPair.wacc")
  }

  "valid - pairs tests: readPair.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//readPair.wacc")
  }

  "valid - pairs tests: writeFst.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//writeFst.wacc")
  }

  "valid - pairs tests: writeSnd.wacc" should "return exit code 0" in {
    throwsNoError("valid/pairs/wacc/valid/pairs//writeSnd.wacc")
  }

}
