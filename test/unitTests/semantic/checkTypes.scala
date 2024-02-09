import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Node,
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
  ArrayLiter
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.SymbolTable
import org.scalatest.BeforeAndAfterEach
import wacc.TypeChecker

class checkTypes extends AnyFlatSpec with BeforeAndAfterEach {

  var symbolTable: SymbolTable = _
  var typeChecker: TypeChecker = _

  // Testing Functions -------------------------------------------------------------------------------------------------

  override def beforeEach(): Unit = {
    symbolTable = new SymbolTable(None)
    typeChecker = new TypeChecker(symbolTable)
  }

  def checkSucceeds(node: Node): Assertion = noException should be thrownBy typeChecker.check(node)

  def checkFails(node: Node, errorMessage: String): Assertion = {
    val e = intercept[SemanticError] {
      typeChecker.check(node)
    }
    e.getMessage shouldBe errorMessage
  }
  
  // Tests for base-type --------------------------------------------------------------------------------------------------

  "The type checker" should "accept integer literals" in {
    checkSucceeds(IntLiter(1))
  }

  it should "reject non-integer assignments to an integer variable" in {
    checkFails(IdentAsgn(IntTypeNode(), Ident("x"), CharLiter('a')), "Type mismatch: expected integer, got Some(char)")
  }

  it should "accept character literals" in {
    checkSucceeds(CharLiter('a'))
  }

  it should "reject non-character assignments to a character variable" in {
    checkFails(IdentAsgn(CharTypeNode(), Ident("x"), IntLiter(1)), "Type mismatch: expected char, got Some(integer)")
  }

  it should "accept string literals" in {
    checkSucceeds(StringLiter("hello"))
  }

  it should "reject non-string assignments to a string variable" in {
    checkFails(IdentAsgn(StringTypeNode(), Ident("x"), CharLiter('a')), "Type mismatch: expected string, got Some(char)")
  }

  it should "accept boolean literals" in {
    checkSucceeds(BoolLiter(true))
    checkSucceeds(BoolLiter(false))
  }

  it should "reject non-boolean assignments to a boolean variable" in {
    checkFails(IdentAsgn(BoolTypeNode(), Ident("x"), IntLiter(1)), "Type mismatch: expected bool, got Some(integer)")
  }

  it should "accept brackets" in {
    checkSucceeds(Brackets(IntLiter(1)))
  }

  // Tests for array-type -------------------------------------------------------------------------------------------------

  it should "accept int array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(IntTypeNode()),
        Ident("x"),
        ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))
      )
    )
  }

  it should "reject non-int assignments to an int array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(IntTypeNode()),
        Ident("x"),
        ArrayLiter(List(CharLiter('a'), CharLiter('b'), CharLiter('c')))
      ),
      "Type mismatch: expected array of integer, got Some(array of char)"
    )
  }

  it should "accept bool array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(BoolTypeNode()),
        Ident("x"),
        ArrayLiter(List(BoolLiter(true), BoolLiter(false)))
      )
    )
  }

  it should "reject non-bool assignments to a bool array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(BoolTypeNode()),
        Ident("x"),
        ArrayLiter(List(CharLiter('a'), CharLiter('b'), CharLiter('c')))
      ),
      "Type mismatch: expected array of bool, got Some(array of char)"
    )
  }

  it should "accept char array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(CharTypeNode()),
        Ident("x"),
        ArrayLiter(List(CharLiter('a'), CharLiter('b'), CharLiter('c')))
      )
    )
  }

  it should "reject non-char assignments to a char array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(CharTypeNode()),
        Ident("x"),
        ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))
      ),
      "Type mismatch: expected array of char, got Some(array of integer)"
    )
  }

  it should "accept string array types" in {
    checkSucceeds(
      IdentAsgn(
        ArrayTypeNode(StringTypeNode()),
        Ident("x"),
        ArrayLiter(List(StringLiter("hello"), StringLiter("world")))
      )
    )
  }

  it should "reject non-string assignments to a string array variable" in {
    checkFails(
      IdentAsgn(
        ArrayTypeNode(StringTypeNode()),
        Ident("x"),
        ArrayLiter(List(CharLiter('a'), CharLiter('b'), CharLiter('c')))
      ),
      "Type mismatch: expected array of string, got Some(array of char)"
    )
  }

  // Tests for pair-type --------------------------------------------------------------------------------------------------

  it should "accept pair types" in {
    checkSucceeds(
      IdentAsgn(
        PairTypeNode(IntTypeNode(), BoolTypeNode()),
        Ident("x"),
        NewPair(IntLiter(1), BoolLiter(true))
      )
    )
  }

  // Tests for Programs -------------------------------------------------------------------------------------------------

  it should "accept fully-formed programs" in {
    val node = Program(
      List(
        Func(
          IntTypeNode(),
          Ident("f"),
          ParamList(List(Param(IntTypeNode(), Ident("x")))),
          Return(IntLiter(1))
        ),
        Func(
          IntTypeNode(),
          Ident("g"),
          ParamList(List(Param(IntTypeNode(), Ident("x")))),
          Return(IntLiter(2))
        )
      ),
      IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1))
    )
    checkSucceeds(node)
  }

}
