import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidVariablesTests extends AnyFlatSpec {

  "valid - variables tests: boolDeclaration2.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/boolDeclaration2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: boolDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/boolDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: capCharDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/capCharDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: charDeclaration2.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/charDeclaration2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: charDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/charDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: emptyStringDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/emptyStringDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: intDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/intDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: longVarNames.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/longVarNames.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: manyVariables.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/manyVariables.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: negIntDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/negIntDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: puncCharDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/puncCharDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: stringCarriageReturn.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/stringCarriageReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: stringDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/stringDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: _VarNames.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/_VarNames.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: zeroIntDeclaration.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/Variables/zeroIntDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
