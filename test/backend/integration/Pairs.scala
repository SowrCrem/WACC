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

  "WACC" should "run valid/pairs/checkRefPair.wacc" in {
    val path = constructPath(List("valid", "pairs", "checkRefPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/createPair02.wacc" in {
    val path = constructPath(List("valid", "pairs", "createPair02.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/createRefPair.wacc" in {
    val path = constructPath(List("valid", "pairs", "createRefPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/linkedList.wacc" ignore {
    val path = constructPath(List("valid", "pairs", "linkedList.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/nestedPairLeftAssign.wacc" in {
    val path = constructPath(List("valid", "pairs", "nestedPairLeftAssign.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/null.wacc" in {
    val path = constructPath(List("valid", "pairs", "null.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/pairarray.wacc" in {
    val path = constructPath(List("valid", "pairs", "pairarray.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/printNullPair.wacc" in {
    val path = constructPath(List("valid", "pairs", "printNullPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/printPairOfNulls.wacc" in {
    val path = constructPath(List("valid", "pairs", "printPairOfNulls.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/writeFst.wacc" in {
    val path = constructPath(List("valid", "pairs", "writeFst.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/createPair.wacc" in {
    val path = constructPath(List("valid", "pairs", "createPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/createPair03.wacc" in {
    val path = constructPath(List("valid", "pairs", "createPair03.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/free.wacc" in {
    val path = constructPath(List("valid", "pairs", "free.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/nestedPair.wacc" in {
    val path = constructPath(List("valid", "pairs", "nestedPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/nestedPairRightExtract.wacc" in {
    val path = constructPath(List("valid", "pairs", "nestedPairRightExtract.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/pairExchangeArrayOk.wacc" in {
    val path = constructPath(List("valid", "pairs", "pairExchangeArrayOk.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/printNull.wacc" in {
    val path = constructPath(List("valid", "pairs", "printNull.wacc"))
    runSucceeds(path, "(nil)", 0)
  }

  it should "run valid/pairs/printPair.wacc" in {
    val path = constructPath(List("valid", "pairs", "printPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/readPair.wacc" ignore {
    val path = constructPath(List("valid", "pairs", "readPair.wacc"))
    runSucceeds(path)
  }

  it should "run valid/pairs/writeSnd.wacc" in {
    val path = constructPath(List("valid", "pairs", "writeSnd.wacc"))
    runSucceeds(path)
  }  
  
}