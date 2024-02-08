package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidSequenceTests extends AnyFlatSpec {

  "valid - sequence tests: basicSeq2.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/basicSeq2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: basicSeq.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/basicSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: boolAssignment.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/boolAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: charAssignment.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/charAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: exitSimple.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/exitSimple.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: intAssignment.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/intAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: intLeadingZeros.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/intLeadingZeros.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - sequence tests: stringAssignment.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/stringAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
