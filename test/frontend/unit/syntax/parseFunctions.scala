package test.frontend.unit.syntax

import wacc.Main
import test.Utils._
import wacc.lexer._
import wacc.parser._
import wacc.{
  Program,
  Func,
  Expr,
  Neg,
  Not,
  Len,
  Ord,
  Chr,
  Mul,
  Div,
  Mod,
  Skip,
  Plus,
  Minus,
  GreaterThan,
  GreaterThanEq,
  LessThan,
  LessThanEq,
  Equals,
  NotEquals,
  And,
  Or,
  Call,
  Null,
  Exit,
  Error,
  Ident,
  Param,
  Return,
  ParamList,
  TypeNode,
  Position,
  Brackets,
  IdentAsgn,
  IntLiter,
  BoolLiter,
  CharLiter,
  StringLiter,
  ArrayLiter,
  IntTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  ArrayTypeNode,
  ArrayElem,
}
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._


class parseFunctions extends AnyFlatSpec {

  "functionProgram" should "parse a function program" in {
    val input = "begin int f(int x) is skip end skip end"
    val expected = Success(
      Program(
        List(
          Func(
            IntTypeNode()(pos),
            Ident("f")(pos),
            ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
            Skip()(pos)
          )(pos)
        ),
        Skip()(pos)
      )(pos)
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
            IntTypeNode()(pos),
            Ident("f")(pos),
            ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
            Skip()(pos)
          )(pos),
          Func(
            IntTypeNode()(pos),
            Ident("g")(pos),
            ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
            Skip()(pos)
          )(pos)
        ),
        Skip()(pos)
      )(pos)
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
            IntTypeNode()(pos),
            Ident("f")(pos),
            ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
            Return(IntLiter(5)(pos))(pos)
          )(pos),
          Func(
            IntTypeNode()(pos),
            Ident("g")(pos),
            ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
            Return(IntLiter(2)(pos))(pos)
          )(pos)
        ),
        IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), Call(Ident("f")(pos), (List(IntLiter(5)(pos))))(pos))(pos)
      )(pos)
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
            IntTypeNode()(pos),
            Ident("f")(pos),
            ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
            Return(IntLiter(5)(pos))(pos)
          )(pos)
        ),
        IdentAsgn(IntTypeNode()(pos), Ident("y")(pos), Call(Ident("f")(pos), List(IntLiter(5)(pos)))(pos))(pos)
        )(pos)
      )

    parser.parse(input) shouldBe expected
  }

  it should "reject functions that are exited with a statement after the return" in {
    pending
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
