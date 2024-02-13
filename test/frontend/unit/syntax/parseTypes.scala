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
  Stat,
  Read,
  Free,
  Print,
  Println,
  If,
  While,
  StatJoin,
  ParamList,
  TypeNode,
  Position,
  Brackets,
  IdentAsgn,
  AsgnEq,
  IntLiter,
  BoolLiter,
  CharLiter,
  StringLiter,
  ArrayLiter,
  IntTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  PairTypeNode,
  NewPair,
  FstNode,
  SndNode,
  ArrayTypeNode,
  ArrayElem,
}
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class parseTypes extends AnyFlatSpec {

  // Tests for base-type --------------------------------------------------------------------------------------------------

  "The parser" should "parse int types" in {
    parseSucceeds("int x = 5",
      IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(5)(pos))(pos)
    )
  }

  it should "parse bool types" in {
    parseSucceeds("bool x = true",
      IdentAsgn(BoolTypeNode()(pos), Ident("x")(pos), BoolLiter(true)(pos))(pos)
    )
  }

  it should "parse char types" in {
    parseSucceeds(
      "char x = 'a'",
      IdentAsgn(CharTypeNode()(pos), Ident("x")(pos), CharLiter('a')(pos))(pos)
    )
  }

  it should "parse string types" in {
    parseSucceeds(
      "string x = \"hello\"",
      IdentAsgn(StringTypeNode()(pos), Ident("x")(pos), StringLiter("hello")(pos))(pos)
    )
  }

  // Tests for array-type -------------------------------------------------------------------------------------------------

  it should "parse int array types" in {
    parseSucceeds(
      "int[] x = [1, 2, 3]",
      IdentAsgn(
        ArrayTypeNode(IntTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos)
      )(pos)
    )
  }

  it should "parse bool array types" in {
    parseSucceeds(
      "bool[] x = [true, false]",
      IdentAsgn(
        ArrayTypeNode(BoolTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(BoolLiter(true)(pos), BoolLiter(false)(pos)))(pos)
      )(pos)
    )
  }

  it should "parse char array types" in {
    parseSucceeds(
      "char[] x = ['a', 'b', 'c']",
      IdentAsgn(
        ArrayTypeNode(CharTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(CharLiter('a')(pos), CharLiter('b')(pos), CharLiter('c')(pos)))(pos)
      )(pos)
    )
  }

  it should "parse string array types" in {
    parseSucceeds(
      "string[] x = [\"hello\", \"world\"]",
      IdentAsgn(
        ArrayTypeNode(StringTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(StringLiter("hello")(pos), StringLiter("world")(pos)))(pos)
      )(pos)
    )
  }

  // Tests for pair-type --------------------------------------------------------------------------------------------------

  it should "parse pair types" in {
    parseSucceeds(
      "pair(int, bool) x = newpair(1, true)",
      IdentAsgn(
        PairTypeNode(IntTypeNode()(pos), BoolTypeNode()(pos))(pos),
        Ident("x")(pos),
        NewPair(IntLiter(1)(pos), BoolLiter(true)(pos))(pos)
      )(pos)
    )
  }

  it should "parse pair types with array types" in {
    parser.parse(
      "begin int[] a = [1,2,3,4]; bool[] b = [true, false]; pair(int[], bool[]) x = newpair(a, b) end"
    ) shouldBe Success(
      Program(
        List(),
        StatJoin(
          List(
            IdentAsgn(
              ArrayTypeNode(IntTypeNode()(pos))(pos),
              Ident("a")(pos),
              ArrayLiter(
                List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos), IntLiter(4)(pos))
              )(pos)
            )(pos),
            IdentAsgn(
              ArrayTypeNode(BoolTypeNode()(pos))(pos),
              Ident("b")(pos),
              ArrayLiter(List(BoolLiter(true)(pos), BoolLiter(false)(pos)))(pos)
            )(pos),
            IdentAsgn(
              PairTypeNode(
                ArrayTypeNode(IntTypeNode()(pos))(pos),
                ArrayTypeNode(BoolTypeNode()(pos))(pos)
              )(pos),
              Ident("x")(pos),
              NewPair(Ident("a")(pos), Ident("b")(pos))(pos)
            )(pos)
          )
        )(pos)
      )(pos)
    )
  }


  it should "reject pair types with pair types" in {
    parseFails(
      "pair(pair(int, bool), pair(bool, char)) x = newpair(newpair(1, true), newpair(true, 'a'))"
    )
  }

  it should "parse pair types with pair array types" ignore {
    parseSucceeds(
      "pair(int, pair(bool, char)[]) x = newpair(1, newpair(true, 'a')[])",
      IdentAsgn(
        PairTypeNode(
          IntTypeNode()(pos),
          ArrayTypeNode(PairTypeNode(BoolTypeNode()(pos), CharTypeNode()(pos))(pos))(pos)
        )(pos),
        Ident("x")(pos),
        NewPair(
          IntLiter(1)(pos),
          ArrayLiter(List(NewPair(BoolLiter(true)(pos), CharLiter('a')(pos))(pos)))(pos)
        )(pos)
      )(pos)
    )
  }
}
