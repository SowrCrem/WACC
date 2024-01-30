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

//   it should "parse a simple function program" in {
//     val input =
//       "begin int f() is return 3; return 5 end int ret = call f(); println ret end"
//     val expected = Success(
//       Program(
//         List(
//           Func(
//             IntType(),
//             Ident("f"),
//             ParamList(List()),
//             StatJoin(
//               List(Return(IntLiter(3)), Return(IntLiter(5)))
//             )
//           )
//         ),
//         StatJoin(
//           List(
//             IdentAsgn(IntType(), Ident("ret"), Call(Ident("f"), List())),
//             Println(Ident("ret"))
//           )
//         )
//       )
//     )

//     parser.parse(input) shouldBe expected

//   }
  it should "parse identifiers" in {
    ident.parse("hello") shouldBe Success("hello")
  }

  "atom" should "parse int literals" in {
    intParser.parse("123") shouldBe Success(IntLiter(123))
  }

  it should "parse bool literals" in {
    boolParser.parse("true") shouldBe Success(BoolLiter(true))
  }

  it should "parse char literals" in {
    charParser.parse("'a'") shouldBe Success(CharLiter('a'))
  }

  it should "parse string literals" in {
    stringParser.parse("\"hello\"") shouldBe Success(StringLiter("hello"))
  }

  it should "parse identifiers" in {
    identifierParser.parse("hello") shouldBe Success(Ident("hello"))
  }

  it should "parse brackets" in {
    bracketsParser.parse("(123)") shouldBe Success(Brackets(IntLiter(123)))
  }

  it should "parse pair literals" in {
    pairLitParser.parse("null") shouldBe Success(Null())
  }

}

