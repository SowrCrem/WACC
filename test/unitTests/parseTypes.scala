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
import org.scalatest.compatible.Assertion

class parseTypes extends AnyFlatSpec {

  // Testing Functions ---------------------------------------------------------------------------------------------------

  def parseSucceeds[T](statement: String, expected: Stat): Assertion = {
    parser.parse("begin " + statement + " end") shouldBe Success(Program(List(), expected))
  }

  def parseFails(statement: String): Assertion = {
    parser.parse("begin " + statement + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
  }

  // Tests for base-type --------------------------------------------------------------------------------------------------

  "The parser" should "parse an int type" in {
    parseSucceeds("int x = 5", IdentAsgn(IntType(), Ident("x"), IntLiter(5)))
  }

  it should "parse a bool type" in {
    parseSucceeds("bool x = true", IdentAsgn(BoolType(), Ident("x"), BoolLiter(true)))
  }

  it should "parse a char type" in {
    parseSucceeds("char x = 'a'", IdentAsgn(CharType(), Ident("x"), CharLiter('a')))
  }

  it should "parse a string type" in {
    parseSucceeds("string x = \"hello\"", IdentAsgn(StringType(), Ident("x"), StringLiter("hello")))
  }

  // Tests for array-type -------------------------------------------------------------------------------------------------

  it should "parse an int array type" ignore {
    parseSucceeds("int[] x = [1, 2, 3]", IdentAsgn(ArrayType(IntType()), Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
  }

  it should "parse a bool array type" ignore {
    parseSucceeds("bool[] x = [true, false]", IdentAsgn(ArrayType(BoolType()), Ident("x"), ArrayLiter(List(BoolLiter(true), BoolLiter(false)))))
  }

  it should "parse a char array type" ignore {
    parseSucceeds("char[] x = ['a', 'b', 'c']", IdentAsgn(ArrayType(CharType()), Ident("x"), ArrayLiter(List(CharLiter('a'), CharLiter('b'), CharLiter('c')))))
  }

  it should "parse a string array type" ignore {
    parseSucceeds("string[] x = [\"hello\", \"world\"]", IdentAsgn(ArrayType(StringType()), Ident("x"), ArrayLiter(List(StringLiter("hello"), StringLiter("world")))))
  }

  // Tests for pair-type --------------------------------------------------------------------------------------------------

  it should "parse pair types" ignore {
    parseSucceeds("pair(int, bool) x = newpair(1, true)", IdentAsgn(PairType(IntType(), BoolType()), Ident("x"), NewPair(IntLiter(1), BoolLiter(true))))
  }

  it should "parse pair types with array types" ignore {
    parseSucceeds("pair(int[], bool[]) x = newpair([1, 2, 3], [true, false])", IdentAsgn(PairType(ArrayType(IntType()), ArrayType(BoolType())), Ident("x"), NewPair(ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3))), ArrayLiter(List(BoolLiter(true), BoolLiter(false))))))
  }

  it should "reject pair types with pair types" in {
    parseFails("pair(pair(int, bool), pair(bool, char)) x = newpair(newpair(1, true), newpair(true, 'a'))")
  }

  it should "parse pair types with pair array types" ignore {
    parseSucceeds("pair(int, pair(bool, char)[]) x = newpair(1, newpair(true, 'a')[])", IdentAsgn(PairType(IntType(), ArrayType(PairType(BoolType(), CharType()))), Ident("x"), NewPair(IntLiter(1), ArrayLiter(List(NewPair(BoolLiter(true), CharLiter('a')))))))
  }
}
