import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidIOTests extends AnyFlatSpec {

  "valid - IO tests: IOLoop.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/IOLoop.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - IO tests: IOSequence.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/IOSequence.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: hashInProgram.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/hashInProgram.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: multipleStringsAssignment.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/multipleStringsAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: print-backspace.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/print-backspace.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: printBool.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/printBool.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: printCharArray.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/printCharArray.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: printCharAsString.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/printCharAsString.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: printChar.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/printChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: printEscChar.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/printEscChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: printInt.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/printInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: println.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/println.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - print tests: print.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print/print.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: echoBigInt.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/echoBigInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: echoBigNegInt.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/echoBigNegInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: echoChar.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/echoChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: echoInt.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/echoInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: echoNegInt.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/echoNegInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: echoPuncChar.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/echoPuncChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: readAtEof.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/readAtEof.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - read tests: read.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read/read.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
