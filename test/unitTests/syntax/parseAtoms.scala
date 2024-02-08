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


class parseAtoms extends AnyFlatSpec {

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

  def parseSucceeds[T](input: String, expected: Expr, identifier: String = "", comment: String = ""): Assertion = identifier match {
    case "" => parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), Exit(expected)))
    case _  => {
      val (literalType, typeNode) = getType(expected)
      val assignment = IdentAsgn(IntTypeNode(), Ident(identifier), expected)
      parser.parse("begin int " + identifier + " = " + input + " " + comment + " end") shouldBe Success(Program(List(), assignment))
    }
  }

  def parseFails(input: String, errorMessage: String = "", identifier: String = ""): Assertion = identifier match {
    case "" => errorMessage match {
      case "" => parser.parse("begin exit " + input + " end") should matchPattern {
        case Failure(_) => // Match on any Failure
      }
      case _ => parser.parse("begin exit " + input + " end") should matchPattern {
        case Failure(msg) if msg == errorMessage => // Match on a Failure with the specific error message
      }
    }
    case _  => parser.parse("begin int " + identifier + " = " + input + " end") should matchPattern {
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
    parseFails("++123")
    parseFails("--123")
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

  it should "parse escaped char literals" ignore {
    parseSucceeds("\'\\0\'" , CharLiter('\u0000'))
    parseSucceeds("\'\\b\'" , CharLiter('\b'))
    parseSucceeds("\'\\n\'" , CharLiter('\n'))
    parseSucceeds("\'\\f\'" , CharLiter('\f'))
    parseSucceeds("\'\\r\'" , CharLiter('\r'))
    parseSucceeds("\'\\t\'" , CharLiter('\t'))
    parseSucceeds("\'\\\'\'", CharLiter('\''))
  }

  it should "reject invalid escaped char literals" ignore {
    parseFails("\'\\a\'")
    parseFails("\'\\z\'")
    parseFails("\'\\ \'")
    parseFails("\'\\\"\'")
    parseFails("\'\\x\'")
    parseFails("\'\\u\'")
    parseFails("\'\\U\'")
  }

  it should "reject invalid char literals" ignore {
    
    // TODO: Should we include empty char? If not, write a test making sure its not rejected
    parseFails("\'\'") // Empty char
    parseFails("\'\\\'") // Backslash
    parseFails("\'\"\'") // Double quote
    parseFails("\'\'\'") // Single quote
  }

  // Tests for StringLiter --------------------------------------------------------------------------------------------

  it should "parse empty string literals" in {
    parseSucceeds("\"\"", StringLiter(""))
  }

  it should "parse string literals" in {
    parseSucceeds("\"hello\"", StringLiter("hello"))
    parseSucceeds("\"hello world\"", StringLiter("hello world"))
  }

  it should "parse escaped string literals" ignore {
    
    parseSucceeds("\"\\0\"", StringLiter("\u0000"))
    parseSucceeds("\"\\b\"", StringLiter("\b"))
    parseSucceeds("\"\\n\"", StringLiter("\n"))
    parseSucceeds("\"\\f\"", StringLiter("\f"))
    parseSucceeds("\"\\r\"", StringLiter("\r"))
    parseSucceeds("\"\\t\"", StringLiter("\t"))
  }

  it should "reject invalid string literals" ignore {
    
    parseFails("\"\""  ) // Empty char
    parseFails("\"\\\"") // Backslash
    parseFails("\"\"\"") // Double quote
    parseFails("\"\'\"") // Single quote
  }

  it should "reject invalid escaped string literals" ignore {
    
    parseFails("\"\\a\"" )
    parseFails("\"\\z\"" )
    parseFails("\"\\\'\"")
    parseFails("\"\\\"\"")
    parseFails("\"\\x\"" )
    parseFails("\"\\u\"" )
    parseFails("\"\\U\"" )
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

  // Tests for Comment ------------------------------------------------------------------------------------------
  it should "ignore comments" in {
    commentIgnored("# This is a comment\n")
    commentIgnored("## This is a comment\n")
  }

  it should "reject comments without EOL" in {
    parseFails("# This is a comment")
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

}
