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
  NewPair,
  IntTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  PairTypeNode,
  PairElemTypeNode,
  ArrayTypeNode,
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
import org.scalactic.Fail

class parseStatements extends AnyFlatSpec {

  // Testing Functions ---------------------------------------------------------------------------------------------------

  def parseSucceeds[T](statement: String, expected: Stat): Assertion = {
    parser.parse("begin " + statement + " end") shouldBe Success(Program(List(), expected))
  }

  def parseFails(input: String, errorMessage: String = ""): Assertion = errorMessage match {
    case "" => parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
    // Match on a Failure with the specific error message
    case _ => parser.parse("begin exit " + input + " end") match {
      case Failure (msg) => msg shouldBe errorMessage
      case _ => fail("Wrong Error Message")
    }
  }

  // Tests for skip ------------------------------------------------------------------------------------------------------

  "The parser" should "parse skip" in {
    parseSucceeds("skip", Skip())
  }
  
  // Tests for assignment ------------------------------------------------------------------------------------------------

  it should "parse LHS new identifier assignments" in {
    parseSucceeds("int x = 1", IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1)))
  }

  it should "parse LHS existing identifier assignments" in{
    asgnEqParser.parse("x = 3") shouldBe Success(AsgnEq(Ident("x"), IntLiter(3)))
    asgnEqParser.parse("x[2] = 2") shouldBe Success(AsgnEq(ArrayElem(Ident("x"), List(IntLiter(2))), IntLiter(2)))
  }

  it should "parse LHS array elem assignments" in {
    parseSucceeds("x[1] = 3", AsgnEq(ArrayElem(Ident("x"), List(IntLiter(1))), IntLiter(3)))
  }

  it should "parse LHS pair elem assignments" in {
    parseSucceeds("fst p = 3", AsgnEq(FstNode(Ident("p")), IntLiter(3)))
    parseSucceeds("snd p = 3", AsgnEq(SndNode(Ident("p")), IntLiter(3)))
  }

  it should "parse RHS expression assignments" in {
    parseSucceeds("x = 1 + 2", AsgnEq(Ident("x"), Plus(IntLiter(1), IntLiter(2))))
    parseSucceeds("x = 1 * 2", AsgnEq(Ident("x"), Mul(IntLiter(1), IntLiter(2))))
    parseSucceeds("x = 1 / 2", AsgnEq(Ident("x"), Div(IntLiter(1), IntLiter(2))))
    parseSucceeds("x = !true", AsgnEq(Ident("x"), Not(BoolLiter(true))))
    parseSucceeds("x = len \"hello\"", AsgnEq(Ident("x"), Len(StringLiter("hello"))))
  }

  it should "parse RHS array literal assignments" in {
    parseSucceeds("x = []", AsgnEq(Ident("x"), ArrayLiter(List())))
    parseSucceeds("x = [1]", AsgnEq(Ident("x"), ArrayLiter(List(IntLiter(1)))))
    parseSucceeds("x = [1, 2, 3]", AsgnEq(Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
  }

  it should "parse RHS new pair assignments" in {
    parseSucceeds("x = newpair(1, 2)", AsgnEq(Ident("x"), NewPair(IntLiter(1), IntLiter(2))))
  }

  it should "parse RHS pair elem assignments" in {
    parseSucceeds("x = fst p", AsgnEq(Ident("x"), FstNode(Ident("p"))))
    parseSucceeds("x = snd p", AsgnEq(Ident("x"), SndNode(Ident("p"))))
  }

  it should "parse RHS call assignments" in {
    parseSucceeds("x = call f()", AsgnEq(Ident("x"), Call(Ident("f"), ArgList(List()))))
    parseSucceeds("x = call f(1, 2, 3)", AsgnEq(Ident("x"), Call(Ident("f"), ArgList( List(IntLiter(1), IntLiter(2), IntLiter(3)) ) )))
  }

  // Tests for read ------------------------------------------------------------------------------------------------------

  it should "parse read with identifier" in {
    parseSucceeds("read x", Read(Ident("x")))
  }

  it should "parse read with array elem" in {
    parseSucceeds("read x[1]", Read(ArrayElem(Ident("x"), List(IntLiter(1)))))
  }

  it should "parse read with pair elem" in {
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

  it should "parse multiple statements" in {
    parseSucceeds("skip; skip", StatJoin(List(Skip(), Skip())))
  }

  it should "reject trailing semicolon" in {
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

  it should "reject statement closing parenthesis" in {
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 13):\n")
    errorBuilder.append("  unexpected closing parenthesis\n")
    errorBuilder.append("  expected end\n")
    errorBuilder.append("  >begin skip) end\n")
    errorBuilder.append("             ^")
    parseFails("return 5)", errorBuilder.toString())
  }
  
  it should "reject statement parenthesisation" in {
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 13):\n")
    errorBuilder.append("  unexpected opening parenthesis\n")
    errorBuilder.append("  expected \"\"\", \"\'\", \"(\", \"+\", \"-\", arithmetic operator, boolean, digit, expected start of array, fst, identifier, logical operator, newpair, null, snd, or unary operator\n")
    errorBuilder.append("  >begin (return 5) end\n")
    errorBuilder.append("         ^")
    parseFails("(return 5)", errorBuilder.toString())
  }
}
