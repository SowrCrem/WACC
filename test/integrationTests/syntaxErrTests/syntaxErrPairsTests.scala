import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrPairsTests extends AnyFlatSpec {

  "syntaxErr - pairs tests: badLookup01.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Pairs/badLookup01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - pairs tests: badLookup02.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Pairs/badLookup02.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - pairs tests: elemOfNonPair.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Pairs/elemOfNonPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - pairs tests: fstNull.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Pairs/fstNull.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - pairs tests: noNesting.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Pairs/noNesting.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - pairs tests: sndNull.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/syntaxErr/Pairs/sndNull.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
