import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrIfTests extends AnyFlatSpec {

  "syntaxErr - if tests: ifiErr.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/If/ifiErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - if tests: ifNoelse.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/If/ifNoelse.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - if tests: ifNofi.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/If/ifNofi.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - if tests: ifNothen.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/If/ifNothen.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
