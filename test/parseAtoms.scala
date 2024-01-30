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
  Node
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._

class parseAtoms extends AnyFlatSpec {

  // Tests for IntLiter
  def intTest(num: String): Result[String, Node] = parser.parse("begin int x = " + num + " end")

  "prog" should "parse unsigned int literals" in {
    intTest("123") contains Success(IntLiter(123))
  }

  it should "parse signed positive int literals" in {
    parser.parse("begin int x = +123 end") contains Success(IntLiter(123))
  }

  it should "parse signed negative int literals" in {
    parser.parse("begin int x = -123 end") contains Success(IntLiter(-123))
  }

  // it should "reject doubly signed int literals" in {
  //   parser.parse("begin int x = --123 end") shouldBe Error(_) // TODO: Add appropriate error message
  // }

  // Tests for BoolLiter
  it should "parse true bool literals" in {
    parser.parse("true") shouldBe Success(BoolLiter(true))
  }

  it should "parse false bool literals" in {
    parser.parse("false") shouldBe Success(BoolLiter(false))
  }

  // Tests for CharLiter
  it should "parse char literals" in {
    parser.parse("\'a\'") shouldBe Success(CharLiter('a'))
  }

  it should "parse escaped char literals" in {
    parser.parse("\'\\0\'") shouldBe Success(CharLiter('\u0000'))
    parser.parse("\'\\b\'") shouldBe Success(CharLiter('\b'))
    parser.parse("\'\\t\'") shouldBe Success(CharLiter('\t'))
    parser.parse("\'\\n\'") shouldBe Success(CharLiter('\n'))
    parser.parse("\'\\f\'") shouldBe Success(CharLiter('\f'))
    parser.parse("\'\\r\'") shouldBe Success(CharLiter('\r'))
    parser.parse("\'\\\"\'") shouldBe Success(CharLiter('\\'))
  }

  // Tests for StringLiter
  it should "parse string literals" in {
    parser.parse("\"hello\"") shouldBe Success(StringLiter("hello"))
  }

  // Tests for Null (PairLiter)
  it should "parse null literals" in {
    parser.parse("null") shouldBe Success(Null())
  }

  // Tests for Ident
  it should "parse identifiers" in {
    identifierParser.parse("hello") shouldBe Success(Ident("hello"))
  }

  // Tests for Comment


  // Tests for Brackets
  it should "parse brackets" in {
    bracketsParser.parse("(123)") shouldBe Success(Brackets(IntLiter(123)))
  }

}
