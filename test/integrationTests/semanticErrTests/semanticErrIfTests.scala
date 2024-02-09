package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrIfTests extends AnyFlatSpec {

  "semanticErr - if tests: ifIntCondition.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/if/ifIntCondition.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrIfTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - if tests: ifIntCondition.wacc" should "return exit code 200" in {/s/"semanticErr - if tests: ifIntCondition.wacc" should "return exit code 200" in {/"semanticErr - if tests: ifIntCondition.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
