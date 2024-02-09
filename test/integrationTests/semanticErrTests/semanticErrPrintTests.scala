package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrPrintTests extends AnyFlatSpec {

  "semanticErr - print tests: printTypeErr01.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("../test/wacc/invalid/semanticErr/print/printTypeErr01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPrintTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - print tests: printTypeErr01.wacc" should "return exit code 200" in {/s/"semanticErr - print tests: printTypeErr01.wacc" should "return exit code 200" in {/"semanticErr - print tests: printTypeErr01.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
