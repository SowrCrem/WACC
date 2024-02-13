package test.frontend.integration.syntax

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class While extends AnyFlatSpec {

  "syntaxErr - while tests: donoErr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - while tests: dooErr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - while tests: whileNodone.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - while tests: whileNodo.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

  "syntaxErr - while tests: whilErr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
