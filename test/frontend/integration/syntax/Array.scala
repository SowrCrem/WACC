package test.frontend.integration.syntax.Array

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

  "syntaxErr - array tests: arrayExpr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/array/arrayExpr.wacc")
  }

}
