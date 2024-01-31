import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrSequenceTests extends AnyFlatSpec {

  "syntaxErr - sequence tests: doubleSeq.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/Sequence/doubleSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - sequence tests: emptySeq.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/Sequence/emptySeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - sequence tests: endSeq.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/Sequence/endSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - sequence tests: extraSeq.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/Sequence/extraSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "syntaxErr - sequence tests: missingSeq.wacc" should "return exit code 200" in {
    

    val path : Array[String] = Array("test/wacc/syntaxErr/Sequence/missingSeq.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
