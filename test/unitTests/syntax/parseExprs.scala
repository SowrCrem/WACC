package unitTests.syntax
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Program,
  IntLiter,
  IdentAsgn,
  IntTypeNode,
  Neg,
  BoolLiter,
  BoolTypeNode,
  CharLiter,
  CharTypeNode,
  StringLiter,
  ArrayLiter,
  StringTypeNode,
  Ident,
  Brackets,
  Null,
  Error,
  Node,
  Expr,
  Exit,
  Not,
  Len,
  Ord,
  Chr,
  Mul,
  Div,
  Mod,
  Plus,
  Minus,
  GreaterThan,
  GreaterThanEq,
  LessThan,
  LessThanEq,
  Equals,
  NotEquals,
  And,
  Or
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.TypeNode
import wacc.ArrayElem

class parseExprs extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------

  def parseSucceeds[T](input: String, expected: Expr): Assertion = {
    parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), Exit(expected)))
  }

  def parseFails(input: String, errorMessage: String = ""): Assertion = errorMessage match {
    case "" => parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
    case _ => parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(msg) if msg == errorMessage => // Match on a Failure with the specific error message
    }
  }

  // Tests for Unary Operator ------------------------------------------------------------------------------------------

  it should "parse negations" in {
    parseSucceeds("!true" , Not(BoolLiter(true)))
    parseSucceeds("!false", Not(BoolLiter(false)))
  }

  it should "parse negations with brackets" in {
    parseSucceeds("!(true)" , Not(Brackets(BoolLiter(true))))
    parseSucceeds("!(false)", Not(Brackets(BoolLiter(false))))
  }

  it should "parse dashes" in {
    parseSucceeds("-1", Neg(IntLiter(1)))
  }

  it should "parse len" in {
    parseSucceeds("len \"hello\"", Len(StringLiter("hello")))
    parseSucceeds("len [1,2,3]", Len(ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
  }

  it should "parse ord" in {
    parseSucceeds("ord 'a'", Ord(CharLiter('a')))
  }

  it should "parse chr" in {
    parseSucceeds("chr 97", Chr(IntLiter(97)))
  }

  // Tests for Binary Operator -----------------------------------------------------------------------------------------

  it should "parse multiplication" in {
    parseSucceeds("1 * 2", Mul(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 * 2 * 3", Mul(Mul(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse division" in {
    parseSucceeds("1 / 2", Div(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 / 2 / 3", Div(Div(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse modulo" in {
    parseSucceeds("1 % 2", Mod(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 % 2 % 3", Mod(Mod(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse addition" in {
    parseSucceeds("1 + 2", Plus(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 + 2 + 3", Plus(Plus(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse subtraction" in {
    parseSucceeds("1 - 2", Minus(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 - 2 - 3", Minus(Minus(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse greater than" in {
    parseSucceeds("1 > 2", GreaterThan(IntLiter(1), IntLiter(2)))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse greater than or equal" in {
    parseSucceeds("1 >= 2", GreaterThanEq(IntLiter(1), IntLiter(2)))
  }



  //TODO: operation is not associative, so it should fail
  it should "parse less than" in {
    parseSucceeds("1 < 2", LessThan(IntLiter(1), IntLiter(2)))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse less than or equal" in {
    parseSucceeds("1 <= 2", LessThanEq(IntLiter(1), IntLiter(2)))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse equals" in {
    parseSucceeds("1 == 2", Equals(IntLiter(1), IntLiter(2)))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse not equals" in {
    parseSucceeds("1 != 2", NotEquals(IntLiter(1), IntLiter(2)))
  }

  //TODO: operation is not associative, so it should fail
  it should "parse and" in {
    parseSucceeds("true && false", And(BoolLiter(true), BoolLiter(false)))
  }

  it should "parse or" in {
    parseSucceeds("true || false", Or(BoolLiter(true), BoolLiter(false)))
  }

  // Tests for ArrayElem ----------------------------------------------------------------------------------------
  
  it should "parse array elements" in {
    parseSucceeds("arr[0]", ArrayElem(Ident("arr"), List(IntLiter(0))))
  }

  it should "parse 2d array elements" in {
    parseSucceeds("arr[0][1]", ArrayElem(Ident("arr"), List(IntLiter(0), IntLiter(1))))
  }

}