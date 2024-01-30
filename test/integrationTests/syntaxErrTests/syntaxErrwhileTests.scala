import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrWhileTests extends AnyFlatSpec {

  "syntaxErr - while tests: donoErr.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/While/donoErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - while tests: dooErr.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/While/dooErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - while tests: whileNodone.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/While/whileNodone.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - while tests: whileNodo.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/While/whileNodo.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - while tests: whilErr.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/While/whilErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

}
