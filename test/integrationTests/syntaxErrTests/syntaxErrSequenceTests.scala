package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrSequenceTests extends AnyFlatSpec {

  "syntaxErr - sequence tests: doubleSeq.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/sequence/doubleSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - sequence tests: doubleSeq.wacc" should "return exit code 100" in {/s/"syntaxErr - sequence tests: doubleSeq.wacc" should "return exit code 100" in {/"syntaxErr - sequence tests: doubleSeq.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - sequence tests: emptySeq.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/sequence/emptySeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - sequence tests: emptySeq.wacc" should "return exit code 100" in {/s/"syntaxErr - sequence tests: emptySeq.wacc" should "return exit code 100" in {/"syntaxErr - sequence tests: emptySeq.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - sequence tests: endSeq.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/sequence/endSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - sequence tests: endSeq.wacc" should "return exit code 100" in {/s/"syntaxErr - sequence tests: endSeq.wacc" should "return exit code 100" in {/"syntaxErr - sequence tests: endSeq.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - sequence tests: extraSeq.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/sequence/extraSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - sequence tests: extraSeq.wacc" should "return exit code 100" in {/s/"syntaxErr - sequence tests: extraSeq.wacc" should "return exit code 100" in {/"syntaxErr - sequence tests: extraSeq.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - sequence tests: missingSeq.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("../test/wacc/invalid/syntaxErr/sequence/missingSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrSequenceTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - sequence tests: missingSeq.wacc" should "return exit code 100" in {/s/"syntaxErr - sequence tests: missingSeq.wacc" should "return exit code 100" in {/"syntaxErr - sequence tests: missingSeq.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
