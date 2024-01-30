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
  IntType,
  PairType,
  PairElemType,
  ArrayType,
  BoolType,
  CharType,
  StringType
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import wacc.NewPair

class functionProgramTests extends AnyFlatSpec {

}