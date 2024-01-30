import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidRuntimeErrTests extends AnyFlatSpec {

  "valid - runtimeErr tests: arrayOutOfBounds" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/RuntimeErr/arrayOutOfBounds")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - runtimeErr tests: badChar" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/RuntimeErr/badChar")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - runtimeErr tests: divideByZero" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/RuntimeErr/divideByZero")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - runtimeErr tests: integerOverflow" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/RuntimeErr/integerOverflow")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - runtimeErr tests: nullDereference" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/RuntimeErr/nullDereference")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

}
