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

  def parseFails(input: String, inputType: String): Assertion = {
    parser.parse("begin " + inputType + " input = " + input + " end") should matchPattern { 
      // Match on any Failure with any String
      case Failure(_) => 
    }
  }

  def parseSucceeds[T](input: String, expected: T): Assertion = {
    val identifier = Ident("input")
    var literalType = "int"
    var typeNode: Type = IntType()
    
    // Dynamically generate the literal node based on the expected type
    var literal: Node = Null()
    expected match {
      case exp: Int =>
        literal = if (exp < 0) Neg(IntLiter(exp * -1)) else IntLiter(exp)
      case exp: Boolean =>
        literalType = "bool"
        literal     = BoolLiter(exp)
        typeNode    = BoolType()
      case exp: Char =>
        literalType = "char"
        literal     = CharLiter(exp)
        typeNode    = CharType()
      case exp: String =>
        literalType = "string"
        literal     = StringLiter(exp)
        typeNode    = StringType()
      case exp: Null =>
        literal     = Null()
      case _ =>
        throw new IllegalArgumentException("Unsupported type")
    }

    val assignment = IdentAsgn(typeNode, identifier, literal)
    parser.parse("begin " + literalType + " input = " + input + " end") shouldBe Success(Program(List(), assignment))
  }

  // Tests for IntLiter -----------------------------------------------------------------------------------------------
  "the parser" should "parse unsigned int literals" in {
    parseSucceeds("123", 123)
  }

  it should "parse signed positive int literals" in {
    parseSucceeds("+123", 123)
  }

  it should "parse signed negative int literals" in {
    parseSucceeds("-123", -123)
  }

  it should "reject doubly signed int literals" in {
    pending
    parseFails("++123", "int")
    parseFails("--123", "int")
  }

  // Tests for BoolLiter ----------------------------------------------------------------------------------------------
  it should "parse true bool literals" in {
    parseSucceeds("true", true)
  }

  it should "parse false bool literals" in {
    parseSucceeds("false", false)
  }

  // Tests for CharLiter ----------------------------------------------------------------------------------------------
  it should "parse char literals" in {
    parseSucceeds("\'a\'", 'a')
  }

  it should "parse escaped char literals" in {
    pending
    parseSucceeds("\'\\0\'" , '\u0000')
    parseSucceeds("\'\\b\'" , '\b')
    parseSucceeds("\'\\n\'" , '\n')
    parseSucceeds("\'\\f\'" , '\f')
    parseSucceeds("\'\\r\'" , '\r')
    parseSucceeds("\'\\t\'" , '\t')
    parseSucceeds("\'\\\'\'", '\'')
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
    parseSucceeds("\"\"", "")
  }

  it should "parse string literals" in {
    parseSucceeds("\"hello\"", "hello")
    parseSucceeds("\"hello world\"", "hello world")
  }

  it should "parse escaped string literals" in {
    pending
    parseSucceeds("\"\\0\"", "\u0000")
    parseSucceeds("\"\\b\"", "\b")
    parseSucceeds("\"\\n\"", "\n")
    parseSucceeds("\"\\f\"", "\f")
    parseSucceeds("\"\\r\"", "\r")
    parseSucceeds("\"\\t\"", "\t")
  }

  it should "reject invalid string literals" in {
    pending
    parseSucceeds("\"\""  , "string") // Empty char
    parseSucceeds("\"\\\"", "string") // Backslash
    parseSucceeds("\"\"\"", "string") // Double quote
    parseSucceeds("\"\'\"", "string") // Single quote
  }

  it should "reject invalid escaped string literals" in {
    pending
    parseSucceeds("\"\\a\"" , "string")
    parseSucceeds("\"\\z\"" , "string")
    parseSucceeds("\"\\\'\"", "string")
    parseSucceeds("\"\\\"\"", "string")
    parseSucceeds("\"\\x\"" , "string")
    parseSucceeds("\"\\u\"" , "string")
    parseSucceeds("\"\\U\"" , "string")
  }

  // Tests for Null (PairLiter) ---------------------------------------------------------------------------------------
  it should "parse null literals" in {
    parseSucceeds("null", Null())
  }

  // Tests for Ident --------------------------------------------------------------------------------------------------
  it should "parse identifiers" in {
    pending
    // parser.parse("hello") shouldBe Success(Ident("hello"))
  }

  // TODO: Tests for Comment ------------------------------------------------------------------------------------------


  // Tests for Brackets -----------------------------------------------------------------------------------------------
  it should "parse brackets" in {
    pending
    parser.parse("(123)") shouldBe Success(Brackets(IntLiter(123)))
  }

  // TODO: Tests for ArrayElem ----------------------------------------------------------------------------------------

}
