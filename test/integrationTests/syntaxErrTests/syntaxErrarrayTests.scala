import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrArrayTests extends AnyFlatSpec {

  "syntaxErr - array tests: arrayExpr.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Array/arrayExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

}
