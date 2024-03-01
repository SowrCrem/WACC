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

class RuntimeErr extends AnyFlatSpec with BeforeAndAfterEach {

  val RUNTIME_ERR = 255
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  // arrayOutOfBounds Tests ----------------------------------------------------------------------------

  "WACC" should "run valid/runtimeErr/arrayOutOfBounds/arrayNegBounds.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "arrayOutOfBounds", "arrayNegBounds.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/arrayOutOfBounds/arrayOutOfBounds.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "arrayOutOfBounds", "arrayOutOfBounds.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/arrayOutOfBounds/arrayOutOfBoundsWrite.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "arrayOutOfBounds", "arrayOutOfBoundsWrite.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  // badChar Tests ----------------------------------------------------------------------------

  it should "run valid/runtimeErr/badChar/negativeChr.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "badChar", "negativeChr.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/badChar/tooBigChar.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "badChar", "tooBigChar.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  // divideByZero Tests ----------------------------------------------------------------------------

  it should "run valid/runtimeErr/divideByZero/divideByZero.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "divideByZero", "divideByZero.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/divideByZero/divZero.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "divideByZero", "divZero.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/divideByZero/modByZero.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "divideByZero", "modByZero.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  // integerOverflow Tests ----------------------------------------------------------------------------

  it should "run valid/runtimeErr/integerOverflow/intJustOverflow.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intJustOverflow.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intUnderflow.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intUnderflow.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intWayOverflow.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intWayOverflow.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intmultOverflow.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intmultOverflow.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intnegateOverflow.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intnegateOverflow.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intnegateOverflow2.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intnegateOverflow2.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intnegateOverflow3.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intnegateOverflow3.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/integerOverflow/intnegateOverflow4.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "integerOverflow", "intnegateOverflow4.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  // nullDereference Tests ----------------------------------------------------------------------------

  it should "run valid/runtimeErr/nullDereference/freeNull.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "freeNull.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/nullDereference/readNull1.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "readNull1.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/nullDereference/readNull2.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "readNull2.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/nullDereference/setNull1.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "setNull1.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/nullDereference/setNull2.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "setNull2.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/nullDereference/useNull1.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "useNull1.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }

  it should "run valid/runtimeErr/nullDereference/useNull2.wacc" in {
    val path = constructPath(List("valid", "runtimeErr", "nullDereference", "useNull2.wacc"))
    runSucceeds(path, RUNTIME_ERR)
  }
}
