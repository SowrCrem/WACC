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

class parseAtoms extends AnyFlatSpec {

  it should "parse int literals" in {
    parser.parse("123") shouldBe Success(123)
  }

  it should "parse char literals" in {
    parser.parse("'a'") shouldBe Success('a')
  }

  it should "parse string literals" in {
    parser.parse("\"hello\"") shouldBe Success("hello")
  }

  it should "parse identifiers" in {
    parser.parse("hello") shouldBe Success("hello")
  }

  "atom" should "parse int literals" in {
    parser.parse("123") shouldBe Success(IntLiter(123))
  }

  it should "parse bool literals" in {
    parser.parse("true") shouldBe Success(BoolLiter(true))
  }

  it should "parse char literals" in {
    parser.parse("'a'") shouldBe Success(CharLiter('a'))
  }

  it should "parse string literals" in {
    parser.parse("\"hello\"") shouldBe Success(StringLiter("hello"))
  }

  it should "parse identifiers" in {
    parser.parse("hello") shouldBe Success(Ident("hello"))
  }

  it should "parse brackets" in {
    parser.parse("(123)") shouldBe Success(Brackets(IntLiter(123)))
  }

  it should "parse pair literals" in {
    parser.parse("null") shouldBe Success(Null())
  }

}
