import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidVariablesTests extends AnyFlatSpec {

  "valid - variables tests: boolDeclaration2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/boolDeclaration2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: boolDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/boolDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: capCharDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/capCharDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: charDeclaration2.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/charDeclaration2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: charDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/charDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: emptyStringDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/emptyStringDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: intDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/intDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: longVarNames.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/longVarNames.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: manyVariables.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/manyVariables.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: negIntDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/negIntDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: puncCharDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/puncCharDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: stringCarriageReturn.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/stringCarriageReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: stringDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/stringDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: _VarNames.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/_VarNames.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - variables tests: zeroIntDeclaration.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/variables/zeroIntDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
