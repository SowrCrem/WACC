import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidRuntimeErrTests extends AnyFlatSpec {

  "valid - arrayOutOfBounds tests: arrayNegBounds.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/arrayOutOfBounds/arrayNegBounds.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - arrayOutOfBounds tests: arrayOutOfBounds.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/arrayOutOfBounds/arrayOutOfBounds.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - arrayOutOfBounds tests: arrayOutOfBoundsWrite.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/arrayOutOfBounds/arrayOutOfBoundsWrite.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - badChar tests: negativeChr.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/badChar/negativeChr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - badChar tests: tooBigChr.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/badChar/tooBigChr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - divideByZero tests: divideByZero.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/divideByZero/divideByZero.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - divideByZero tests: divZero.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/divideByZero/divZero.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - divideByZero tests: modByZero.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/divideByZero/modByZero.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intJustOverflow.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intJustOverflow.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intmultOverflow.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intmultOverflow.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intnegateOverflow2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intnegateOverflow2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intnegateOverflow3.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intnegateOverflow3.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intnegateOverflow4.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intnegateOverflow4.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intnegateOverflow.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intnegateOverflow.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intUnderflow.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intUnderflow.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - integerOverflow tests: intWayOverflow.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/integerOverflow/intWayOverflow.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: freeNull.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/freeNull.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: readNull1.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/readNull1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: readNull2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/readNull2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: setNull1.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/setNull1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: setNull2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/setNull2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: useNull1.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/useNull1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nullDereference tests: useNull2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/runtimeErr/nullDereference/useNull2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
