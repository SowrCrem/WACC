import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Expr,
  Program,
  IntLiter,
  IdentAsgn,
  IntTypeNode,
  Neg,
  BoolLiter,
  BoolTypeNode,
  CharLiter,
  CharTypeNode,
  StringLiter,
  StringTypeNode,
  Ident,
  Brackets,
  ArrayLiter,
  Null, 
  Error,
  Node,
  Exit
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.TypeNode
import wacc.ArrayTypeNode


class parseAsgns extends AnyFlatSpec {
  
  // Testing Functions -------------------------------------------------------------------------------------------------
  def getType(expected: Node): (String, TypeNode) = expected match {
    case IntLiter(_)    => ("int"   , IntTypeNode())
    case Neg(_)         => ("int"   , IntTypeNode())
    case BoolLiter(_)   => ("bool"  , BoolTypeNode())
    case CharLiter(_)   => ("char"  , CharTypeNode())
    case StringLiter(_) => ("string", StringTypeNode())
    case Brackets(expr) => getType(expr)
    case _              => ("int"   , IntTypeNode())
  }

  def parseSucceeds[T](input: String, expected: Expr, identifier: String = "input", comment: String = ""): Assertion = {
    val (literalType, typeNode) = getType(expected)
    val assignment = IdentAsgn(IntTypeNode(), Ident(identifier), expected)
    parser.parse("begin int " + identifier + " = " + input + " " + comment + " end") shouldBe Success(Program(List(), assignment))
  }

  def parseFails(input: String, errorMessage: String = "", identifier: String = "input"): Assertion = {
    parser.parse("begin int " + identifier + " = " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
  }

  def parseWithIdentifier(name:String, success:Boolean): Assertion = {
    if (success) parseSucceeds("1", IntLiter(1), name) else parseFails("1", "int", name)
  }
  
  def commentIgnored(comment: String) = {
    parseSucceeds("1", IntLiter(1), "input", comment)
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
    parseSucceeds("[1,2,3]", ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3))))
  }

  // We need to parse assignments of the format "int[][] ident = expr". Dont use the functions above for this
  it should "parse arrays of arrays" in {
    val expected = IdentAsgn(ArrayTypeNode(ArrayTypeNode(IntTypeNode())), Ident("input"), ArrayLiter(List(ArrayLiter(List(IntLiter(1), IntLiter(2))), ArrayLiter(List(IntLiter(3), IntLiter(4))))))
    parser.parse("begin int[][] input = [[1,2],[3,4]] end") shouldBe Success(Program(List(), expected))
  }

  it should "Parse empty array assignments" in {
    val expected = IdentAsgn(ArrayTypeNode(IntTypeNode()), Ident("input"), ArrayLiter(List()))
    parser.parse("begin int[] input = [] end") shouldBe Success(Program(List(), expected))
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
