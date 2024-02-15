package test.frontend.unit.semantic

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
  SymbolTable,
  TypeChecker,
}
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class checkTypes extends SemanticUnitTester {
  
  // Tests for base-type --------------------------------------------------------------------------------------------------

  "The type checker" should "accept integer literals" in {
    checkSucceeds(IntLiter(1)(pos))
  }

  it should "reject non-integer assignments to an integer variable" in {
    checkFails(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), CharLiter('a')(pos))(pos), "Type mismatch: expected integer, got Some(char)")
  }

  it should "accept character literals" in {
    checkSucceeds(CharLiter('a')(pos))
  }

  it should "reject non-character assignments to a character variable" in {
    checkFails(IdentAsgn(CharTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos), "Type mismatch: expected char, got Some(integer)")
  }

  it should "accept string literals" in {
    checkSucceeds(StringLiter("hello")(pos))
  }

  it should "reject non-string assignments to a string variable" in {
    checkFails(IdentAsgn(StringTypeNode()(pos), Ident("x")(pos), CharLiter('a')(pos))(pos), "Type mismatch: expected string, got Some(char)")
  }

  it should "accept boolean literals" in {
    checkSucceeds(BoolLiter(true)(pos))
    checkSucceeds(BoolLiter(false)(pos))
  }

  it should "reject non-boolean assignments to a boolean variable" in {
    checkFails(IdentAsgn(BoolTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos), "Type mismatch: expected bool, got Some(integer)")
  }

  it should "accept brackets" in {
    checkSucceeds(Brackets(IntLiter(1)(pos))(pos))
  }

  // Tests for array-type -------------------------------------------------------------------------------------------------

  it should "accept int array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(IntTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos)
      )(pos)
    )
  }

  it should "reject non-int assignments to an int array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(IntTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(CharLiter('a')(pos), CharLiter('b')(pos), CharLiter('c')(pos)))(pos)
      )(pos),
      "Type mismatch: expected array of integer, got Some(array of char)"
    )
  }

  it should "accept bool array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(BoolTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(BoolLiter(true)(pos), BoolLiter(false)(pos)))(pos)
      )(pos)
    )
  }

  it should "reject non-bool assignments to a bool array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(BoolTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(CharLiter('a')(pos), CharLiter('b')(pos), CharLiter('c')(pos)))(pos)
      )(pos),
      "Type mismatch: expected array of bool, got Some(array of char)"
    )
  }

  it should "accept char array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(CharTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(CharLiter('a')(pos), CharLiter('b')(pos), CharLiter('c')(pos)))(pos)
      )(pos)
    )
  }

  it should "reject non-char assignments to a char array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(CharTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos)
      )(pos),
      "Type mismatch: expected array of char, got Some(array of integer)"
    )
  }

  it should "accept string array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(StringTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(StringLiter("hello")(pos), StringLiter("world")(pos)))(pos)
      )(pos)
    )
  }

  it should "reject non-string assignments to a string array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(StringTypeNode()(pos))(pos),
        Ident("x")(pos),
        ArrayLiter(List(CharLiter('a')(pos), CharLiter('b')(pos), CharLiter('c')(pos)))(pos)
      )(pos),
      "Type mismatch: expected array of string, got Some(array of char)"
    )
  }

  // Tests for pair-type --------------------------------------------------------------------------------------------------

  it should "accept pair types" in {
    checkSucceeds(
      IdentAsgn(
        PairTypeNode(IntTypeNode()(pos), BoolTypeNode()(pos))(pos),
        Ident("x")(pos),
        NewPair(IntLiter(1)(pos), BoolLiter(true)(pos))(pos)
      )(pos)
    )
  }

  // Tests for Programs -------------------------------------------------------------------------------------------------

  it should "accept fully-formed programs" in {
    val position = Program(
      List(
        Func(
          IntTypeNode()(pos),
          Ident("f")(pos),
          ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
          List(Return(IntLiter(1)(pos))(pos))
        )(pos),
        Func(
          IntTypeNode()(pos),
          Ident("g")(pos),
          ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
          List(Return(IntLiter(2)(pos))(pos))
        )(pos)
      ),
      List(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos))
    )(pos)
    checkSucceeds(position)
  }
}