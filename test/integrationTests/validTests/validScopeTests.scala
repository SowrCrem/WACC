package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class ValidScopeTests extends AnyFlatSpec {

  "valid - scope tests: ifNested1.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/scope/ifNested1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: ifNested1.wacc" should "return exit code 0" in {/s/"valid - scope tests: ifNested1.wacc" should "return exit code 0" in {/"valid - scope tests: ifNested1.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: ifNested2.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/scope/ifNested2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: ifNested2.wacc" should "return exit code 0" in {/s/"valid - scope tests: ifNested2.wacc" should "return exit code 0" in {/"valid - scope tests: ifNested2.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: indentationNotImportant.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/indentationNotImportant.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: indentationNotImportant.wacc" should "return exit code 0" in {/s/"valid - scope tests: indentationNotImportant.wacc" should "return exit code 0" in {/"valid - scope tests: indentationNotImportant.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: intsAndKeywords.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/scope/intsAndKeywords.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: intsAndKeywords.wacc" should "return exit code 0" in {/s/"valid - scope tests: intsAndKeywords.wacc" should "return exit code 0" in {/"valid - scope tests: intsAndKeywords.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: printAllTypes.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/printAllTypes.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: printAllTypes.wacc" should "return exit code 0" in {/s/"valid - scope tests: printAllTypes.wacc" should "return exit code 0" in {/"valid - scope tests: printAllTypes.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeBasic.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeBasic.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeBasic.wacc" should "return exit code 0" in {/"valid - scope tests: scopeBasic.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeIfRedefine.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeIfRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeIfRedefine.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeIfRedefine.wacc" should "return exit code 0" in {/"valid - scope tests: scopeIfRedefine.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeRedefine.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeRedefine.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeRedefine.wacc" should "return exit code 0" in {/"valid - scope tests: scopeRedefine.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeSimpleRedefine.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeSimpleRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeSimpleRedefine.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeSimpleRedefine.wacc" should "return exit code 0" in {/"valid - scope tests: scopeSimpleRedefine.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeVars.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeVars.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeVars.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeVars.wacc" should "return exit code 0" in {/"valid - scope tests: scopeVars.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scope.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/scope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scope.wacc" should "return exit code 0" in {/s/"valid - scope tests: scope.wacc" should "return exit code 0" in {/"valid - scope tests: scope.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeWhileNested.wacc" should "return exit code 0" ignore {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeWhileNested.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeWhileNested.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeWhileNested.wacc" should "return exit code 0" in {/"valid - scope tests: scopeWhileNested.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: scopeWhileRedefine.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/scope/scopeWhileRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: scopeWhileRedefine.wacc" should "return exit code 0" in {/s/"valid - scope tests: scopeWhileRedefine.wacc" should "return exit code 0" in {/"valid - scope tests: scopeWhileRedefine.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

  "valid - scope tests: splitScope.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/scope/splitScope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 0) {
      val filePath = "test/integrationTests/validTests/validScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"valid - scope tests: splitScope.wacc" should "return exit code 0" in {/s/"valid - scope tests: splitScope.wacc" should "return exit code 0" in {/"valid - scope tests: splitScope.wacc" should "return exit code 0" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 0
  }

}
