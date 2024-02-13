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

class Print extends AnyFlatSpec {

  "syntaxErr - print tests: printlnCharArry.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
  }

}
