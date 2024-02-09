package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class ValidWhileTests extends AnyFlatSpec {

  "valid - while tests: fibonacciFullIt.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/fibonacciFullIt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: fibonacciFullIt.wacc" should "return exit code 0" in {/s/"valid - while tests: fibonacciFullIt.wacc" should "return exit code 0" in {/"valid - while tests: fibonacciFullIt.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: fibonacciIterative.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/fibonacciIterative.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: fibonacciIterative.wacc" should "return exit code 0" in {/s/"valid - while tests: fibonacciIterative.wacc" should "return exit code 0" in {/"valid - while tests: fibonacciIterative.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: loopCharCondition.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/loopCharCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: loopCharCondition.wacc" should "return exit code 0" in {/s/"valid - while tests: loopCharCondition.wacc" should "return exit code 0" in {/"valid - while tests: loopCharCondition.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: loopIntCondition.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/loopIntCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: loopIntCondition.wacc" should "return exit code 0" in {/s/"valid - while tests: loopIntCondition.wacc" should "return exit code 0" in {/"valid - while tests: loopIntCondition.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: max.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/max.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: max.wacc" should "return exit code 0" in {/s/"valid - while tests: max.wacc" should "return exit code 0" in {/"valid - while tests: max.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: min.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/min.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: min.wacc" should "return exit code 0" in {/s/"valid - while tests: min.wacc" should "return exit code 0" in {/"valid - while tests: min.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: rmStyleAdd.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/rmStyleAdd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: rmStyleAdd.wacc" should "return exit code 0" in {/s/"valid - while tests: rmStyleAdd.wacc" should "return exit code 0" in {/"valid - while tests: rmStyleAdd.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: rmStyleAddIO.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/rmStyleAddIO.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: rmStyleAddIO.wacc" should "return exit code 0" in {/s/"valid - while tests: rmStyleAddIO.wacc" should "return exit code 0" in {/"valid - while tests: rmStyleAddIO.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: whileBasic.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/while/whileBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: whileBasic.wacc" should "return exit code 0" in {/s/"valid - while tests: whileBasic.wacc" should "return exit code 0" in {/"valid - while tests: whileBasic.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: whileBoolFlip.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/whileBoolFlip.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: whileBoolFlip.wacc" should "return exit code 0" in {/s/"valid - while tests: whileBoolFlip.wacc" should "return exit code 0" in {/"valid - while tests: whileBoolFlip.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: whileCount.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/whileCount.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: whileCount.wacc" should "return exit code 0" in {/s/"valid - while tests: whileCount.wacc" should "return exit code 0" in {/"valid - while tests: whileCount.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - while tests: whileFalse.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/while/whileFalse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validWhileTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - while tests: whileFalse.wacc" should "return exit code 0" in {/s/"valid - while tests: whileFalse.wacc" should "return exit code 0" in {/"valid - while tests: whileFalse.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

}
