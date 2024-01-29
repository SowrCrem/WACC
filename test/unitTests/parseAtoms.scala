import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  IntLiter,
  CharLiter,
  StringLiter,
  BoolLiter,
  Ident,
  Brackets,
  Null,
  Error
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._

//TODO: tests for integer overflow

class ParseAtoms extends AnyFlatSpec {

  // Tests for IntLiter
  "prog" should "parse unsigned int literals" in {
    parser.parse("123")  shouldBe Success(IntLiter(123))
  }

  it should "parse signed positive int literals" in {
    parser.parse("+123") shouldBe Success(IntLiter(123))
  }

  it should "parse signed negative int literals" in {
    parser.parse("-123") shouldBe Success(IntLiter(-123))
  }

  it should "not parse doubly signed int literals" in {
    parser.parse("--123") shouldBe Error("") // TODO: Write Appropriate Error Message
  }

  // Tests for BoolLiter
  it should "parse true bool literals" in {
    parser.parse("true") shouldBe Success(BoolLiter(true))
  }

  it should "parse false bool literals" in {
    parser.parse("false") shouldBe Success(BoolLiter(false))
  }

  // Tests for Charliter
  it should "parse char literals" in {
    parser.parse("'a'") shouldBe Success(CharLiter('a'))
  }

  it should "reject backslash characters" in {
    parser.parse("'\\'") shouldBe Error("") // TODO: Write Appropriate Error Message
  }

  it should "reject single quote characters" in {
    parser.parse("'\''") shouldBe Error("") // TODO: Write Appropriate Error Message
  }

  it should "reject double quote characters" in {
    parser.parse("'\"'") shouldBe Error("") // TODO: Write Appropriate Error Message
  }

  it should "parse escaped char literals" in {
    parser.parse("'\\0") shouldBe Success(CharLiter('\u0000'))
    parser.parse("'\\b") shouldBe Success(CharLiter('\b'))
    parser.parse("'\\t") shouldBe Success(CharLiter('\b'))
    parser.parse("'\\n") shouldBe Success(CharLiter('\b'))
    parser.parse("'\\f") shouldBe Success(CharLiter('\b'))
    parser.parse("'\\r") shouldBe Success(CharLiter('\b'))
    parser.parse("'\\\"") shouldBe Success(CharLiter('\b'))
    parser.parse("'\\r") shouldBe Success(CharLiter('\b'))
  }

  // Tests for StringLiter
  it should "parse string literals" in {
    parser.parse("\"hello\"") shouldBe Success(StringLiter("hello"))
  }

  // Tests for Null (PairLiter)
  it should "parse null values" in {
    parser.parse("null") shouldBe Success(Null())
  }

  // Tests for Ident
  it should "parse identifiers" in {
    parser.parse("hello") shouldBe Success(Ident("hello"))
  }  

  // TODO: Tests for Array-Elem

  // Tests for Brackets
  it should "parse brackets" in {
    parser.parse("(123)") shouldBe Success(Brackets(IntLiter(123)))
  }

}
