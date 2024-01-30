import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidArrayTests extends AnyFlatSpec {

  "valid - array tests: arrayBasic.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayEmpty.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayEmpty.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayIndexMayBeArrayIndex.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayIndexMayBeArrayIndex.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayLength.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayLength.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayLookup.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayLookup.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayNested.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayNested.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayOnHeap.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayOnHeap.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arrayPrint.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arrayPrint.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: arraySimple.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/arraySimple.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: array.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/array.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: charArrayInStringArray.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/charArrayInStringArray.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: emptyArrayAloneIsFine.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/emptyArrayAloneIsFine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: emptyArrayNextLine.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/emptyArrayNextLine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: emptyArrayPrint.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/emptyArrayPrint.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: emptyArrayReplace.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/emptyArrayReplace.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: emptyArrayScope.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/emptyArrayScope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: free.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/free.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: lenArrayIndex.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/lenArrayIndex.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: modifyString.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/modifyString.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: printRef.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/printRef.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

  "valid - array tests: stringFromArray.wacc" should "return exit code 0" in {
    val path : Array[String] = Array("test/wacc/valid/Array/stringFromArray.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
        
    pending
  }

}
