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
import java.lang.StringBuilder


class parseAtoms extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------

  def parseSucceeds(input: String, expected: Expr): Assertion =
    parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), Exit(expected)))

  def parseFails(input: String, errorMessage: String = ""): Assertion = errorMessage match {
    case "" => parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
    // Match on a Failure with the specific error message
    case _ => parser.parse("begin exit " + input + " end") match {
      case Failure (msg) => msg shouldBe errorMessage
      case _ => fail("Wrong Error Message")
    }
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
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 13):\n")
    errorBuilder.append("  unexpected \"+\"\n")
    errorBuilder.append("  expected digit\n")
    errorBuilder.append("  >begin exit ++123 end\n")
    errorBuilder.append("               ^")

    parseFails("++123", errorBuilder.toString())
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

  // Tests for Brackets -----------------------------------------------------------------------------------------------
  it should "parse brackets" in {
    parseSucceeds("(123)"  , Brackets(IntLiter(123)))
    parseSucceeds("(true)" , Brackets(BoolLiter(true)))
    parseSucceeds("(\'a\')", Brackets(CharLiter('a')))
    parseSucceeds("(\"\")" , Brackets(StringLiter("")))
    parseSucceeds("(null)" , Brackets(Null()))
    parseSucceeds("(hello)", Brackets(Ident("hello")))
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
