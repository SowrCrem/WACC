package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrPairsTests extends AnyFlatSpec {

  "syntaxErr - pairs tests: badLookup01.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/pairs/badLookup01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - pairs tests: badLookup02.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/pairs/badLookup02.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - pairs tests: elemOfNonPair.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/pairs/elemOfNonPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - pairs tests: fstNull.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/pairs/fstNull.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - pairs tests: noNesting.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/pairs/noNesting.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "syntaxErr - pairs tests: sndNull.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/pairs/sndNull.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
