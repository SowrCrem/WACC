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

class Pairs extends AnyFlatSpec with BeforeAndAfterEach {
  
    override protected def afterEach(): Unit = {
      semanticChecker.reset()
      X86IRGenerator.reset()
    }

  // "WACC" should "run valid/pair/checkRefPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "checkRefPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/createPair02.wacc" in {
  //   val path = constructPath(List("valid", "pair", "createPair02.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/createRefPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "createRefPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/linkedList.wacc" in {
  //   val path = constructPath(List("valid", "pair", "linkedList.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/nestedPairLeftAssign.wacc" in {
  //   val path = constructPath(List("valid", "pair", "nestedPairLeftAssign.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/null.wacc" in {
  //   val path = constructPath(List("valid", "pair", "null.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/pairarray.wacc" in {
  //   val path = constructPath(List("valid", "pair", "pairarray.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/printNullPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "printNullPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/printPairOfNulls.wacc" in {
  //   val path = constructPath(List("valid", "pair", "printPairOfNulls.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/writeFst.wacc" in {
  //   val path = constructPath(List("valid", "pair", "writeFst.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/createPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "createPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/createPair03.wacc" in {
  //   val path = constructPath(List("valid", "pair", "createPair03.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/free.wacc" in {
  //   val path = constructPath(List("valid", "pair", "free.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/nestedPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "nestedPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/nestedPairRightExtract.wacc" in {
  //   val path = constructPath(List("valid", "pair", "nestedPairRightExtract.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/pairExchangeArrayOk.wacc" in {
  //   val path = constructPath(List("valid", "pair", "pairExchangeArrayOk.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/printNull.wacc" in {
  //   val path = constructPath(List("valid", "pair", "printNull.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/printPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "printPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/readPair.wacc" in {
  //   val path = constructPath(List("valid", "pair", "readPair.wacc"))
  //   runSucceeds(path, "", 0)
  // }

  // it should "run valid/pair/writeSnd.wacc" in {
  //   val path = constructPath(List("valid", "pair", "writeSnd.wacc"))
  //   runSucceeds(path, "", 0)
  // }  
  
}