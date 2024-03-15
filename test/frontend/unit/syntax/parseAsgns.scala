package test.frontend.unit.syntax

import wacc.Main
import wacc.lexer._
import wacc.parser._
import wacc.{
  Program,
  Expr,
  Neg,
  Null,
  Exit,
  Ident,
  TypeNode,
  Position,
  Brackets,
  IdentAsgn,
  IntLiter,
  BoolLiter,
  CharLiter,
  StringLiter,
  ArrayLiter,
  IntTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  ArrayTypeNode
}
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class parseAsgns extends AnyFlatSpec {
  val pos = (0, 0)

  // Testing Functions -------------------------------------------------------------------------------------------------
  def getType(expected: Position): (String, TypeNode) = expected match {
    case IntLiter(_)    => ("int"   , IntTypeNode   ()(pos))
    case Neg(_)         => ("int"   , IntTypeNode   ()(pos))
    case BoolLiter(_)   => ("bool"  , BoolTypeNode  ()(pos))
    case CharLiter(_)   => ("char"  , CharTypeNode  ()(pos))
    case StringLiter(_) => ("string", StringTypeNode()(pos))
    case Brackets(expr) => getType(expr)
    case _              => ("int"   , IntTypeNode   ()(pos))
  }

  def parseSucceeds[T](
      input: String,
      expected: Expr,
      identifier: String = "input",
      comment: String = ""
  ): Assertion = {
    val (literalType, typeNode) = getType(expected)
    val assignment = IdentAsgn(IntTypeNode()(pos), Ident(identifier)(pos), expected)(pos)
    parser.parse(
      "begin int " + identifier + " = " + input + " " + comment + " end"
    ) shouldBe Success(Program(List(), List(), List(assignment))(pos))
  }

  def parseFails(
      input: String,
      errorMessage: String = "",
      identifier: String = "input"
  ): Assertion = {
    parser.parse("begin int " + identifier + " = " + input + " end") should matchPattern {
      case Failure(_) => /* Match on any Failure */
    }
  }

  def parseWithIdentifier(name: String, success: Boolean): Assertion = {
    if (success) parseSucceeds("1", IntLiter(1)(pos), name)
    else parseFails("1", "int", name)
  }

  def commentIgnored(comment: String) = {
    parseSucceeds("1", IntLiter(1)(pos), "input", comment)
  }

  // Tests for Ident --------------------------------------------------------------------------------------------------
  it should "parse identifiers" in {
    parseWithIdentifier("hello", true)
  }

  it should "parse identifiers with underscores" in {

    parseWithIdentifier("hello_world", true)
  }

  it should "parse identifiers starting with underscores" in {
    parseWithIdentifier("_hello", true)
  }

  it should "parse identifiers with numbers" in {
    parseWithIdentifier("hello123", true)
  }

  it should "reject identifiers starting with numbers" in {
    parseWithIdentifier("123hello", false)
  }

  it should "parse identifiers with underscores and numbers" in {
    parseWithIdentifier("hello_world123", true)
  }

  it should "reject brackets around identifiers" in {
    parseWithIdentifier("(hello)", false)
  }

  // Tests for Arrays --------------------------------------------------------------------------------------------------

  it should "parse arrays" in {
    parseSucceeds(
      "[1,2,3]",
      ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos)
    )
  }

  // We need to parse assignments of the format "int[][] ident = expr". Dont use the functions above for this
  it should "parse arrays of arrays" in {
    val expected = IdentAsgn(
      ArrayTypeNode(ArrayTypeNode(IntTypeNode()(pos))(pos))(pos),
      Ident("input")(pos),
      ArrayLiter(
        List(
          ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos)))(pos),
          ArrayLiter(List(IntLiter(3)(pos), IntLiter(4)(pos)))(pos)
        )
      )(pos)
    )(pos)
    parser.parse("begin int[][] input = [[1,2],[3,4]] end") shouldBe Success(
      Program(List(), List(), List(expected))(pos)
    )
  }

  it should "Parse empty array assignments" in {
    val expected = IdentAsgn(
      ArrayTypeNode(IntTypeNode()(pos))(pos),
      Ident("input")(pos),
      ArrayLiter(List())(pos)
    )(pos)
    parser.parse("begin int[] input = [] end") shouldBe Success(
      Program(List(), List(), List(expected))(pos)
    )
  }

  // Tests for Comment ------------------------------------------------------------------------------------------
  it should "ignore comments" in {
    commentIgnored("# This is a comment\n")
    commentIgnored("## This is a comment\n")
  }

  it should "reject comments without EOL" in {
    parseFails("# This is a comment")
  }

}
