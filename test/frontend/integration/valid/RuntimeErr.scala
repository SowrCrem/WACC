package test.frontend.integration.valid.RuntimeErr

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class RuntimeErr extends AnyFlatSpec {

  "valid - arrayOutOfBounds tests: arrayNegBounds.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/arrayOutOfBounds/wacc/valid/runtimeErr//arrayOutOfBounds/arrayNegBounds.wacc")
  }

  "valid - arrayOutOfBounds tests: arrayOutOfBounds.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/arrayOutOfBounds/wacc/valid/runtimeErr//arrayOutOfBounds/arrayOutOfBounds.wacc")
  }

  "valid - arrayOutOfBounds tests: arrayOutOfBoundsWrite.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/arrayOutOfBounds/wacc/valid/runtimeErr//arrayOutOfBounds/arrayOutOfBoundsWrite.wacc")
  }

  "valid - badChar tests: negativeChr.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/badChar/wacc/valid/runtimeErr//badChar/negativeChr.wacc")
  }

  "valid - badChar tests: tooBigChr.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/badChar/wacc/valid/runtimeErr//badChar/tooBigChr.wacc")
  }

  "valid - divideByZero tests: divideByZero.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/divideByZero/wacc/valid/runtimeErr//divideByZero/divideByZero.wacc")
  }

  "valid - divideByZero tests: divZero.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/divideByZero/wacc/valid/runtimeErr//divideByZero/divZero.wacc")
  }

  "valid - divideByZero tests: modByZero.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/divideByZero/wacc/valid/runtimeErr//divideByZero/modByZero.wacc")
  }

  "valid - integerOverflow tests: intJustOverflow.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intJustOverflow.wacc")
  }

  "valid - integerOverflow tests: intmultOverflow.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intmultOverflow.wacc")
  }

  "valid - integerOverflow tests: intnegateOverflow2.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intnegateOverflow2.wacc")
  }

  "valid - integerOverflow tests: intnegateOverflow3.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intnegateOverflow3.wacc")
  }

  "valid - integerOverflow tests: intnegateOverflow4.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intnegateOverflow4.wacc")
  }

  "valid - integerOverflow tests: intnegateOverflow.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intnegateOverflow.wacc")
  }

  "valid - integerOverflow tests: intUnderflow.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intUnderflow.wacc")
  }

  "valid - integerOverflow tests: intWayOverflow.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/integerOverflow/wacc/valid/runtimeErr//integerOverflow/intWayOverflow.wacc")
  }

  "valid - nullDereference tests: freeNull.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/freeNull.wacc")
  }

  "valid - nullDereference tests: readNull1.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/readNull1.wacc")
  }

  "valid - nullDereference tests: readNull2.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/readNull2.wacc")
  }

  "valid - nullDereference tests: setNull1.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/setNull1.wacc")
  }

  "valid - nullDereference tests: setNull2.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/setNull2.wacc")
  }

  "valid - nullDereference tests: useNull1.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/useNull1.wacc")
  }

  "valid - nullDereference tests: useNull2.wacc" should "return exit code 0" in {
    throwsNoError("valid/runtimeErr/nullDereference/wacc/valid/runtimeErr//nullDereference/useNull2.wacc")
  }

}
