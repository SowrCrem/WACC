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

class checkFunctions extends SemanticUnitTester {

  // Tests for Functions ------------------------------------------------------------------------------------------------
  
  "The type checker" should "accept function calls" in {
    val position = Program(List(),
      List(Func(
          IntTypeNode()(pos),
          Ident("f")(pos),
          ParamList(List(Param(IntTypeNode()(pos), Ident("x")(pos))(pos)))(pos),
          List(Return(IntLiter(1)(pos))(pos))
        )(pos)),
      List(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), Call(Ident("f")(pos), List(IntLiter(1)(pos)))(pos))(pos))
    )(pos)
    checkSucceeds(position)
  }

}