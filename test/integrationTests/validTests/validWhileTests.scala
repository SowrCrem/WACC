import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidWhileTests extends AnyFlatSpec {

  "valid - while tests: fibonacciFullIt.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/fibonacciFullIt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: fibonacciIterative.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/fibonacciIterative.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: loopCharCondition.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/loopCharCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: loopIntCondition.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/loopIntCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: max.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/max.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: min.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/min.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: rmStyleAddIO.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/rmStyleAddIO.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: rmStyleAdd.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/rmStyleAdd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: whileBasic.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/whileBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: whileBoolFlip.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/whileBoolFlip.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: whileCount.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/whileCount.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - while tests: whileFalse.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/While/whileFalse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
