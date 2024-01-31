import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrVariablesTests extends AnyFlatSpec {

  "semanticErr - variables tests: basicTypeErr01.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr02.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr02.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr03.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr03.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr04.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr04.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr05.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr05.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr06.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr06.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr07.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr07.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr08.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr08.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr09.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr09.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr10.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr10.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr11.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr11.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: basicTypeErr12.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/basicTypeErr12.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: caseMatters.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/caseMatters.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: doubleDeclare.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/doubleDeclare.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: undeclaredScopeVar.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/undeclaredScopeVar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: undeclaredVarAccess.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/undeclaredVarAccess.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - variables tests: undeclaredVar.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/variables/undeclaredVar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
