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
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._

class stmtParsingTests extends AnyFlatSpec {

  "stmt parser" should "parse skip" in {
    parser.parse("skip") should be(Success(Skip()))
  }

  it should "parse read" in {
    parser.parse("read x") should be(Success(Read(Ident("x"))))
  }

  it should "parse if" in {
    parser.parse("if (true) then skip else skip fi") should be(
      Success(
        If(
          Brackets(BoolLiter(true)),
          Skip(),
          Skip()
        )
      )
    )
  }

}
