package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import sys.process._
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Array extends AnyFlatSpec with BeforeAndAfterEach {
  
  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

  "WACC" should "run valid/array/array.wacc" in {
    val path = constructPath(List("valid", "array", "array.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayBasic.wacc" in {
    val path = constructPath(List("valid", "array", "arrayBasic.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayEmpty.wacc" in {
    val path = constructPath(List("valid", "array", "arrayEmpty.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayIndexMayBeArrayIndex.wacc" in {
    val path = constructPath(List("valid", "array", "arrayIndexMayBeArrayIndex.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayLength.wacc" in {
    val path = constructPath(List("valid", "array", "arrayLength.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayLookup.wacc" in {
    val path = constructPath(List("valid", "array", "arrayLookup.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayNested.wacc" in {
    val path = constructPath(List("valid", "array", "arrayNested.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayOnHeap.wacc" in {
    val path = constructPath(List("valid", "array", "arrayOnHeap.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arrayPrint.wacc" in {
    val path = constructPath(List("valid", "array", "arrayPrint.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/arraySimple.wacc" in {
    val path = constructPath(List("valid", "array", "arraySimple.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/charArrayInStringArray.wacc" in {
    val path = constructPath(List("valid", "array", "charArrayInStringArray.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/emptyArrayAloneIsFine.wacc" in {
    val path = constructPath(List("valid", "array", "emptyArrayAloneIsFine.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/emptyArrayNextLine.wacc" in {
    val path = constructPath(List("valid", "array", "emptyArrayNextLine.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/emptyArrayPrint.wacc" in {
    val path = constructPath(List("valid", "array", "emptyArrayPrint.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/emptyArrayReplace.wacc" in {
    val path = constructPath(List("valid", "array", "emptyArrayReplace.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/emptyArrayScope.wacc" in {
    val path = constructPath(List("valid", "array", "emptyArrayScope.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/free.wacc" in {
    val path = constructPath(List("valid", "array", "free.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/lenArrayIndex.wacc" in {
    val path = constructPath(List("valid", "array", "lenArrayIndex.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/modifyString.wacc" in {
    val path = constructPath(List("valid", "array", "modifyString.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/printRef.wacc" in {
    val path = constructPath(List("valid", "array", "printRef.wacc"))
    runSucceeds(path)
  }

  it should "run valid/array/stringFromArray.wacc" in {
    val path = constructPath(List("valid", "array", "stringFromArray.wacc"))
    runSucceeds(path)
  }

} 