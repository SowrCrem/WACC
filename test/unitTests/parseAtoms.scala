import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Program,
  IntLiter,
  IdentAsgn,
  IntType,
  Neg,
  BoolLiter,
  BoolType,
  CharLiter,
  CharType,
  StringLiter,
  StringType,
  Ident,
  Brackets,
  Null,
  Error,
  Node
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.Type

class parseAtoms extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------
  def getType(expected: Node): (String, Type) = expected match {
    case IntLiter(_)    => ("int"   , IntType())
    case Neg(_)         => ("int"   , IntType())
    case BoolLiter(_)   => ("bool"  , BoolType())
    case CharLiter(_)   => ("char"  , CharType())
    case StringLiter(_) => ("string", StringType())
    case Brackets(expr) => getType(expr)
    case _              => ("int"   , IntType())
  }

  def parseSucceeds[T](input: String, expected: Node, identifier: String = "input", comment: String = ""): Assertion = {
    val (literalType, typeNode) = getType(expected)
    val assignment = IdentAsgn(typeNode, Ident(identifier), expected)
    parser.parse("begin " + literalType + " " + identifier + " = " + input + " " + comment + "\n end") shouldBe Success(Program(List(), assignment))
  }

  def parseFails(input: String, inputType: String, identifier: String = "input"): Assertion = {
    parser.parse("begin " + inputType + " " + identifier + " = " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
  }

  def parseWithIdentifier(name:String, success:Boolean): Assertion = {
    if (success) parseSucceeds("1", IntLiter(1), name) else parseFails("1", "int", name)
  }

  def commentIgnored(comment: String) = {
    parseSucceeds("1", IntLiter(1), "input", comment)
  }

  // Tests for IntLiter -----------------------------------------------------------------------------------------------
  "the parser" should "parse unsigned int literals" in {
    parseSucceeds("123", IntLiter(123))
  }

  it should "parse signed positive int literals" in {
    parseSucceeds("+123", IntLiter(123))
  }

  it should "parse signed negative int literals" in {
    parseSucceeds("-123", Neg(IntLiter(123)))
  }

  it should "reject doubly signed int literals" in {
    pending
    parseFails("++123", "int")
    parseFails("--123", "int")
  }

  // Tests for BoolLiter ----------------------------------------------------------------------------------------------
  it should "parse true bool literals" in {
    parseSucceeds("true", BoolLiter(true))
  }

  it should "parse false bool literals" in {
    parseSucceeds("false", BoolLiter(false))
  }

  // Tests for CharLiter ----------------------------------------------------------------------------------------------
  it should "parse char literals" in {
    parseSucceeds("\'a\'", CharLiter('a'))
  }

  it should "parse escaped char literals" in {
    pending
    parseSucceeds("\'\\0\'" , CharLiter('\u0000'))
    parseSucceeds("\'\\b\'" , CharLiter('\b'))
    parseSucceeds("\'\\n\'" , CharLiter('\n'))
    parseSucceeds("\'\\f\'" , CharLiter('\f'))
    parseSucceeds("\'\\r\'" , CharLiter('\r'))
    parseSucceeds("\'\\t\'" , CharLiter('\t'))
    parseSucceeds("\'\\\'\'", CharLiter('\''))
  }

  it should "reject invalid escaped char literals" in {
    // pending
    parseFails("\'\\a\'" , "char")
    parseFails("\'\\z\'" , "char")
    parseFails("\'\\\'"  , "char")
    parseFails("\'\\\"\'", "char")
    parseFails("\'\\x\'" , "char")
    parseFails("\'\\u\'" , "char")
    parseFails("\'\\U\'" , "char")
  }

  it should "reject invalid char literals" in {
    pending
    // TODO: Should we include empty char? If not, write a test making sure its not rejected
    parseFails("\'\'"  , "char") // Empty char
    parseFails("\'\\\'", "char") // Backslash
    parseFails("\'\"\'", "char") // Double quote
    parseFails("\'\'\'", "char") // Single quote
  }

  // Tests for StringLiter --------------------------------------------------------------------------------------------

  it should "parse empty string literals" in {
    parseSucceeds("\"\"", StringLiter(""))
  }

  it should "parse string literals" in {
    parseSucceeds("\"hello\"", StringLiter("hello"))
    parseSucceeds("\"hello world\"", StringLiter("hello world"))
  }

  it should "parse escaped string literals" in {
    pending
    parseSucceeds("\"\\0\"", StringLiter("\u0000"))
    parseSucceeds("\"\\b\"", StringLiter("\b"))
    parseSucceeds("\"\\n\"", StringLiter("\n"))
    parseSucceeds("\"\\f\"", StringLiter("\f"))
    parseSucceeds("\"\\r\"", StringLiter("\r"))
    parseSucceeds("\"\\t\"", StringLiter("\t"))
  }

  it should "reject invalid string literals" in {
    pending
    parseFails("\"\""  , "string") // Empty char
    parseFails("\"\\\"", "string") // Backslash
    parseFails("\"\"\"", "string") // Double quote
    parseFails("\"\'\"", "string") // Single quote
  }

  it should "reject invalid escaped string literals" in {
    pending
    parseFails("\"\\a\"" , "string")
    parseFails("\"\\z\"" , "string")
    parseFails("\"\\\'\"", "string")
    parseFails("\"\\\"\"", "string")
    parseFails("\"\\x\"" , "string")
    parseFails("\"\\u\"" , "string")
    parseFails("\"\\U\"" , "string")
  }

  // Tests for Null (PairLiter) ---------------------------------------------------------------------------------------
  it should "parse null literals" in {
    parseSucceeds("null", Null())
  }

  // Tests for Ident --------------------------------------------------------------------------------------------------
  it should "parse identifiers" in {
    parseWithIdentifier("hello", true)
  }

  it should "parse identifiers with underscores" in {
    pending
    parseWithIdentifier("hello_world", true)
  }

  it should "parse identifiers starting with underscores" in {
    pending
    parseWithIdentifier("_hello", true)
  }

  it should "parse identifiers with numbers" in {
    parseWithIdentifier("hello123", true)
  }

  it should "reject identifiers starting with numbers" in {
    parseWithIdentifier("123hello", false)
  }

  it should "parse identifiers with underscores and numbers" in {
    pending
    parseWithIdentifier("hello_world123", true)
  }

  // Tests for Comment ------------------------------------------------------------------------------------------
  it should "ignore comments" in {
    commentIgnored("# This is a comment")
    commentIgnored("## This is a comment")
  }

  it should "reject comments without EOL" in {
    parseFails("# This is a comment", "int")
  }

  // Tests for Brackets -----------------------------------------------------------------------------------------------
  it should "parse brackets" in {
    parseSucceeds("(123)"  , Brackets(IntLiter(123)))
    parseSucceeds("(true)" , Brackets(BoolLiter(true)))
    parseSucceeds("(\'a\')", Brackets(CharLiter('a')))
    parseSucceeds("(\"\")" , Brackets(StringLiter("")))
    parseSucceeds("(null)" , Brackets(Null()))
    parseSucceeds("(hello)", Brackets(Ident("hello")))
  }

  it should "reject brackets around identifiers" in {
    parseWithIdentifier("(hello)", false)
  }

  it should "parse double brackets" in {
    parseSucceeds("((123))"  , Brackets(Brackets(IntLiter(123))))
    parseSucceeds("((true))" , Brackets(Brackets(BoolLiter(true))))
    parseSucceeds("((\'a\'))", Brackets(Brackets(CharLiter('a'))))
    parseSucceeds("((\"\"))" , Brackets(Brackets(StringLiter(""))))
    parseSucceeds("((null))" , Brackets(Brackets(Null())))
    parseSucceeds("((hello))", Brackets(Brackets(Ident("hello"))))
  }

  // Tests for ArrayElem ----------------------------------------------------------------------------------------
  it should "parse array elements" in {
    pending
    parseSucceeds("arr[0]", IntLiter(1)) // TODO: Change to array
  }

  it should "parse 2d array elements" in {
    pending
    parseSucceeds("arr[0][1]", IntLiter(1)) // TODO: Change to array
  }

}
