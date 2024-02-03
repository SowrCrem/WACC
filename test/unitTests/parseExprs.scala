import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import wacc.{
  Program,
  IntLiter,
  IdentAsgn,
  IntType,
  Neg,
  BoolLiter,
  BoolType,
  CharLiter,
  CharType,
  StringLiter,
  ArrayLiter,
  StringType,
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
import wacc.Type

class parseExprs extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------

  def parseSucceeds[T](input: String, expected: Expr): Assertion = {
    parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), Exit(expected)))
  }

  def parseFails(input: String): Assertion = {
    parser.parse("begin exit " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
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

  it should "parse len" ignore {
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

  it should "parse greater than" ignore {
    parseSucceeds("1 > 2", GreaterThan(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 > 2 > 3", GreaterThan(GreaterThan(IntLiter(1), IntLiter(2)), IntLiter(3))) 
  }

  it should "parse greater than or equal" ignore {
    parseSucceeds("1 >= 2", GreaterThanEq(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 >= 2 >= 3", GreaterThanEq(GreaterThanEq(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse less than" ignore {
    parseSucceeds("1 < 2", LessThan(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 < 2 < 3", LessThan(LessThan(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse less than or equal" ignore {
    parseSucceeds("1 <= 2", LessThanEq(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 <= 2 <= 3", LessThanEq(LessThanEq(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse equals" ignore {
    parseSucceeds("1 == 2", Equals(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 == 2 == 3", Equals(Equals(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse not equals" ignore {
    parseSucceeds("1 != 2", NotEquals(IntLiter(1), IntLiter(2)))
    parseSucceeds("1 != 2 != 3", NotEquals(NotEquals(IntLiter(1), IntLiter(2)), IntLiter(3)))
  }

  it should "parse and" ignore {
    parseSucceeds("true && false", And(BoolLiter(true), BoolLiter(false)))
    parseSucceeds("true && false && true", And(And(BoolLiter(true), BoolLiter(false)), BoolLiter(true)))
  }

  it should "parse or" ignore {
    parseSucceeds("true || false", Or(BoolLiter(true), BoolLiter(false)))
    parseSucceeds("true || false || true", Or(Or(BoolLiter(true), BoolLiter(false)), BoolLiter(true)))
  }

  // Tests for ArrayElem ----------------------------------------------------------------------------------------
  
  it should "parse array elements" ignore {
    parseSucceeds("arr[0]", ArrayLiter(List(IntLiter(0))))
  }

  it should "parse 2d array elements" ignore {
    parseSucceeds("arr[0][1]", ArrayLiter(List(IntLiter(0), IntLiter(1))))
  }

}