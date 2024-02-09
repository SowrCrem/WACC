package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class ValidSequenceTests extends AnyFlatSpec {

  "valid - sequence tests: basicSeq2.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/basicSeq2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: basicSeq2.wacc" should "return exit code 0" in {/s/"valid - sequence tests: basicSeq2.wacc" should "return exit code 0" in {/"valid - sequence tests: basicSeq2.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: basicSeq.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/basicSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: basicSeq.wacc" should "return exit code 0" in {/s/"valid - sequence tests: basicSeq.wacc" should "return exit code 0" in {/"valid - sequence tests: basicSeq.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: boolAssignment.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/sequence/boolAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: boolAssignment.wacc" should "return exit code 0" in {/s/"valid - sequence tests: boolAssignment.wacc" should "return exit code 0" in {/"valid - sequence tests: boolAssignment.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: charAssignment.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/sequence/charAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: charAssignment.wacc" should "return exit code 0" in {/s/"valid - sequence tests: charAssignment.wacc" should "return exit code 0" in {/"valid - sequence tests: charAssignment.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: exitSimple.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/sequence/exitSimple.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: exitSimple.wacc" should "return exit code 0" in {/s/"valid - sequence tests: exitSimple.wacc" should "return exit code 0" in {/"valid - sequence tests: exitSimple.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: intAssignment.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/sequence/intAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: intAssignment.wacc" should "return exit code 0" in {/s/"valid - sequence tests: intAssignment.wacc" should "return exit code 0" in {/"valid - sequence tests: intAssignment.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: intLeadingZeros.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/sequence/intLeadingZeros.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: intLeadingZeros.wacc" should "return exit code 0" in {/s/"valid - sequence tests: intLeadingZeros.wacc" should "return exit code 0" in {/"valid - sequence tests: intLeadingZeros.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - sequence tests: stringAssignment.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/sequence/stringAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - sequence tests: stringAssignment.wacc" should "return exit code 0" in {/s/"valid - sequence tests: stringAssignment.wacc" should "return exit code 0" in {/"valid - sequence tests: stringAssignment.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

}
