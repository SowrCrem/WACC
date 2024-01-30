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

  "valid - IO tests: print" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/print")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - IO tests: read" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/IO/read")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
