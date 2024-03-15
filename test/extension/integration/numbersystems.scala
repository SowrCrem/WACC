package test.extension.integration

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

class NumberSystems extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run extensions/numbersystems/binIntCalc.wacc" in {
    val path = constructPath(List("extensions", "numbersystems", "binIntCalc.wacc"))
    runSucceeds(path, "Hello!")
  }

  "WACC" should "run extensions/numbersystems/hexIntCalc.wacc" in {
    val path = constructPath(List("extensions", "numbersystems", "hexIntCalc.wacc"))
    runSucceeds(path, "Hello!")
  }

}
