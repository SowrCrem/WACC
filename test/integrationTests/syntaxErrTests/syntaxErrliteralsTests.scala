import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrLiteralsTests extends AnyFlatSpec {

  "syntaxErr - literals tests: charLiteralSingle.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Literals/charLiteralSingle.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - literals tests: stringLiteralNoNewlines.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Literals/stringLiteralNoNewlines.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - literals tests: stringLiteralOnlyAscii.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Literals/stringLiteralOnlyAscii.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

}
