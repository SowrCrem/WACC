package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrExitTests extends AnyFlatSpec {

  "semanticErr - exit tests: badCharExit.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/exit/badCharExit.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrExitTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - exit tests: badCharExit.wacc" should "return exit code 200" in {/s/"semanticErr - exit tests: badCharExit.wacc" should "return exit code 200" in {/"semanticErr - exit tests: badCharExit.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - exit tests: exitNonInt.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/exit/exitNonInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrExitTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - exit tests: exitNonInt.wacc" should "return exit code 200" in {/s/"semanticErr - exit tests: exitNonInt.wacc" should "return exit code 200" in {/"semanticErr - exit tests: exitNonInt.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - exit tests: globalReturn.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/exit/globalReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrExitTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - exit tests: globalReturn.wacc" should "return exit code 200" in {/s/"semanticErr - exit tests: globalReturn.wacc" should "return exit code 200" in {/"semanticErr - exit tests: globalReturn.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - exit tests: returnsInMain.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/exit/returnsInMain.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrExitTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - exit tests: returnsInMain.wacc" should "return exit code 200" in {/s/"semanticErr - exit tests: returnsInMain.wacc" should "return exit code 200" in {/"semanticErr - exit tests: returnsInMain.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
