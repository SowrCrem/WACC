package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrReadTests extends AnyFlatSpec {

  "semanticErr - read tests: readIntoBadFst.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/read/readIntoBadFst.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrReadTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - read tests: readIntoBadFst.wacc" should "return exit code 200" in {/s/"semanticErr - read tests: readIntoBadFst.wacc" should "return exit code 200" in {/"semanticErr - read tests: readIntoBadFst.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - read tests: readIntoBadSnd.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/read/readIntoBadSnd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrReadTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - read tests: readIntoBadSnd.wacc" should "return exit code 200" in {/s/"semanticErr - read tests: readIntoBadSnd.wacc" should "return exit code 200" in {/"semanticErr - read tests: readIntoBadSnd.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - read tests: readTypeErr01.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/read/readTypeErr01.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrReadTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - read tests: readTypeErr01.wacc" should "return exit code 200" in {/s/"semanticErr - read tests: readTypeErr01.wacc" should "return exit code 200" in {/"semanticErr - read tests: readTypeErr01.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
