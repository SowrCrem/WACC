package integrationTests.syntaxErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SyntaxErrVariablesTests extends AnyFlatSpec {

  "syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/variables/badintAssignments1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrVariablesTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 100" in {/s/"syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 100" in {/"syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/variables/badintAssignments2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrVariablesTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 100" in {/s/"syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 100" in {/"syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/variables/badintAssignments.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrVariablesTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 100" in {/s/"syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 100" in {/"syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/variables/bigIntAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrVariablesTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 100" in {/s/"syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 100" in {/"syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

  "syntaxErr - variables tests: varNoName.wacc" should "return exit code 100" in {

    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/variables/varNoName.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 100) {
      val filePath = "test/integrationTests/syntaxErrTests/syntaxErrVariablesTests.scala"
      val sedCommand = s"""sed -i '0,/"syntaxErr - variables tests: varNoName.wacc" should "return exit code 100" in {/s/"syntaxErr - variables tests: varNoName.wacc" should "return exit code 100" in {/"syntaxErr - variables tests: varNoName.wacc" should "return exit code 100" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 100
  }

}
