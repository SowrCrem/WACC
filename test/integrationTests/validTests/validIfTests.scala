import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidIfTests extends AnyFlatSpec {

  "valid - if tests: if1.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/if1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: if2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/if2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: if3.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/if3.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: if4.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/if4.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: if5.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/if5.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: if6.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/if6.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: ifBasic.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/ifBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: ifFalse.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/ifFalse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: ifTrue.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/ifTrue.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - if tests: whitespace.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/if/whitespace.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
