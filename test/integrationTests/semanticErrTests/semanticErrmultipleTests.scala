import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrMultipleTests extends AnyFlatSpec {

  "semanticErr - multiple tests: funcMess.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Multiple/funcMess.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - multiple tests: ifAndWhileErrs.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Multiple/ifAndWhileErrs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - multiple tests: messyExpr.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Multiple/messyExpr.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - multiple tests: multiCaseSensitivity.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Multiple/multiCaseSensitivity.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - multiple tests: multiTypeErrs.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Multiple/multiTypeErrs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - multiple tests: obfuscatingReturnsWithWhile.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Multiple/obfuscatingReturnsWithWhile.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

}
