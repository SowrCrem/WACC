import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrExitTests extends AnyFlatSpec {

  "semanticErr - exit tests: badCharExit.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Exit/badCharExit.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - exit tests: exitNonInt.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Exit/exitNonInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - exit tests: globalReturn.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Exit/globalReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - exit tests: returnsInMain.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Exit/returnsInMain.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
