import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrVariablesTests extends AnyFlatSpec {

  "semanticErr - variables tests: basicTypeErr01.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr02.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr02.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr03.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr03.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr04.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr04.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr05.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr05.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr06.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr06.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr07.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr07.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr08.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr08.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr09.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr09.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr10.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr10.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr11.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr11.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: basicTypeErr12.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/basicTypeErr12.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: caseMatters.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/caseMatters.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: doubleDeclare.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/doubleDeclare.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: undeclaredScopeVar.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/undeclaredScopeVar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: undeclaredVarAccess.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/undeclaredVarAccess.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - variables tests: undeclaredVar.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Variables/undeclaredVar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

}
