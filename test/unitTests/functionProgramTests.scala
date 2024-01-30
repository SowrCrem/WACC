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
    pending
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

  it should "parse a function program with multiple functions" in {
    pending
    val input =
      "begin int f(int x) is skip end int g(int x) is skip end skip end"
    val expected = Success(
      Program(
        List(
          Func(
            IntType(),
            Ident("f"),
            ParamList(List(Param(IntType(), Ident("x")))),
            Skip()
          ),
          Func(
            IntType(),
            Ident("g"),
            ParamList(List(Param(IntType(), Ident("x")))),
            Skip()
          )
        ),
        Skip()
      )
    )

    parser.parse(input) shouldBe expected

  }

  // Function calls must be assigned to a variable

  it should "parse functions that are called" in {
    pending
    val input =
      "begin int f(int x) is return 5 end int g(int x) is return 2 end int x = call f(5) end"
    val expected = Success(
      Program(
        List(
          Func(
            IntType(),
            Ident("f"),
            ParamList(List(Param(IntType(), Ident("x")))),
            Return(IntLiter(5))
          ),
          Func(
            IntType(),
            Ident("g"),
            ParamList(List(Param(IntType(), Ident("x")))),
            Return(IntLiter(2))
          )
        ),
        IdentAsgn(IntType(), Ident("x"), Call(Ident("f"), List(IntLiter(5))))
      )
    )

    parser.parse(input) shouldBe expected
  }
}
