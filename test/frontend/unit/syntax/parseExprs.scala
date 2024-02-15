package test.frontend.unit.syntax

import wacc.Main
import test.Utils._
import wacc.lexer._
import wacc.parser._
import wacc.{
  Program,
  Expr,
  Neg,
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
  Or,
  Null,
  Exit,
  Ident,
  TypeNode,
  Position,
  Brackets,
  IdentAsgn,
  IntLiter,
  BoolLiter,
  CharLiter,
  StringLiter,
  ArrayLiter,
  IntTypeNode,
  BoolTypeNode,
  CharTypeNode,
  StringTypeNode,
  ArrayTypeNode,
  ArrayElem,
}
import java.lang.StringBuilder
import parsley.{Failure, Result, Success}
import org.scalactic.{Fail, Bool}
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class parseExprs extends AnyFlatSpec {

  // Tests for Unary Operator ------------------------------------------------------------------------------------------

  it should "parse negations" in {
    parseSucceeds("!true" , Not(BoolLiter(true )(pos))(pos))
    parseSucceeds("!false", Not(BoolLiter(false)(pos))(pos))
  }

  it should "parse negations with brackets" in {
    parseSucceeds("!(true)" , Not(Brackets(BoolLiter(true )(pos))(pos))(pos))
    parseSucceeds("!(false)", Not(Brackets(BoolLiter(false)(pos))(pos))(pos))
  }

  it should "parse negative numbers" in {
    parseSucceeds("-1", IntLiter(-1)(pos))
  }

  it should "parse len" in {
    parseSucceeds("len \"hello\"", Len(StringLiter("hello")(pos))(pos))
    parseSucceeds("len [1,2,3]"  , Len(ArrayLiter (List(IntLiter(1)(pos), IntLiter(2)(pos), IntLiter(3)(pos)))(pos))(pos))
  }

  it should "parse ord" in {
    parseSucceeds("ord 'a'", Ord(CharLiter('a')(pos))(pos))
  }

  it should "parse chr" in {
    parseSucceeds("chr 97", Chr(IntLiter(97)(pos))(pos))
  }

  // Tests for Binary Operator -----------------------------------------------------------------------------------------

  it should "parse multiplication" in {
    parseSucceeds("1 * 2", Mul(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    parseSucceeds("1 * 2 * 3", Mul(Mul(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse division" in {
    parseSucceeds("1 / 2", Div(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    parseSucceeds("1 / 2 / 3", Div(Div(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse modulo" in {
    parseSucceeds("1 % 2", Mod(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    parseSucceeds("1 % 2 % 3", Mod(Mod(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse addition" in {
    parseSucceeds("1 + 2", Plus(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    parseSucceeds("1 + 2 + 3", Plus(Plus(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse subtraction" in {
    parseSucceeds("1 - 2", Minus(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
    parseSucceeds("1 - 2 - 3", Minus(Minus(IntLiter(1)(pos), IntLiter(2)(pos))(pos), IntLiter(3)(pos))(pos))
  }

  it should "parse greater than" in {
    parseSucceeds("1 > 2", GreaterThan(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }


  // Jeet: I feel like parsing wise all of these are completely fine - because they are valid expressions

  //TODO: operation is not associative, so it should fail
  it should "parse greater than or equal" in {
    parseSucceeds("1 >= 2", GreaterThanEq(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }



  //TODO: operation is not associative, so it should fail
  it should "parse less than" in {
    parseSucceeds("1 < 2", LessThan(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse less than or equal" in {
    parseSucceeds("1 <= 2", LessThanEq(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse equals" in {
    parseSucceeds("1 == 2", Equals(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }


  //TODO: operation is not associative, so it should fail
  it should "parse not equals" in {
    parseSucceeds("1 != 2", NotEquals(IntLiter(1)(pos), IntLiter(2)(pos))(pos))
  }

  //TODO: operation is not associative, so it should fail
  it should "parse and" in {
    parseSucceeds("true && false", And(BoolLiter(true)(pos), BoolLiter(false)(pos))(pos))
  }

  it should "parse or" in {
    parseSucceeds("true || false", Or(BoolLiter(true)(pos), BoolLiter(false)(pos))(pos))
  }

  it should "reject non-associative operator chains" in {
    val errorBuilder = new StringBuilder()
    errorBuilder.append("(line 1, column 18):\n")
    errorBuilder.append("  unexpected \"<\"\n")
    errorBuilder.append("  expected arithmetic operator or comparison operator\n")
    errorBuilder.append("  non-associative operators cannot be chained together\n")
    errorBuilder.append("  >begin exit 1 < 2 < 3 end\n")
    errorBuilder.append("                    ^")
    parseFails("1 < 2 < 3", errorBuilder.toString())
  }

  // Tests for ArrayElem ----------------------------------------------------------------------------------------
  
  it should "parse array elements" in {
    parseSucceeds("arr[0]", ArrayElem(Ident("arr")(pos), List(IntLiter(0)(pos)))(pos))
  }

  it should "parse 2d array elements" in {
    parseSucceeds("arr[0][1]", ArrayElem(Ident("arr")(pos), List(IntLiter(0)(pos), IntLiter(1)(pos)))(pos))
  }

}