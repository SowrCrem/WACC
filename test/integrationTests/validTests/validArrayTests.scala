import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidArrayTests extends AnyFlatSpec {

  "valid - array tests: arrayBasic.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayBasic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayEmpty.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayEmpty.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayIndexMayBeArrayIndex.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayIndexMayBeArrayIndex.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayLength.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayLength.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayLookup.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayLookup.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayNested.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayNested.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayOnHeap.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayOnHeap.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arrayPrint.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arrayPrint.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: arraySimple.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/arraySimple.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: array.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/array.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: charArrayInStringArray.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/charArrayInStringArray.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: emptyArrayAloneIsFine.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/emptyArrayAloneIsFine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: emptyArrayNextLine.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/emptyArrayNextLine.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: emptyArrayPrint.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/emptyArrayPrint.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: emptyArrayReplace.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/emptyArrayReplace.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: emptyArrayScope.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/emptyArrayScope.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: free.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/free.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: lenArrayIndex.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/lenArrayIndex.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: modifyString.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/modifyString.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: printRef.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/printRef.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - array tests: stringFromArray.wacc" should "return exit code 0" in {
    
    pending

    val path : Array[String] = Array("test/wacc/valid/array/stringFromArray.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
