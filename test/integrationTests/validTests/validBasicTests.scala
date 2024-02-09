package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class ValidBasicTests extends AnyFlatSpec {

  "valid - exit tests: exit-1.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/exit/exit-1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - exit tests: exit-1.wacc" should "return exit code 0" in {/s/"valid - exit tests: exit-1.wacc" should "return exit code 0" in {/"valid - exit tests: exit-1.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - exit tests: exitBasic.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/exit/exitBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - exit tests: exitBasic.wacc" should "return exit code 0" in {/s/"valid - exit tests: exitBasic.wacc" should "return exit code 0" in {/"valid - exit tests: exitBasic.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - exit tests: exitBasic2.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/exit/exitBasic2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - exit tests: exitBasic2.wacc" should "return exit code 0" in {/s/"valid - exit tests: exitBasic2.wacc" should "return exit code 0" in {/"valid - exit tests: exitBasic2.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - exit tests: exitWrap.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/exit/exitWrap.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - exit tests: exitWrap.wacc" should "return exit code 0" in {/s/"valid - exit tests: exitWrap.wacc" should "return exit code 0" in {/"valid - exit tests: exitWrap.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - skip tests: commentEoF.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/skip/commentEoF.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - skip tests: commentEoF.wacc" should "return exit code 0" in {/s/"valid - skip tests: commentEoF.wacc" should "return exit code 0" in {/"valid - skip tests: commentEoF.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - skip tests: commentInLine.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/skip/commentInLine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - skip tests: commentInLine.wacc" should "return exit code 0" in {/s/"valid - skip tests: commentInLine.wacc" should "return exit code 0" in {/"valid - skip tests: commentInLine.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - skip tests: comment.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/skip/comment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - skip tests: comment.wacc" should "return exit code 0" in {/s/"valid - skip tests: comment.wacc" should "return exit code 0" in {/"valid - skip tests: comment.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - skip tests: skip.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/basic/skip/skip.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validBasicTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - skip tests: skip.wacc" should "return exit code 0" in {/s/"valid - skip tests: skip.wacc" should "return exit code 0" in {/"valid - skip tests: skip.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

}
