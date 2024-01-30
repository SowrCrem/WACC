import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidScopeTests extends AnyFlatSpec {

  "valid - scope tests: ifNested1.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/ifNested1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: ifNested2.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/ifNested2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: indentationNotImportant.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/indentationNotImportant.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: intsAndKeywords.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/intsAndKeywords.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: printAllTypes.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/printAllTypes.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeBasic.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeIfRedefine.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeIfRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeRedefine.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeSimpleRedefine.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeSimpleRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeVars.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeVars.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scope.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeWhileNested.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeWhileNested.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: scopeWhileRedefine.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/scopeWhileRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - scope tests: splitScope.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Scope/splitScope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
