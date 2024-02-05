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
  Exit
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.TypeNode

class parseExprs extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------
   def getType(expected: Node): (String, TypeNode) = expected match {
    case IntLiter(_)    => ("int"   , IntTypeNode())
    case Neg(_)         => ("int"   , IntTypeNode())
    case BoolLiter(_)   => ("bool"  , BoolTypeNode())
    case CharLiter(_)   => ("char"  , CharTypeNode())
    case StringLiter(_) => ("string", StringTypeNode())
    case Brackets(expr) => getType(expr)
    case _              => ("int"   , IntTypeNode())
  }

  def parseSucceeds[T](input: String, expected: Expr, identifier: String = "input", comment: String = ""): Assertion = {
    parser.parse("begin exit " + input + " end") shouldBe Success(Program(List(), Exit(expected)))
  }

  def parseFails(input: String, inputType: String, identifier: String = "input"): Assertion = {
    parser.parse("begin " + inputType + " " + identifier + " = " + input + " end") should matchPattern {
      case Failure(_) => // Match on any Failure
    }
  }

  def parseWithIdentifier(name:String, success:Boolean): Assertion = {
    if (success) parseSucceeds("1", IntLiter(1), name) else parseFails("1", "int", name)
  }

  def commentIgnored(comment: String) = {
    parseSucceeds("1", IntLiter(1), "input", comment)
  }

  // Tests for Unary Operator ------------------------------------------------------------------------------------------


  // Tests for Binary Operator -----------------------------------------------------------------------------------------


  // Tests for ArrayElem ----------------------------------------------------------------------------------------
  // it should "parse array elements" in {
  //   pending
  //   parseSucceeds("arr[0]", ArrayLiter(List())) // TODO: Change to array
  // }

  // it should "parse 2d array elements" in {
  //   pending
  //   parseSucceeds("arr[0][1]", IntLiter(1)) // TODO: Change to array
  // }
}
