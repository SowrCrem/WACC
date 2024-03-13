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

class Lazy extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "Wacc" should "run valid/lazy/lazyInt.wacc" in {
    val path = constructPath(List("valid", "lazy", "lazyInt.wacc"))
    runSucceeds(path, "", 0)
  }

  it should "run valid/lazy/lazyInt2.wacc" in {
    val path = constructPath(List("valid", "lazy", "lazyInt2.wacc"))

    runSucceeds(path, "", 0)
  }

  it should "run valid/lazy/lazyIntUsed.wacc" in {
    val path = constructPath(List("valid", "lazy", "lazyIntUsed.wacc"))
    runSucceeds(path, "", 0)
  }

  it should "run valid/lazy/lazynessCaches.wacc" in {
    val path = constructPath(List("valid", "lazy", "lazynessCaches.wacc"))
    runSucceeds(path, "", 0)
  }

  it should "run valid/lazy/lazyIntDivZero.wacc" in {
    val path = constructPath(List("valid", "lazy", "lazyIntDivZero.wacc"))
    runSucceeds(path, "", 255)
  }

  it should "run valid/lazy/superLazy.wacc" in {
    val path = constructPath(List("valid", "lazy", "superLazy.wacc"))
    runSucceeds(path, "", 0)
  }

  it should "run valid/lazy/lazyDoesntCookTheCodebase.wacc" in {
    val path = constructPath(List("valid", "lazy", "lazyDoesntCookTheCodebase.wacc"))
    runSucceeds(path, "", 0)
  }
  

}