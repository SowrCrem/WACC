import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrIfTests extends AnyFlatSpec {

  "semanticErr - if tests: ifIntCondition.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/if/ifIntCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}