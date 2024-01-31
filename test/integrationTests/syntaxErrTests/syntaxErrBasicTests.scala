import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrBasicTests extends AnyFlatSpec {

  "syntaxErr - basic tests: badComment2.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/badComment2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: badComment.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/badComment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: badEscape.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/badEscape.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: beginNoend.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/beginNoend.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: bgnErr.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/bgnErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: multipleBegins.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/multipleBegins.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: noBody.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/noBody.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: skpErr.wacc" should "return exit code 100" in {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/skpErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - basic tests: unescapedChar.wacc" should "return exit code 100" ignore {
    

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/unescapedChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
