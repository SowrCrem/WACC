import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrScopeTests extends AnyFlatSpec {

  "semanticErr - scope tests: badParentScope.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/scope/badParentScope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - scope tests: badScopeRedefine.wacc" should "return exit code 200" in {
    
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/scope/badScopeRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
