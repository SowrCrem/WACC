package test.frontend.integration.syntax.If

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class If extends AnyFlatSpec {

  "syntaxErr - if tests: ifiErr.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/if/ifiErr.wacc")
  }

  "syntaxErr - if tests: ifNoelse.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/if/ifNoelse.wacc")
  }

  "syntaxErr - if tests: ifNofi.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/if/ifNofi.wacc")
  }

  "syntaxErr - if tests: ifNothen.wacc" should "return exit code 100" in {
    throwsSyntaxError("invalid/syntaxErr/if/ifNothen.wacc")
  }

}
