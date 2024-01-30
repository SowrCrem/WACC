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

  it should "parse list of comma separated statements" in {
    parser.parse("skip; skip; skip") should be(
      Success(StatJoin(List(Skip(), Skip(), Skip())))
    )
  }

  it should "parse while loop with skip" in {
    parser.parse("while (true) do skip done") should be(
      Success(
        While(
          Brackets(BoolLiter(true)),
          Skip()
        )
      )
    )
  }

  it should "parse free, return, read, exit and print statements" in {
    parser.parse("free x; return x; exit x; print x; println x") should be(
      Success(
        StatJoin(
          List(
            Free(Ident("x")),
            Return(Ident("x")),
            Exit(Ident("x")),
            Print(Ident("x")),
            Println(Ident("x"))
          )
        )
      )
    )
  }

  it should "parse begin end" in {
    parser.parse("begin skip end") should be(
      Success(
        Skip()
      )
    )
  }

  it should "parse new identifiers" in {
    parser.parse("int x = 1") should be(
      Success(
        IdentAsgn(
          IntType(),
          Ident("x"),
          IntLiter(1)
        )
      )
    )
  }

  it should "parse complex pair types " in {
    parser.parse("pair(int, pair(int, char)[]) x = newpair(1, 2)") should be(
      Success(
        IdentAsgn(
          PairType(IntType(), ArrayType(PairType(IntType(), CharType()))),
          Ident("x"),
          NewPair(IntLiter(1), IntLiter(2))
        )
      )
    )

  }

  it should "parse pair identifier" in {
    parser.parse("pair(int, int) x = newpair(1, 2)") should be(
      Success(
        IdentAsgn(
          PairType(IntType(), IntType()),
          Ident("x"),
          NewPair(IntLiter(1), IntLiter(2))
        )
      )
    )
  }

}
