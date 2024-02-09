import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Position,
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
  Skip,
  Read,
  If,
  While,
  Free,
  Return,
  Exit,
  Print,
  Println,
  StatJoin,
  IdentAsgn,
  IntTypeNode,
  PairTypeNode,
  PairElemTypeNode,
  ArrayTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  Func,
  ParamList,
  Param,
  Program,
  TypeNode,
  Call,
  Expr,
  NewPair,
  ArgList,
  SemanticError,
  ArrayLiter,
  Neg,
  Ord,
  Chr,
  Mod,
  Minus,
  GreaterThan,
  GreaterThanEq,
  LessThan,
  LessThanEq,
  Equals,
  NotEquals,
  And,
  Or,
  Stat,
  AsgnEq,
  FstNode,
  SndNode
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.SymbolTable
import org.scalatest.BeforeAndAfterEach
import wacc.TypeChecker

class checkStatements extends AnyFlatSpec with BeforeAndAfterEach {

  var symbolTable: SymbolTable = _
  var typeChecker: TypeChecker = _

  // Testing Functions -------------------------------------------------------------------------------------------------

  override def beforeEach(): Unit = {
    symbolTable = new SymbolTable(None)
    typeChecker = new TypeChecker(symbolTable)
  }

  def checkSucceeds(position: Position): Assertion = noException should be thrownBy typeChecker.check(position)

  def checkFails(position: Position, errorMessage: String): Assertion = {
    val e = intercept[Exception] {
      typeChecker.check(position)
    }
    e.getMessage shouldBe errorMessage.toString()
  }

  // Tests for skip ------------------------------------------------------------------------------------------------------

  "The parser" should "parse skip" in {
    checkSucceeds(Skip())
  }
  
  // Tests for assignment ------------------------------------------------------------------------------------------------

  it should "parse LHS new identifier assignments" in {
    checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1)))
  }

  it should "parse LHS existing identifier assignments" in{
    checkSucceeds(AsgnEq(Ident("x"), IntLiter(3)))
    checkSucceeds(AsgnEq(ArrayElem(Ident("x"), List(IntLiter(2))), IntLiter(2)))
  }

  it should "parse LHS array elem assignments" in {
    checkSucceeds(AsgnEq(ArrayElem(Ident("x"), List(IntLiter(1))), IntLiter(3)))
  }

  it should "parse LHS pair elem assignments" in {
    checkSucceeds(AsgnEq(FstNode(Ident("p")), IntLiter(3)))
    checkSucceeds(AsgnEq(SndNode(Ident("p")), IntLiter(3)))
  }

  it should "parse RHS expression assignments" in {
    checkSucceeds(AsgnEq(Ident("x"), Plus(IntLiter(1), IntLiter(2))))
    checkSucceeds(AsgnEq(Ident("x"), Mul(IntLiter(1), IntLiter(2))))
    checkSucceeds(AsgnEq(Ident("x"), Div(IntLiter(1), IntLiter(2))))
    checkSucceeds(AsgnEq(Ident("x"), Not(BoolLiter(true))))
    checkSucceeds(AsgnEq(Ident("x"), Len(StringLiter("hello"))))
  }

  it should "parse RHS array literal assignments" in {
    checkSucceeds(AsgnEq(Ident("x"), ArrayLiter(List())))
    checkSucceeds(AsgnEq(Ident("x"), ArrayLiter(List(IntLiter(1)))))
    checkSucceeds(AsgnEq(Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
  }

  it should "parse RHS new pair assignments" in {
    checkSucceeds(AsgnEq(Ident("x"), NewPair(IntLiter(1), IntLiter(2))))
  }

  it should "parse RHS pair elem assignments" in {
    checkSucceeds(AsgnEq(Ident("x"), FstNode(Ident("p"))))
    checkSucceeds(AsgnEq(Ident("x"), SndNode(Ident("p"))))
  }

  it should "parse RHS call assignments" in {
    checkSucceeds(AsgnEq(Ident("x"), Call(Ident("f"), ArgList(List()))))
    checkSucceeds(AsgnEq(Ident("x"), Call(Ident("f"), ArgList( List(IntLiter(1), IntLiter(2), IntLiter(3)) ) )))
  }

  // Tests for read ------------------------------------------------------------------------------------------------------

  it should "parse read with identifier" in {
    checkSucceeds(Read(Ident("x")))
  }

  it should "parse read with array elem" in {
    checkSucceeds(Read(ArrayElem(Ident("x"), List(IntLiter(1)))))
  }

  it should "parse read with pair elem" in {
    checkSucceeds(Read(FstNode(Ident("p"))))
    checkSucceeds(Read(SndNode(Ident("p"))))
  }

  // Tests for free ------------------------------------------------------------------------------------------------------

  it should "parse free" in {
    checkSucceeds(Free(Ident("x")))
  }

  // Tests for return ----------------------------------------------------------------------------------------------------

  it should "parse return" in {
    checkSucceeds(Return(IntLiter(1)))
  }

  // Tests for exit ------------------------------------------------------------------------------------------------------

  it should "parse exit" in {
    checkSucceeds(Exit(IntLiter(1)))
  }

  // Tests for print -----------------------------------------------------------------------------------------------------

  it should "parse print" in {
    checkSucceeds(Print(IntLiter(1)))
  }

  // Tests for println ---------------------------------------------------------------------------------------------------

  it should "parse println" in {
    checkSucceeds(Println(IntLiter(1)))
  }

  // Tests for if --------------------------------------------------------------------------------------------------------

  it should "parse if" in {
    checkSucceeds(If(BoolLiter(true), Skip(), Skip()))
  }

  // Tests for while -----------------------------------------------------------------------------------------------------

  it should "parse while" in {
    checkSucceeds(While(BoolLiter(true), Skip()))
  }

  // Tests for semicolon -------------------------------------------------------------------------------------------------

  it should "parse multiple statements" in {
    checkSucceeds(StatJoin(List(Skip(), Skip())))

  }
}
