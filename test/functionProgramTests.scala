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
  Len,
  Skip,
  Read,
  If,
  While,
  Free,
  Return,
  Exit,
  Print,
  Println,
  StatJoin,
  IdentAsgn,
  IntType,
  PairType,
  PairElemType,
  ArrayType,
  BoolType,
  CharType,
  StringType,
  Func,
  ParamList,
  Param,
  Program,
  Type,
  Call
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import wacc.NewPair

class functionProgramTests extends AnyFlatSpec {

  "functionProgram" should "parse a function program" in {
    val input = "begin int f(int x) is skip end skip end"
    val expected = Success(
      Program(
        List(
          Func(
            IntType(),
            Ident("f"),
            ParamList(List(Param(IntType(), Ident("x")))),
            Skip()
          )
        ),
        Skip()
      )
    )

    parser.parse(input) shouldBe expected

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

}
