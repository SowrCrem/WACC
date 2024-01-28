import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.IntLiterNode
import parsley.{Failure, Result, Success}
import wacc.parser._


class InvalidCompile extends AnyFlatSpec {

  "invalid syntax test badComment.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/badComment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "invalid semantic test badComment.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/invalid/semanticErr/exit/badCharExit.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  it should "parse int literals" in {
    atoms.parse("123") shouldBe Success(IntLiterNode(123))
  }

}
