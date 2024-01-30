import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidBasicTests extends AnyFlatSpec {

  "valid - basic tests: exit" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Basic/exit")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - basic tests: skip" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Basic/skip")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
