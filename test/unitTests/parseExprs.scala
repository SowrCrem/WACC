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
  StringType,
  Ident,
  Brackets,
  Null,
  Error,
  Node
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.Type

class parseExprs extends AnyFlatSpec {

  // Testing Functions -------------------------------------------------------------------------------------------------
   def getType(expected: Node): (String, Type) = expected match {
    case IntLiter(_)    => ("int"   , IntType())
    case Neg(_)         => ("int"   , IntType())
    case BoolLiter(_)   => ("bool"  , BoolType())
    case CharLiter(_)   => ("char"  , CharType())
    case StringLiter(_) => ("string", StringType())
    case Brackets(expr) => getType(expr)
    case _              => ("int"   , IntType())
  }

  def parseSucceeds[T](input: String, expected: Node, identifier: String = "input", comment: String = ""): Assertion = {
    val (literalType, typeNode) = getType(expected)
    val assignment = IdentAsgn(typeNode, Ident(identifier), expected)
    parser.parse("begin " + literalType + " " + identifier + " = " + input + " " + comment + "\n end") shouldBe Success(Program(List(), assignment))
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
  //   parseSucceeds("arr[0]", IntLiter(1)) // TODO: Change to array
  // }

  // it should "parse 2d array elements" in {
  //   pending
  //   parseSucceeds("arr[0][1]", IntLiter(1)) // TODO: Change to array
  // }
}
