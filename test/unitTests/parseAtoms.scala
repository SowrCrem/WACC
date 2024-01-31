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

  def isSuccess[T](input: String, expected: T): Assertion = {
    val identifier = Ident("input")
    var literalType = "int"
    var typeNode: Type = IntType()
    
    // Dynamically generate the literal node based on the expected type
    var literal: Node = Null()
    var isError = false
    expected match {
      case exp: Int =>
        literal = IntLiter(exp)
        if (exp < 0) {
          literal = Neg(IntLiter(exp * -1))
        }
      case exp: Boolean =>
        literalType = "bool"
        literal = BoolLiter(exp)
        typeNode = BoolType()
      case exp: Char =>
        literalType = "char"
        literal = CharLiter(exp)
        typeNode = CharType()
      case exp: String =>
        literalType = "string"
        literal = StringLiter(exp)
        typeNode = StringType()
      case exp: Null =>
        literalType = "int"
        literal = Null()
      case exp: Error =>
        literal = Error("")
        isError = true
    }

    if (isError) {
      // Match on any Error with any String
      return parser.parse("begin " + literalType + " input = " + input + " end") should matchPattern { 
        case Error(_) => 
      }
    }
    val assignment = IdentAsgn(typeNode, identifier, literal)
    parser.parse("begin " + literalType + " input = " + input + " end") shouldBe Success(Program(List(), assignment))
  }

  // Tests for IntLiter -----------------------------------------------------------------------------------------------
  "the parser" should "parse unsigned int literals" in {
    isSuccess("123", 123)
  }

  it should "parse signed positive int literals" in {
    isSuccess("+123", 123)
  }

  it should "parse signed negative int literals" in {
    isSuccess("-123", -123)
  }

  it should "reject doubly signed int literals" in {
    pending
    isSuccess("--123", Error(""))
  }

  // Tests for BoolLiter ----------------------------------------------------------------------------------------------
  it should "parse true bool literals" in {
    isSuccess("true", true)
  }

  it should "parse false bool literals" in {
    isSuccess("false", false)
  }

  // Tests for CharLiter ----------------------------------------------------------------------------------------------
  it should "parse char literals" in {
    isSuccess("\'a\'", 'a')
  }

  it should "parse escaped char literals" in {
    pending
    isSuccess("\'\\0\'", '\u0000')
    isSuccess("\'\\b\'", '\b')
    isSuccess("\'\\n\'", '\n')
    isSuccess("\'\\f\'", '\f')
    isSuccess("\'\\r\'", '\r')
    isSuccess("\'\\t\'", '\t')
    isSuccess("\'\\\'\'", '\'')
  }

  it should "reject invalid escaped char literals" in {
    pending
    isSuccess("\'\\a\'", Error(""))
    isSuccess("\'\\z\'", Error(""))
    isSuccess("\'\\\'", Error(""))
    isSuccess("\'\\\"\'", Error(""))
    isSuccess("\'\\x\'", Error(""))
    isSuccess("\'\\u\'", Error(""))
    isSuccess("\'\\U\'", Error(""))
  }

  it should "reject invalid char literals" in {
    pending
    // TODO: Should we include empty char? If not, write a test making sure its not rejected
    isSuccess("\'\'"  , Error("")) // Empty char
    isSuccess("\'\\\'", Error("")) // Backslash
    isSuccess("\'\"\'", Error("")) // Double quote
    isSuccess("\'\'\'", Error("")) // Single quote
  }

  // Tests for StringLiter --------------------------------------------------------------------------------------------

  it should "parse empty string literals" in {
    isSuccess("\"\"", "")
  }

  it should "parse string literals" in {
    isSuccess("\"hello\"", "hello")
    isSuccess("\"hello world\"", "hello world")
  }

  it should "parse escaped string literals" in {
    pending
    isSuccess("\"\\0\"", "\u0000")
    isSuccess("\"\\b\"", "\b")
    isSuccess("\"\\n\"", "\n")
    isSuccess("\"\\f\"", "\f")
    isSuccess("\"\\r\"", "\r")
    isSuccess("\"\\t\"", "\t")
  }

  it should "reject invalid string literals" in {
    pending
    isSuccess("\"\""  , Error("")) // Empty char
    isSuccess("\"\\\"", Error("")) // Backslash
    isSuccess("\"\"\"", Error("")) // Double quote
    isSuccess("\"\'\"", Error("")) // Single quote
  }

  it should "reject invalid escaped string literals" in {
    pending
    isSuccess("\"\\a\"", Error(""))
    isSuccess("\"\\z\"", Error(""))
    isSuccess("\"\\\'\"", Error(""))
    isSuccess("\"\\\"\"", Error(""))
    isSuccess("\"\\x\"", Error(""))
    isSuccess("\"\\u\"", Error(""))
    isSuccess("\"\\U\"", Error(""))
  }

  // Tests for Null (PairLiter) ---------------------------------------------------------------------------------------
  it should "parse null literals" in {
    isSuccess("null", Null())
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
