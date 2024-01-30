import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrArrayTests extends AnyFlatSpec {

  "semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/arrayIndexComplexNotInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: arrayIndexNotInt.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/arrayIndexNotInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: arrayMultipleIndexError.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/arrayMultipleIndexError.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: badIndex.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/badIndex.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: indexUndefIdent.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/indexUndefIdent.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: mixingTypesInArrays.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/mixingTypesInArrays.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: noArrayCovariance.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/noArrayCovariance.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: nonMatchingArrays.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/nonMatchingArrays.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: noStringIndex.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/noStringIndex.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: wrongArrayDimension.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/wrongArrayDimension.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  "semanticErr - array tests: wrongArrayType.wacc" should "return exit code 100" in {
    
    pending

    val path : Array[String] = Array("test/wacc/semanticErr/Array/wrongArrayType.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

}
