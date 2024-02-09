import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrReadTests extends AnyFlatSpec {

  "semanticErr - read tests: readIntoBadFst.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/read/readIntoBadFst.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - read tests: readIntoBadSnd.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/read/readIntoBadSnd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - read tests: readTypeErr01.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/read/readTypeErr01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
