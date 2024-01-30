import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SyntaxErrVariablesTests extends AnyFlatSpec {

  "syntaxErr - variables tests: badintAssignments1.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Variables/badintAssignments1.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - variables tests: badintAssignments2.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Variables/badintAssignments2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - variables tests: badintAssignments.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Variables/badintAssignments.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - variables tests: bigIntAssignment.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Variables/bigIntAssignment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

  "syntaxErr - variables tests: varNoName.wacc" should "return exit code 200" in {
    val path : Array[String] = Array("test/wacc/syntaxErr/Variables/varNoName.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
        
    pending
  }

}
