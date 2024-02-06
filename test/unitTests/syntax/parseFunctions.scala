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
  IntTypeNode,
  PairTypeNode,
  PairElemTypeNode,
  ArrayTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  Func,
  ParamList,
  Param,
  Program,
  TypeNode,
  Call,
  Expr,
  NewPair,
  ArgList
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion

class parseFunctions extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------

  def parseSucceeds[T](input: String, expected: Expr): Assertion = {
    parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), Exit(expected)))
  }

  def parseFails(input: String): Assertion = {
    parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
  }

  // Tests --------------------------------------------------------------------------------------------------------------

  "functionProgram" should "parse a function program" ignore {
    val input = "begin int f(int x) is skip end skip end"
    val expected = Success(
      Program(
        List(
          Func(
            IntTypeNode(),
            Ident("f"),
            ParamList(List(Param(IntTypeNode(), Ident("x")))),
            Skip()
          )
        ),
        Skip()
      )
    )

    parser.parse(input) shouldBe expected

  }

  it should "parse a function program with multiple functions" ignore {
    val input =
      "begin int f(int x) is skip end int g(int x) is skip end skip end"
    val expected = Success(
      Program(
        List(
          Func(
            IntTypeNode(),
            Ident("f"),
            ParamList(List(Param(IntTypeNode(), Ident("x")))),
            Skip()
          ),
          Func(
            IntTypeNode(),
            Ident("g"),
            ParamList(List(Param(IntTypeNode(), Ident("x")))),
            Skip()
          )
        ),
        Skip()
      )
    )

    parser.parse(input) shouldBe expected

  }

  // Function calls must be assigned to a variable

  it should "parse functions that are called" ignore {
    val input =
      "begin int f(int x) is return 5 end int g(int x) is return 2 end int x = call f(5) end"
    val expected = Success(
      Program(
        List(
          Func(
            IntTypeNode(),
            Ident("f"),
            ParamList(List(Param(IntTypeNode(), Ident("x")))),
            Return(IntLiter(5))
          ),
          Func(
            IntTypeNode(),
            Ident("g"),
            ParamList(List(Param(IntTypeNode(), Ident("x")))),
            Return(IntLiter(2))
          )
        ),
        IdentAsgn(IntTypeNode(), Ident("x"), Call(Ident("f"), (ParamList(List(Param(IntTypeNode(), IntLiter(5)))))))
      )
    )

    parser.parse(input) shouldBe expected
  }

}
