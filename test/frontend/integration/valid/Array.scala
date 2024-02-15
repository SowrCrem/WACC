package test.frontend.integration.valid.Array

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Array extends AnyFlatSpec {

  "valid - array tests: arrayBasic.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayBasic.wacc")
  }

  "valid - array tests: arrayEmpty.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayEmpty.wacc")
  }

  "valid - array tests: arrayIndexMayBeArrayIndex.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayIndexMayBeArrayIndex.wacc")
  }

  "valid - array tests: arrayLength.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayLength.wacc")
  }

  "valid - array tests: arrayLookup.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayLookup.wacc")
  }

  "valid - array tests: arrayNested.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayNested.wacc")
  }

  "valid - array tests: arrayOnHeap.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayOnHeap.wacc")
  }

  "valid - array tests: arrayPrint.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arrayPrint.wacc")
  }

  "valid - array tests: arraySimple.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//arraySimple.wacc")
  }

  "valid - array tests: array.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//array.wacc")
  }

  "valid - array tests: charArrayInStringArray.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//charArrayInStringArray.wacc")
  }

  "valid - array tests: emptyArrayAloneIsFine.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//emptyArrayAloneIsFine.wacc")
  }

  "valid - array tests: emptyArrayNextLine.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//emptyArrayNextLine.wacc")
  }

  "valid - array tests: emptyArrayPrint.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//emptyArrayPrint.wacc")
  }

  "valid - array tests: emptyArrayReplace.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//emptyArrayReplace.wacc")
  }

  "valid - array tests: emptyArrayScope.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//emptyArrayScope.wacc")
  }

  "valid - array tests: free.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//free.wacc")
  }

  "valid - array tests: lenArrayIndex.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//lenArrayIndex.wacc")
  }

  "valid - array tests: modifyString.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//modifyString.wacc")
  }

  "valid - array tests: printRef.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//printRef.wacc")
  }

  "valid - array tests: stringFromArray.wacc" should "return exit code 0" in {
    throwsNoError("valid/array/wacc/valid/array//stringFromArray.wacc")
  }

}
