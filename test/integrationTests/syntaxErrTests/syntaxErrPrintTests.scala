import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrPrintTests extends AnyFlatSpec {

  "syntaxErr - print tests: printlnCharArry.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Print/printlnCharArry.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
