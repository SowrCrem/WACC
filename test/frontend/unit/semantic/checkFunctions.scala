// import org.scalatest.flatspec.AnyFlatSpec
// import org.scalatest.matchers.should.Matchers._
// import wacc.Main
// import wacc.{
//   Position,
//   IntLiter,
//   CharLiter,
//   StringLiter,
//   BoolLiter,
//   Ident,
//   Brackets,
//   Null,
//   Error,
//   ArrayElem,
//   Plus,
//   Mul,
//   Div,
//   Not,
//   Len,
//   Skip,
//   Read,
//   If,
//   While,
//   Free,
//   Return,
//   Exit,
//   Print,
//   Println,
//   StatJoin,
//   IdentAsgn,
//   IntTypeNode,
//   PairTypeNode,
//   PairElemTypeNode,
//   ArrayTypeNode,
//   BoolTypeNode,
//   CharTypeNode,
//   StringTypeNode,
//   Func,
//   ParamList,
//   Param,
//   Program,
//   TypeNode,
//   Call,
//   Expr,
//   NewPair,
//   ArgList
// }
// import parsley.{Failure, Result, Success}
// import wacc.parser._
// import wacc.lexer._
// import org.scalactic.Bool
// import org.scalatest.compatible.Assertion
// import wacc.SymbolTable
// import org.scalatest.BeforeAndAfterEach
// import wacc.TypeChecker

// class checkFunctions extends AnyFlatSpec with BeforeAndAfterEach {

//   var symbolTable: SymbolTable = _
//   var typeChecker: TypeChecker = _

//   // Testing Functions -------------------------------------------------------------------------------------------------

//   override def beforeEach(): Unit = {
//     symbolTable = new SymbolTable(None)
//     typeChecker = new TypeChecker(symbolTable)
//   }

//   def checkSucceeds(position: Position): Assertion = noException should be thrownBy typeChecker.check(position)

//   def checkFails(position: Position, errorMessage: String): Assertion = {
//     val e = intercept[Exception] {
//       typeChecker.check(position)
//     }
//     e.getMessage shouldBe errorMessage.toString()
//   }

//   // Tests for Functions ------------------------------------------------------------------------------------------------
  
//   "The type checker" should "accept function calls" in {
//     val position = Program(
//       List(
//         Func(
//           IntTypeNode(),
//           Ident("f"),
//           ParamList(List(Param(IntTypeNode(), Ident("x")))),
//           Return(IntLiter(1))
//         )
//       ),
//       IdentAsgn(IntTypeNode(), Ident("x"), Call(Ident("f"), ArgList(List(IntLiter(1)))))
//     )
//     checkSucceeds(position)
//   }

  
// }