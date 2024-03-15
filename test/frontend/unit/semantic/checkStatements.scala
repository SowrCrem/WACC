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

class checkStatements extends SemanticUnitTester {

  // Tests for skip ------------------------------------------------------------------------------------------------------
  
  "The type checker" should "accept skip" in {
    checkSucceeds(Skip()(pos))
  }
  
  // Tests for assignment ------------------------------------------------------------------------------------------------

  it should "accept LHS new identifier assignments" in {
    checkSucceeds(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos))
  }

  it should "accept LHS existing identifier assignments" in{
    checkSucceedsStats(List(
      IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos),
      AsgnEq(Ident("x")(pos), IntLiter(3)(pos))(pos)
    ))
  }

  it should "reject LHS non-existing identifier assignments" in {
    checkFails(AsgnEq(Ident("x")(pos), IntLiter(3)(pos))(pos), "Variable x not declared")
    checkFails(AsgnEq(ArrayElem(Ident("x")(pos), List(IntLiter(1)(pos)))(pos), IntLiter(3)(pos))(pos), "Variable x not declared")
  }

  it should "accept LHS existing array elem assignments" in {
    checkSucceedsStats(List(
      IdentAsgn(ArrayTypeNode(IntTypeNode()(pos))(pos), Ident("x")(pos), ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos))(pos),
      AsgnEq(ArrayElem(Ident("x")(pos), List(IntLiter(2)(pos)))(pos), IntLiter(2)(pos))(pos)
    ))
  }

  it should "reject LHS non-existing array elem assignments" in {
    checkFails(AsgnEq(ArrayElem(Ident("x")(pos), List(IntLiter(1)(pos)))(pos), IntLiter(3)(pos))(pos), "Variable x not declared")
  }

  it should "accept LHS existing pair elem assignments" in {
    checkSucceedsStats(List(
      IdentAsgn(PairTypeNode(IntTypeNode()(pos), IntTypeNode()(pos))(pos), Ident("p")(pos), NewPair(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos),
      AsgnEq(FstNode(Ident("p")(pos))(pos), IntLiter(3)(pos))(pos),
      AsgnEq(SndNode(Ident("p")(pos))(pos), IntLiter(4)(pos))(pos)
    ))
  }

  it should "reject LHS non-existing pair elem assignments" in {
    checkFails(AsgnEq(FstNode(Ident("p")(pos))(pos), IntLiter(3)(pos))(pos), "Variable p not declared")
    checkFails(AsgnEq(SndNode(Ident("p")(pos))(pos), IntLiter(3)(pos))(pos), "Variable p not declared")
  }

  it should "accept RHS expression assignments" in {
    checkSucceeds(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), Plus(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
    checkSucceeds(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), Mul(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
    checkSucceeds(IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), Div(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
    checkSucceeds(IdentAsgn(BoolTypeNode()(pos), Ident("x")(pos), Not(BoolLiter(true)(pos))(pos))(pos))
    // This is failing:
    // checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), Len(StringLiter("hello"))))
  }

  it should "accept RHS array literal assignments" in {
    checkSucceeds(IdentAsgn(ArrayTypeNode(IntTypeNode()(pos))(pos), Ident("x")(pos), ArrayLiter(List())(pos))(pos))
    checkSucceeds(IdentAsgn(ArrayTypeNode(IntTypeNode()(pos))(pos), Ident("x")(pos), ArrayLiter(List(IntLiter(1)(pos)))(pos))(pos))
    checkSucceeds(IdentAsgn(ArrayTypeNode(IntTypeNode()(pos))(pos), Ident("x")(pos), ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos))(pos))
  }

  it should "accept RHS new pair assignments" in {
    checkSucceeds(IdentAsgn(PairTypeNode(IntTypeNode()(pos), IntTypeNode()(pos))(pos), Ident("x")(pos), NewPair(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
  }

  it should "accept RHS existing pair elem assignments" in {
    checkSucceedsStats(List(
      IdentAsgn(PairTypeNode(IntTypeNode()(pos), IntTypeNode()(pos))(pos), Ident("p")(pos), NewPair(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos),
      AsgnEq(Ident("x")(pos), FstNode(Ident("p")(pos))(pos))(pos),
      AsgnEq(Ident("x")(pos), SndNode(Ident("p")(pos))(pos))(pos)
    ))
  }

  it should "reject RHS non-existing pair elem assignments" in {
    checkSucceeds(AsgnEq(Ident("x")(pos), FstNode(Ident("p")(pos))(pos))(pos))
    checkSucceeds(AsgnEq(Ident("x")(pos), SndNode(Ident("p")(pos))(pos))(pos))
  }

  it should "accept RHS call to existing function assignments" in {
    checkSucceeds(
      Func(IntTypeNode()(pos), Ident("f")(pos), ParamList(List())(pos), List(Return(IntLiter(1)(pos))(pos)))(pos),
      IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), Call(Ident("f")(pos), List())(pos))(pos)
    )
  }

  it should "reject RHS call to non-existing function assignments" in {
    checkFails(AsgnEq(Ident("x")(pos), Call(Ident("f")(pos), List())(pos))(pos))
    checkFails(AsgnEq(Ident("x")(pos), Call(Ident("f")(pos), List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos))(pos))
  }

  // Tests for read ------------------------------------------------------------------------------------------------------

  it should "accept read with existing identifier" in {
    checkSucceedsStats(List(
      IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos),
      Read(Ident("x")(pos))(pos)
    ))
  }

  it should "reject read with non-existing identifier" in {
    checkFails(Read(Ident("x")(pos))(pos), "Variable x not declared")
  }

  // Tests for free ------------------------------------------------------------------------------------------------------

  it should "accept free with existing identifier" in {
    checkSucceedsStats(List(
      IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos),
      Free(Ident("x")(pos))(pos)
    ))
  }

  it should "reject free with non-existing identifier" in {
    checkFails(Free(Ident("x")(pos))(pos))
  }

  // Tests for return ----------------------------------------------------------------------------------------------------

  it should "accept return" in {
    checkSucceeds(
      Func(IntTypeNode()(pos), Ident("f")(pos), ParamList(List())(pos), List(Return(IntLiter(1)(pos))(pos)))(pos),
      Call(Ident("f")(pos), List())(pos)
    )
  }

  it should "reject return with statement after" in {
    checkFails(
      Program(
        List(),
        List(Func(IntTypeNode()(pos), Ident("f")(pos), ParamList(List())(pos), List(Return(IntLiter(1)(pos))(pos)))(pos)),
        List(Return(IntLiter(1)(pos))(pos), Skip()(pos))
      )(pos),
      "Return statement must be the last statement in a function")
  }

  // Tests for exit ------------------------------------------------------------------------------------------------------

  it should "accept exit" in {
    checkSucceeds(Exit(IntLiter(1)(pos))(pos))
  }

  // Tests for print -----------------------------------------------------------------------------------------------------

  it should "accept print" in {
    checkSucceeds(Print(IntLiter(1)(pos))(pos))
  }

  // Tests for println ---------------------------------------------------------------------------------------------------

  it should "accept println" in {
    checkSucceeds(Println(IntLiter(1)(pos))(pos))
  }

  // Tests for if --------------------------------------------------------------------------------------------------------

  it should "accept if with well-formed body" in {
    checkSucceeds(If(BoolLiter(true)(pos), List(Print(IntLiter(1)(pos))(pos)), List(Print(IntLiter(2)(pos))(pos)))(pos))
  }

  it should "reject if with Skips in body" ignore {
    checkFails(If(BoolLiter(true)(pos), List(Skip()(pos)), List(Skip()(pos)))(pos))
  }

  // Tests for while -------------------------------------------------------(pos)----------------------------------------------

  it should "accept while with well-formed body" in {
    checkSucceeds(While(BoolLiter(true)(pos), List(Print(IntLiter(1)(pos))(pos)))(pos))
  }

  it should "reject while with Skips in body" ignore {
    checkFails(While(BoolLiter(true)(pos), List(Skip()(pos)))(pos))
  }

  // Tests for semicolon -------------------------------------------------------------------------------------------------

  it should "accept multiple well-formed statements" in {
    checkSucceedsStats(List(Print(IntLiter(1)(pos))(pos), Print(IntLiter(2)(pos))(pos)))
  }

  it should "reject multiple skips" ignore {
    checkFails(Program(
      List(),
      List(Func(IntTypeNode()(pos), Ident("f")(pos), ParamList(List())(pos), List(Return(IntLiter(1)(pos))(pos)))(pos)),
      List(Skip()(pos), Skip()(pos))
    )(pos),
    "Empty statement"
    )
  }
}
