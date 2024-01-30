import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidIfTests extends AnyFlatSpec {

  "valid - if tests: if1.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/if1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: if2.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/if2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: if3.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/if3.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: if4.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/if4.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: if5.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/if5.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: if6.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/if6.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: ifBasic.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/ifBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: ifFalse.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/ifFalse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: ifTrue.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/ifTrue.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - if tests: whitespace.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/If/whitespace.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

}
