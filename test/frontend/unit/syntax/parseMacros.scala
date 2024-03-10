package test.frontend.unit.syntax

import wacc.Main
import wacc._
import test.Utils._
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._


class parseMacros extends AnyFlatSpec {

  val pos = (0,0)  
  "parseMacros" should "parse a simple macro" in {
    parseSucceeds("DEF TEST_MACRO := 5 END_DEF", "skip", List(Skip()(pos)))
  }

  it should "parse multiple macros" in {
    parseSucceeds("DEF TEST_MACRO := 5 END_DEF DEF TEST_MACRO2 := 6 END_DEF", "skip", List(Skip()(pos)))
  }
  it should "parse a macro with a statement" in {
    parseSucceeds("DEF TEST_MACRO := 5 END_DEF", "int a = ~TEST_MACRO", List(Skip()(pos)))
  }

}

