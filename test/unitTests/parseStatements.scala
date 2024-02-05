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
  Program,
  Expr,
  Stat,
  Skip,
  Read,
  Free,
  Return,
  Exit,
  Print,
  Println,
  If,
  While,
  BeginEnd,
  StatJoin,
  IdentAsgn,
  AsgnEq,
  LValue,
  RValue,
  Call,
  IntType,
  NewPair,
  PairType,
  PairElemType,
  ArrayType,
  BoolType,
  CharType,
  StringType,
  Func,
  ParamList,
  FstNode,
  SndNode,
  ArrayLiter,
  ArgList
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion

class parseStatements extends AnyFlatSpec {

  // Testing Functions ---------------------------------------------------------------------------------------------------

  def parseSucceeds[T](statement: String, expected: Stat): Assertion = {
    parser.parse("begin " + statement + " end") shouldBe Success(Program(List(), expected))
  }

  def parseFails(statement: String): Assertion = {
    parser.parse("begin " + statement + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
  }

  // Tests for skip ------------------------------------------------------------------------------------------------------

  "The parser" should "parse skip" in {
    parseSucceeds("skip", Skip())
  }
  
  // Tests for assignment ------------------------------------------------------------------------------------------------

  it should "parse LHS new identifier assignments" in {
    parseSucceeds("int x = 1", IdentAsgn(IntType(), Ident("x"), IntLiter(1)))
  }

  it should "parse LHS existing identifier assignments" ignore {
    parseSucceeds("int x = 1; x = 3", AsgnEq(Ident("x"), IntLiter(3)))
  }

  it should "parse LHS array elem assignments" in {
    parseSucceeds("x[1] = 3", AsgnEq(ArrayElem(Ident("x"), List(IntLiter(1))), IntLiter(3)))
  }

  it should "parse LHS pair elem assignments" ignore {
    parseSucceeds("fst p = 3", AsgnEq(FstNode(Ident("p")), IntLiter(3)))
    parseSucceeds("snd p = 3", AsgnEq(SndNode(Ident("p")), IntLiter(3)))
  }

  it should "parse RHS expression assignments" ignore {
    parseSucceeds("x = 1 + 2", AsgnEq(Ident("x"), Plus(IntLiter(1), IntLiter(2))))
    parseSucceeds("x = 1 * 2", AsgnEq(Ident("x"), Mul(IntLiter(1), IntLiter(2))))
    parseSucceeds("x = 1 / 2", AsgnEq(Ident("x"), Div(IntLiter(1), IntLiter(2))))
    parseSucceeds("x = !true", AsgnEq(Ident("x"), Not(BoolLiter(true))))
    parseSucceeds("x = len \"hello\"", AsgnEq(Ident("x"), Len(StringLiter("hello"))))
  }

  it should "parse RHS array literal assignments" ignore {
    parseSucceeds("x = []", AsgnEq(Ident("x"), ArrayLiter(List())))
    parseSucceeds("x = [1]", AsgnEq(Ident("x"), ArrayLiter(List(IntLiter(1)))))
    parseSucceeds("x = [1, 2, 3]", AsgnEq(Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
  }

  it should "parse RHS new pair assignments" ignore {
    parseSucceeds("x = newpair(1, 2)", AsgnEq(Ident("x"), NewPair(IntLiter(1), IntLiter(2))))
  }

  it should "parse RHS pair elem assignments" ignore {
    parseSucceeds("x = fst p", AsgnEq(Ident("x"), FstNode(Ident("p"))))
    parseSucceeds("x = snd p", AsgnEq(Ident("x"), SndNode(Ident("p"))))
  }

  it should "parse RHS call assignments" ignore {
    parseSucceeds("x = call f()", AsgnEq(Ident("x"), Call(Ident("f"), ArgList(List()))))
    parseSucceeds("x = call f(1, 2, 3)", AsgnEq(Ident("x"), Call(Ident("f"), ArgList( List(IntLiter(1), IntLiter(2), IntLiter(3)) ) )))
  }

  // Tests for read ------------------------------------------------------------------------------------------------------

  it should "parse read with identifier" in {
    parseSucceeds("read x", Read(Ident("x")))
  }

  it should "parse read with array elem" ignore {
    parseSucceeds("read x[1]", Read(ArrayElem(Ident("x"), List(IntLiter(1)))))
  }

  it should "parse read with pair elem" ignore {
    parseSucceeds("read fst p", Read(FstNode(Ident("p"))))
    parseSucceeds("read snd p", Read(SndNode(Ident("p"))))
  }

  // Tests for free ------------------------------------------------------------------------------------------------------

  it should "parse free" in {
    parseSucceeds("free x", Free(Ident("x")))
  }

  // Tests for return ----------------------------------------------------------------------------------------------------

  it should "parse return" in {
    parseSucceeds("return 1", Return(IntLiter(1)))
  }

  // Tests for exit ------------------------------------------------------------------------------------------------------

  it should "parse exit" in {
    parseSucceeds("exit 1", Exit(IntLiter(1)))
  }

  // Tests for print -----------------------------------------------------------------------------------------------------

  it should "parse print" in {
    parseSucceeds("print 1", Print(IntLiter(1)))
  }

  // Tests for println ---------------------------------------------------------------------------------------------------

  it should "parse println" in {
    parseSucceeds("println 1", Println(IntLiter(1)))
  }

  // Tests for if --------------------------------------------------------------------------------------------------------

  it should "parse if" in {
    parseSucceeds("if true then skip else skip fi", If(BoolLiter(true), Skip(), Skip()))
  }

  // Tests for while -----------------------------------------------------------------------------------------------------

  it should "parse while" in {
    parseSucceeds("while true do skip done", While(BoolLiter(true), Skip()))
  }

  // Tests for semicolon -------------------------------------------------------------------------------------------------

  it should "parse multiple statements" ignore {
    parseSucceeds("skip; skip", StatJoin(List(Skip(), Skip())))
  }
}
