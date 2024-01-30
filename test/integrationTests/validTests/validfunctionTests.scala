import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidFunctionTests extends AnyFlatSpec {

  "valid - function tests: nested_functions" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Function/nested_functions")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - function tests: simple_functions" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Function/simple_functions")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

}
