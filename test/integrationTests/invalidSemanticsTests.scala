import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.IntLiter
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class InvalidSemanticsTests extends AnyFlatSpec {

  "invalid semantic test badComment.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/invalid/semanticErr/exit/badCharExit.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
    
    pending
  }
}