package test.frontend.unit.syntax

import wacc._
import test.Utils._
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._


class parseMacros extends AnyFlatSpec {
  
  "parseMacros" should "parse a simple macro" in {
    parseSucceeds("DEF TEST_MACRO := 5 END_DEF", List())
  }

}

