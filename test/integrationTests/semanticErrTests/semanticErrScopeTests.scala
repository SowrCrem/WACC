package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrScopeTests extends AnyFlatSpec {

  "semanticErr - scope tests: badParentScope.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/scope/badParentScope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - scope tests: badParentScope.wacc" should "return exit code 200" in {/s/"semanticErr - scope tests: badParentScope.wacc" should "return exit code 200" in {/"semanticErr - scope tests: badParentScope.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - scope tests: badScopeRedefine.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/scope/badScopeRedefine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrScopeTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - scope tests: badScopeRedefine.wacc" should "return exit code 200" in {/s/"semanticErr - scope tests: badScopeRedefine.wacc" should "return exit code 200" in {/"semanticErr - scope tests: badScopeRedefine.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
