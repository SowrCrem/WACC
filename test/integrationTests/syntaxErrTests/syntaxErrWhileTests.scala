package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrWhileTests extends AnyFlatSpec {

  "syntaxErr - while tests: donoErr.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/while/donoErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - while tests: dooErr.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/while/dooErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - while tests: whileNodone.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/while/whileNodone.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - while tests: whileNodo.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/while/whileNodo.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - while tests: whilErr.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/while/whilErr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
