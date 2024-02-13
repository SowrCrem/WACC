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
  SymbolTable,
  TypeChecker,
}
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._


class checkExprs extends SemanticUnitTester {

  // Tests for Unary Operator ------------------------------------------------------------------------------------------

  it should "accept boolean negations" in {
    checkSucceeds(Not(BoolLiter(true)(pos))(pos))
    checkSucceeds(Not(BoolLiter(false)(pos))(pos))
  }

  it should "reject negations with non-bool types" in {
    checkFails(Not(IntLiter(1)(pos))(pos),          "Expected bool type, but got int type")
    checkFails(Not(CharLiter('a')(pos))(pos),       "Expected bool type, but got char type")
    checkFails(Not(StringLiter("hello")(pos))(pos), "Expected bool type, but got string type")
    checkFails(Not(Null()(pos))(pos),               "Expected bool type, but got null type")
  }

  it should "accept negations with brackets" in {
    checkSucceeds(Not(Brackets(BoolLiter(true)(pos))(pos))(pos))
    checkSucceeds(Not(Brackets(BoolLiter(false)(pos))(pos))(pos))
  }

  it should "accept integer negations" in {
    checkSucceeds(Neg(IntLiter(1)(pos))(pos))
  }

  it should "reject negations with non-int types" in {
    checkFails(Neg(BoolLiter(true)(pos))(pos),      "Expected int type, but got bool type")
    checkFails(Neg(CharLiter('a')(pos))(pos),       "Expected int type, but got char type")
    checkFails(Neg(StringLiter("hello")(pos))(pos), "Expected int type, but got string type")
    checkFails(Neg(Null()(pos))(pos),               "Expected int type, but got null type")
  }

  it should "accept len" in {
    checkSucceeds(Len(StringLiter("hello")(pos))(pos))
    checkSucceeds(Len(ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos))(pos))
  }

  it should "reject len with non-array or non-string types" in {
    checkFails(Len(BoolLiter(true)(pos))(pos), "Expected array or string type, but got bool type")
    checkFails(Len(IntLiter(1)(pos))(pos),     "Expected array or string type, but got int type")
    checkFails(Len(CharLiter('a')(pos))(pos),  "Expected array or string type, but got char type")
    checkFails(Len(Null()(pos))(pos),          "Expected array or string type, but got null type")
  }

  it should "accept ord" in {
    checkSucceeds(Ord(CharLiter('a')(pos))(pos))
  }

  it should "reject ord with non-char types" in {
    checkFails(Ord(BoolLiter(true)(pos))(pos),      "Expected char type, but got bool type")
    checkFails(Ord(IntLiter(1)(pos))(pos),          "Expected char type, but got int type")
    checkFails(Ord(StringLiter("hello")(pos))(pos), "Expected char type, but got string type")
    checkFails(Ord(Null()(pos))(pos),               "Expected char type, but got null type")
  }


  // Tests for Binary Operator -----------------------------------------------------------------------------------------

  it should "accept multiplication" in {
    checkSucceeds(Mul(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    checkSucceeds(Mul(Mul(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "reject multiplication with non-int types" in {
    checkFails(Mul(BoolLiter(true)(pos), IntLiter(1)(pos))(pos),      "Expected int type, but got bool type")
    checkFails(Mul(CharLiter('a')(pos), IntLiter(1)(pos))(pos),       "Expected int type, but got char type")
    checkFails(Mul(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(Mul(Null()(pos), IntLiter(1)(pos))(pos),               "Expected int type, but got null type")
  }

  it should "accept division" in {
    checkSucceeds(Div(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    checkSucceeds(Div(Div(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "reject division with non-int types" in {
    checkFails(Div(BoolLiter(true)(pos), IntLiter(1)(pos))(pos),      "Expected int type, but got bool type")
    checkFails(Div(CharLiter('a')(pos), IntLiter(1)(pos))(pos),       "Expected int type, but got char type")
    checkFails(Div(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(Div(Null()(pos), IntLiter(1)(pos))(pos),               "Expected int type, but got null type")
  }

  it should "accept modulo" in {
    checkSucceeds(Mod(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    checkSucceeds(Mod(Mod(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "reject modulo with non-int types" in {
    checkFails(Mod(BoolLiter(true)(pos), IntLiter(1)(pos))(pos),      "Expected int type, but got bool type")
    checkFails(Mod(CharLiter('a')(pos), IntLiter(1)(pos))(pos),       "Expected int type, but got char type")
    checkFails(Mod(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(Mod(Null()(pos), IntLiter(1)(pos))(pos),               "Expected int type, but got null type")
  }

  it should "accept addition" in {
    checkSucceeds(Plus(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    checkSucceeds(Plus(Plus(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "reject addition with non-int types" in {
    checkFails(Plus(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(Plus(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(Plus(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(Plus(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept subtraction" in {
    checkSucceeds(Minus(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    checkSucceeds(Minus(Minus(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "reject subtraction with non-int types" in {
    checkFails(Minus(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(Minus(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(Minus(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(Minus(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept greater than" in {
    checkSucceeds(GreaterThan(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }

  it should "reject greater than with non-int types" in {
    checkFails(GreaterThan(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(GreaterThan(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(GreaterThan(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(GreaterThan(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept greater than or equal" in {
    checkSucceeds(GreaterThanEq(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }

  it should "reject greater than or equal with non-int types" in {
    checkFails(GreaterThanEq(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(GreaterThanEq(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(GreaterThanEq(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(GreaterThanEq(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept less than" in {
    checkSucceeds(LessThan(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }

  it should "reject less than with non-int types" in {
    checkFails(LessThan(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(LessThan(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(LessThan(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(LessThan(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept less than or equal" in {
    checkSucceeds(LessThanEq(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }

  it should "reject less than or equal with non-int types" in {
    checkFails(LessThanEq(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(LessThanEq(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(LessThanEq(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(LessThanEq(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept equals" in {
    checkSucceeds(Equals(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    checkSucceeds(Equals(CharLiter('a')(pos), CharLiter('b')(pos))(pos))
    checkSucceeds(Equals(StringLiter("hello")(pos), StringLiter("world")(pos))(pos))
    checkSucceeds(Equals(Null()(pos), Null()(pos))(pos))
  }

  it should "reject equals with non-matching types" in {
    checkFails(Equals(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Type mismatch: expected bool, got int")
    checkFails(Equals(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Type mismatch: expected char, got int")
    checkFails(Equals(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Type mismatch: expected string, got int")
    checkFails(Equals(Null()(pos), IntLiter(1)(pos))(pos), "Type mismatch: expected null, got int")
  }

  it should "accept not equals" in {
    checkSucceeds(NotEquals(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }

  it should "reject not equals with non-int types" in {
    checkFails(NotEquals(BoolLiter(true)(pos), IntLiter(1)(pos))(pos), "Expected int type, but got bool type")
    checkFails(NotEquals(CharLiter('a')(pos), IntLiter(1)(pos))(pos), "Expected int type, but got char type")
    checkFails(NotEquals(StringLiter("hello")(pos), IntLiter(1)(pos))(pos), "Expected int type, but got string type")
    checkFails(NotEquals(Null()(pos), IntLiter(1)(pos))(pos), "Expected int type, but got null type")
  }

  it should "accept and" in {
    checkSucceeds(And(BoolLiter(true)(pos), BoolLiter(false)(pos))(pos))
  }

  it should "reject and with non-bool types" in {
    checkFails(And(IntLiter(1)(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got int type")
    checkFails(And(CharLiter('a')(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got char type")
    checkFails(And(StringLiter("hello")(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got string type")
    checkFails(And(Null()(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got null type")
  }

  it should "accept or" in {
    checkSucceeds(Or(BoolLiter(true)(pos), BoolLiter(false)(pos))(pos))
  }

  it should "reject or with non-bool types" in {
    checkFails(Or(IntLiter(1)(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got int type")
    checkFails(Or(CharLiter('a')(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got char type")
    checkFails(Or(StringLiter("hello")(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got string type")
    checkFails(Or(Null()(pos), BoolLiter(true)(pos))(pos), "Expected bool type, but got null type")
  }

  // Tests for ArrayElem ----------------------------------------------------------------------------------------
  
  it should "accept array elements" in {
    checkSucceeds(ArrayElem(Ident("arr")(pos), List(IntLiter(0)(pos)))(pos))
  }

  it should "accept 2d array elements" in {
    checkSucceeds(ArrayElem(Ident("arr")(pos), List(IntLiter(0)(pos), IntLiter(1)(pos)))(pos))
  }

}