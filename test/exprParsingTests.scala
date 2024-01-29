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
  Error,
  ArrayElem,
  Plus,
  Mul,
  Div,
  Not,
  Len
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._

class exprParsingTests extends AnyFlatSpec {

  "arrayelem" should "parse array elements" in {
    arrayelemParser.parse("hello[123]") shouldBe Success(
      ArrayElem(Ident("hello"), List(IntLiter(123)))
    )
  }

  it should "parse array elements with multiple indices" in {
    arrayelemParser.parse("hello[123][456]") shouldBe Success(
      ArrayElem(Ident("hello"), List(IntLiter(123), IntLiter(456)))
    )
  }

  it should "parse array elements with expressions" in {
    arrayelemParser.parse("hello[123 * 456]") shouldBe Success(
      ArrayElem(Ident("hello"), List(Mul(IntLiter(123), IntLiter(456))))
    )
  }

  "expr" should "parse int literals" in {
    expr.parse("123") shouldBe Success(IntLiter(123))
  }

  it should "parse bool literals" in {
    expr.parse("true") shouldBe Success(BoolLiter(true))
  }

  it should "parse char literals" in {
    expr.parse("'a'") shouldBe Success(CharLiter('a'))
  }

  it should "parse string literals" in {
    expr.parse("\"hello\"") shouldBe Success(StringLiter("hello"))
  }

  it should "parse identifiers" in {
    expr.parse("hello") shouldBe Success(Ident("hello"))
  }

  it should "parse brackets" in {
    expr.parse("(123)") shouldBe Success(Brackets(IntLiter(123)))
  }

  it should "parse pair literals" in {
    expr.parse("null") shouldBe Success(Null())
  }

  it should "parse multiplication binary operators" in {
    expr.parse("123 * 456") shouldBe Success(Mul(IntLiter(123), IntLiter(456)))
  }

  it should "parse binary operators with brackets" in {
    expr.parse("(123 * 456)") shouldBe Success(
      Brackets(Mul(IntLiter(123), IntLiter(456)))
    )
  }

  it should "parse binary operators with multiple brackets" in {
    expr.parse("((123 * 456))") shouldBe Success(
      Brackets(Brackets(Mul(IntLiter(123), IntLiter(456))))
    )
  }

  it should "parse division" in {
    expr.parse("123 / 456") shouldBe Success(Div(IntLiter(123), IntLiter(456)))
  }

  it should "parse unary operators" in {
    expr.parse("!true") shouldBe Success(Not(BoolLiter(true)))
  }

  it should "parse unary operators with brackets" in {
    expr.parse("!(true)") shouldBe Success(Not(Brackets(BoolLiter(true))))
  }

  it should "parse unary operators before binary operators" in {
    expr.parse("!true * 123") shouldBe Success(
      Mul(Not(BoolLiter(true)), IntLiter(123))
    )
    expr.parse("len(\"hi\") * 123") shouldBe Success(
      Mul(Len(Brackets(StringLiter("hi"))), IntLiter(123))
    )
  }

  it should "parse nested expressions in binary operators" in {
    expr.parse("123 * (456 + 789)") shouldBe Success(
      Mul(IntLiter(123), Brackets(Plus(IntLiter(456), IntLiter(789))))
    )
    expr.parse("(123 * 456) + 789") shouldBe Success(
      Plus(Brackets(Mul(IntLiter(123), IntLiter(456))), IntLiter(789))
    )
    expr.parse("123 * (456 + 789) * 101112") shouldBe Success(
      Mul(
        Mul(IntLiter(123), Brackets(Plus(IntLiter(456), IntLiter(789)))),
        IntLiter(101112)
      )
    )

  }

  it should "respect precedence of operators" in {
    expr.parse("123 * 456 + 789") shouldBe Success(
      Plus(Mul(IntLiter(123), IntLiter(456)), IntLiter(789))
    )
    expr.parse("123 + 456 * 789") shouldBe Success(
      Plus(IntLiter(123), Mul(IntLiter(456), IntLiter(789)))
    )
  }

}
