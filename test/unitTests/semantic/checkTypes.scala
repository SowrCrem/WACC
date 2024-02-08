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
  ArgList
}
import parsley.{Failure, Result, Success}
import wacc.parser._
import wacc.lexer._
import org.scalactic.Bool
import org.scalatest.compatible.Assertion
import wacc.SymbolTable
import org.scalatest.BeforeAndAfterEach
import wacc.TypeChecker

class typeCheckerTest extends AnyFlatSpec with BeforeAndAfterEach {

  // Tests --------------------------------------------------------------------------------------------------------------

  var symbolTable: SymbolTable = _
  var typeChecker: TypeChecker = _

  override def beforeEach(): Unit = {
    symbolTable = new SymbolTable(None)
    typeChecker = new TypeChecker(symbolTable)
  }

  "the type checker" should "correctly type check integer literals" in {

    val node = IntLiter(1)
    // Check that no exception is thrown for a valid type check
    noException should be thrownBy typeChecker.check(node)
  }

  it should "correctly type check character literals" in {

    val node = CharLiter('a')
    // Check that no exception is thrown for a valid type check
    noException should be thrownBy typeChecker.check(node)
  }

  it should "correctly type check string literals" in {

    val node = StringLiter("hello")
    // Check that no exception is thrown for a valid type check
    noException should be thrownBy typeChecker.check(node)
  }

  it should "correctly type check boolean literals" in {

    val node = BoolLiter(true)
    // Check that no exception is thrown for a valid type check
    noException should be thrownBy typeChecker.check(node)
  }

  it should "correctly type check brackets" in {

    val node = Brackets(IntLiter(1))
    // Check that no exception is thrown for a valid type check
    try {
      typeChecker.check(node)
    } catch {
      case e: Exception => fail("Identifier type check failed")
    }
  }

  it should "correctly type check programs" in {
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

    // Check that no exception is thrown for a valid type check
    try {
      typeChecker.check(node)
    } catch {
      case e: Exception => fail("Identifier type check failed")
    }
  }

    it should "correctly type check function calls" in {
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
      IdentAsgn(IntTypeNode(), Ident("x"), Call(Ident("f"), ArgList(List(IntLiter(1)))))
    )

    // Check that no exception is thrown for a valid type check
    try {
      typeChecker.check(node)
    } catch {
      case e: Exception => fail("Identifier type check failed")
    }
  }

}
