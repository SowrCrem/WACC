import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  IntLiterNode,
  CharLiterNode,
  StringLiterNode,
  BoolLiterNode,
  IdentNode,
  BracketsNode,
  PairLiterNode,
  ErrorNode
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._

class lexerTests extends AnyFlatSpec {

  it should "parse int literals" in {
    integer.parse("123") shouldBe Success(123)
  }

  it should "parse char literals" in {
    char.parse("'a'") shouldBe Success('a')
  }

  it should "parse string literals" in {
    string.parse("\"hello\"") shouldBe Success("hello")
  }

  it should "parse identifiers" in {
    ident.parse("hello") shouldBe Success("hello")
  }

  "atom" should "parse int literals" in {
    atoms.parse("123") shouldBe Success(IntLiterNode(123))
  }

  it should "parse bool literals" in {
    atoms.parse("true") shouldBe Success(BoolLiterNode(true))
  }

  it should "parse char literals" in {
    atoms.parse("'a'") shouldBe Success(CharLiterNode('a'))
  }

  it should "parse string literals" in {
    atoms.parse("\"hello\"") shouldBe Success(StringLiterNode("hello"))
  }

  it should "parse identifiers" in {
    atoms.parse("hello") shouldBe Success(IdentNode("hello"))
  }

  it should "parse brackets" in {
    atoms.parse("(123)") shouldBe Success(BracketsNode(IntLiterNode(123)))
  }

  it should "parse pair literals" in {
    atoms.parse("null") shouldBe Success(PairLiterNode())
  }

}
