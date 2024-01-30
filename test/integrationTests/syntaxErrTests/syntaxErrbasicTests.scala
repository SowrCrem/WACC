import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrBasicTests extends AnyFlatSpec {

  "syntaxErr - basic tests: badComment2.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/badComment2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: badComment.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/badComment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: badEscape.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/badEscape.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: beginNoend.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/beginNoend.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: bgnErr.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/bgnErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: multipleBegins.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/multipleBegins.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: noBody.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/noBody.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: skpErr.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/skpErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - basic tests: unescapedChar.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Basic/unescapedChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

}
