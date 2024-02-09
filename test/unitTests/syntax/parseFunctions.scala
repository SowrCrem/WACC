package unitTests.syntax

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

  "functionProgram" should "parse a function program" in {
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

  it should "parse a function program with multiple functions" in {
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

  it should "parse functions that are called" in  {
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
        IdentAsgn(IntTypeNode(), Ident("x"), Call(Ident("f"), (ArgList(List(IntLiter(5)))))
        )
      )
    )

    parser.parse(input) shouldBe expected
  }


  // TODO: Functions can only be exited via return or exit, with no trailing code
  //       after the LAST return or exit 
  it should "parse function that are exited with a return" in {
    val input = 
      "begin int f(int x) is return 5 end int y = call f(5) end"
    val expected = Success(
      Program(
        List(
          Func(
            IntTypeNode(),
            Ident("f"),
            ParamList(List(Param(IntTypeNode(), Ident("x")))),
            Return(IntLiter(5))
          )
        ),
        IdentAsgn(IntTypeNode(), Ident("y"), Call(Ident("f"), (ArgList(List(IntLiter(5)))))
        )
      )
    )

    parser.parse(input) shouldBe expected
  }

  it should "reject functions that are exited with a statement after the return" in {
    val input = 
      "begin int f(int x) is return 5 int z = 3 end int y = call f(5) end"
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 32):\n")
    errorBuilder.append("  unexpected int\n")
    errorBuilder.append("  expected end\n")
    errorBuilder.append("  >begin int f(int x) is return 5 int z = 3 end int y = call f(5) end\n")
    errorBuilder.append("                                  ^^^")

    parser.parse(input) shouldBe Failure(errorBuilder.toString())
  }
}
