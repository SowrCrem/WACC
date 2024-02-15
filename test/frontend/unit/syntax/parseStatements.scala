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

class parseStatements extends AnyFlatSpec {

  // Tests for skip ------------------------------------------------------------------------------------------------------

  "The parser" should "parse skip" in {
    parseSucceeds("skip", Skip()(pos))
  }
  
  // Tests for assignment ------------------------------------------------------------------------------------------------

  it should "parse LHS new identifier assignments" in {
    parseSucceeds("int x = 1", IdentAsgn(IntTypeNode()(pos), Ident("x")(pos), IntLiter(1)(pos))(pos))
  }

  it should "parse LHS existing identifier assignments" in{
    asgnEqParser.parse("x = 3") shouldBe Success(AsgnEq(Ident("x")(pos), IntLiter(3)(pos))(pos))
    asgnEqParser.parse("x[2] = 2") shouldBe Success(AsgnEq(ArrayElem(Ident("x")(pos), List(IntLiter(2)(pos)))(pos), IntLiter(2)(pos))(pos))
  }

  it should "parse LHS array elem assignments" in {
    parseSucceeds("x[1] = 3", AsgnEq(ArrayElem(Ident("x")(pos), List(IntLiter(1)(pos)))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse LHS pair elem assignments" in {
    parseSucceeds("fst p = 3", AsgnEq(FstNode(Ident("p")(pos))(pos), IntLiter(3)(pos))(pos))
    parseSucceeds("snd p = 3", AsgnEq(SndNode(Ident("p")(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse RHS expression assignments" in {
    parseSucceeds("x = 1 + 2", AsgnEq(Ident("x")(pos), Plus(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
    parseSucceeds("x = 1 * 2", AsgnEq(Ident("x")(pos), Mul (IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
    parseSucceeds("x = 1 / 2", AsgnEq(Ident("x")(pos), Div (IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
    parseSucceeds("x = !true", AsgnEq(Ident("x")(pos), Not (BoolLiter(true)(pos))(pos))(pos))
    parseSucceeds("x = len \"hello\"", AsgnEq(Ident("x")(pos), Len(StringLiter("hello")(pos))(pos))(pos))
  }

  it should "parse RHS array literal assignments" in {
    parseSucceeds("x = []",  AsgnEq(Ident("x")(pos), ArrayLiter(List())(pos))(pos))
    parseSucceeds("x = [1]", AsgnEq(Ident("x")(pos), ArrayLiter(List(IntLiter(1)(pos)))(pos))(pos))
    parseSucceeds("x = [1, 2, 3]", AsgnEq(Ident("x")(pos), ArrayLiter(List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos))(pos))
  }

  it should "parse RHS new pair assignments" in {
    parseSucceeds("x = newpair(1, 2)", AsgnEq(Ident("x")(pos), NewPair(IntLiter(1)(pos), IntLiter(2)(pos))(pos))(pos))
  }

  it should "parse RHS pair elem assignments" in {
    parseSucceeds("x = fst p", AsgnEq(Ident("x")(pos), FstNode(Ident("p")(pos))(pos))(pos))
    parseSucceeds("x = snd p", AsgnEq(Ident("x")(pos), SndNode(Ident("p")(pos))(pos))(pos))
  }

  it should "parse RHS call assignments" in {
    parseSucceeds("x = call f()", AsgnEq(Ident("x")(pos), Call(Ident("f")(pos), List())(pos))(pos))
    parseSucceeds("x = call f(1, 2, 3)", AsgnEq(Ident("x")(pos), Call(Ident("f")(pos), List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)) )(pos) )(pos))
  }

  // Tests for read ------------------------------------------------------------------------------------------------------

  it should "parse read with identifier" in {
    parseSucceeds("read x", Read(Ident("x")(pos))(pos))
  }

  it should "parse read with array elem" in {
    parseSucceeds("read x[1]", Read(ArrayElem(Ident("x")(pos), List(IntLiter(1)(pos)))(pos))(pos))
  }

  it should "parse read with pair elem" in {
    // parseSucceeds("read fst p", Read(FstNode(Ident("p"))))
    parseSucceeds("read fst p", Read(FstNode(Ident("p")(pos))(pos))(pos))
    parseSucceeds("read snd p", Read(SndNode(Ident("p")(pos))(pos))(pos))
  }

  // Tests for free ------------------------------------------------------------------------------------------------------

  it should "parse free" in {
    parseSucceeds("free x", Free(Ident("x")(pos))(pos))
  }

  // Tests for return ----------------------------------------------------------------------------------------------------

  it should "parse return" in {
    parseSucceeds("return 1", Return(IntLiter(1)(pos))(pos))
  }

  // Tests for exit ------------------------------------------------------------------------------------------------------

  it should "parse exit" in {
    parseSucceeds("exit 1", Exit(IntLiter(1)(pos))(pos))
  }

  // Tests for print -----------------------------------------------------------------------------------------------------

  it should "parse print" in {
    parseSucceeds("print 1", Print(IntLiter(1)(pos))(pos))
  }

  // Tests for println ---------------------------------------------------------------------------------------------------

  it should "parse println" in {
    parseSucceeds("println 1", Println(IntLiter(1)(pos))(pos))
  }

  // Tests for if --------------------------------------------------------------------------------------------------------

  it should "parse if" in {
    // parseSucceeds("if true then skip else skip fi", If(BoolLiter(true), Skip(), Skip()))
    parseSucceeds("if true then skip else skip fi", If(BoolLiter(true)(pos), List(Skip()(pos)), List(Skip()(pos)))(pos))
  }

  // Tests for while -----------------------------------------------------------------------------------------------------

  it should "parse while" in {
    parseSucceeds("while true do skip done", While(BoolLiter(true)(pos), List(Skip()(pos)))(pos))
  }

  // Tests for semicolon -------------------------------------------------------------------------------------------------

  it should "parse multiple statements" in {
    parseSucceeds("skip; skip", List(Skip()(pos), Skip()(pos)))
  }

  it should "reject trailing semicolon" ignore {
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 13):\n")
    errorBuilder.append("  unexpected keyword end\n")
    errorBuilder.append("  expected statement\n")
    errorBuilder.append("  >begin skip; end\n")
    errorBuilder.append("               ^^^")
    parseFails("skip;", errorBuilder.toString())
    parseFails("skip;;", "EROR")
    // Both have the exact same error message
  }

  it should "reject statement closing parenthesis" ignore {
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 13):\n")
    errorBuilder.append("  unexpected closing parenthesis\n")
    errorBuilder.append("  expected end\n")
    errorBuilder.append("  >begin skip) end\n")
    errorBuilder.append("             ^")
    parseFails("return 5)", errorBuilder.toString())
  }
  
  it should "reject statement parenthesisation" in {
    pending
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 13):\n")
    errorBuilder.append("  unexpected opening parenthesis\n")
    errorBuilder.append("  expected \"\"\", \"\'\", \"(\", \"+\", \"-\", arithmetic operator, boolean, digit, expected start of array, fst, identifier, logical operator, newpair, null, snd, or unary operator\n")
    errorBuilder.append("  >begin (return 5) end\n")
    errorBuilder.append("         ^")

    val program = "begin (return 5) end"
    parser.parse(program) shouldBe Failure(errorBuilder.toString())

    // parseFails("(return 5)", errorBuilder.toString())
  }
}
