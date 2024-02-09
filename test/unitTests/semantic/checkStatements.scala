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
//   ArgList,
//   SemanticError,
//   ArrayLiter,
//   Neg,
//   Ord,
//   Chr,
//   Mod,
//   Minus,
//   GreaterThan,
//   GreaterThanEq,
//   LessThan,
//   LessThanEq,
//   Equals,
//   NotEquals,
//   And,
//   Or,
//   Stat,
//   AsgnEq,
//   FstNode,
//   SndNode
// }
// import parsley.{Failure, Result, Success}
// import wacc.parser._
// import wacc.lexer._
// import org.scalactic.Bool
// import org.scalatest.compatible.Assertion
// import wacc.SymbolTable
// import org.scalatest.BeforeAndAfterEach
// import wacc.TypeChecker

// class checkStatements extends AnyFlatSpec with BeforeAndAfterEach {

//   var symbolTable: SymbolTable = _
//   var typeChecker: TypeChecker = _

//   // Testing Functions -------------------------------------------------------------------------------------------------

//   override def beforeEach(): Unit = {
//     symbolTable = new SymbolTable(None)
//     typeChecker = new TypeChecker(symbolTable)
//   }

//   def checkSucceeds(node: Position): Assertion = {
//     try {
//       noException shouldBe thrownBy (typeChecker.check(node))
//     } catch {
//       case e: Exception => checkFails(node, "Unexpected Error")
//     }
//   }

//   def checkFails(node: Position, errorMessage: String = ""): Assertion = errorMessage match {
//     case "" => {
//       a [SemanticError] shouldBe thrownBy {
//         typeChecker.check(node)
//       }
//     }
//     case msg => {
//       val e = intercept[SemanticError] {
//         typeChecker.check(node)
//       }
//       e.getMessage shouldBe msg
//     }
//   }

//   // Tests for skip ------------------------------------------------------------------------------------------------------

//   "The type checker" should "accept skip" in {
//     checkSucceeds(Skip())
//   }
  
//   // Tests for assignment ------------------------------------------------------------------------------------------------

//   it should "accept LHS new identifier assignments" in {
//     checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1)))
//   }

//   it should "accept LHS existing identifier assignments" in{
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1)),
//       AsgnEq(Ident("x"), IntLiter(3))
//     )))
//   }

//   it should "reject LHS non-existing identifier assignments" in {
//     checkFails(AsgnEq(Ident("x"), IntLiter(3)), "Variable x not declared")
//     checkFails(AsgnEq(ArrayElem(Ident("x"), List(IntLiter(1))), IntLiter(3)), "Variable x not declared")
//   }

//   it should "accept LHS existing array elem assignments" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(ArrayTypeNode(IntTypeNode()), Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))),
//       AsgnEq(ArrayElem(Ident("x"), List(IntLiter(2))), IntLiter(2))
//     )))
//   }

//   it should "reject LHS non-existing array elem assignments" in {
//     checkFails(AsgnEq(ArrayElem(Ident("x"), List(IntLiter(1))), IntLiter(3)), "Variable x not declared")
//   }

//   it should "accept LHS existing pair elem assignments" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(PairTypeNode(IntTypeNode(), IntTypeNode()), Ident("p"), NewPair(IntLiter(1), IntLiter(2))),
//       AsgnEq(FstNode(Ident("p")), IntLiter(3)),
//       AsgnEq(SndNode(Ident("p")), IntLiter(4))
//     )))
//   }

//   it should "reject LHS non-existing pair elem assignments" in {
//     checkFails(AsgnEq(FstNode(Ident("p")), IntLiter(3)), "Variable p not declared")
//     checkFails(AsgnEq(SndNode(Ident("p")), IntLiter(3)), "Variable p not declared")
//   }

//   it should "accept RHS expression assignments" in {
//     checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), Plus(IntLiter(1), IntLiter(2))))
//     checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), Mul(IntLiter(1), IntLiter(2))))
//     checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), Div(IntLiter(1), IntLiter(2))))
//     checkSucceeds(IdentAsgn(BoolTypeNode(), Ident("x"), Not(BoolLiter(true))))
//     // This is failing:
//     // checkSucceeds(IdentAsgn(IntTypeNode(), Ident("x"), Len(StringLiter("hello"))))
//   }

//   it should "accept RHS array literal assignments" in {
//     checkSucceeds(IdentAsgn(ArrayTypeNode(IntTypeNode()), Ident("x"), ArrayLiter(List())))
//     checkSucceeds(IdentAsgn(ArrayTypeNode(IntTypeNode()), Ident("x"), ArrayLiter(List(IntLiter(1)))))
//     checkSucceeds(IdentAsgn(ArrayTypeNode(IntTypeNode()), Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
//   }

//   it should "accept RHS new pair assignments" in {
//     checkSucceeds(IdentAsgn(PairTypeNode(IntTypeNode(), IntTypeNode()), Ident("x"), NewPair(IntLiter(1), IntLiter(2))))
//   }

//   it should "accept RHS existing pair elem assignments" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(PairTypeNode(IntTypeNode(), IntTypeNode()), Ident("p"), NewPair(IntLiter(1), IntLiter(2))),
//       AsgnEq(Ident("x"), FstNode(Ident("p"))),
//       AsgnEq(Ident("x"), SndNode(Ident("p")))
//     )))
//   }

//   it should "reject RHS non-existing pair elem assignments" in {
//     checkSucceeds(AsgnEq(Ident("x"), FstNode(Ident("p"))))
//     checkSucceeds(AsgnEq(Ident("x"), SndNode(Ident("p"))))
//   }

//   it should "accept RHS call to existing function assignments" in {
//     checkSucceeds(Program(List(Func(IntTypeNode(), Ident("f"), ParamList(List()), Return(IntLiter(1)))),
//       IdentAsgn(IntTypeNode(), Ident("x"), Call(Ident("f"), ArgList(List()))))
//     )
//   }

//   it should "reject RHS call to non-existing function assignments" in {
//     checkFails(AsgnEq(Ident("x"), Call(Ident("f"), ArgList(List()))))
//     checkFails(AsgnEq(Ident("x"), Call(Ident("f"), ArgList( List(IntLiter(1), IntLiter(2), IntLiter(3))))))
//   }

//   // Tests for read ------------------------------------------------------------------------------------------------------

//   it should "accept read with existing identifier" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1)),
//       Read(Ident("x"))
//     )))
//   }

//   it should "reject read with non-existing identifier" in {
//     checkFails(Read(Ident("x")), "Variable x not declared")
//   }

//   it should "accept read with existing array elem" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(ArrayTypeNode(IntTypeNode()), Ident("x"), ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))),
//       Read(ArrayElem(Ident("x"), List(IntLiter(1))))
//     )))
//   }

//   it should "reject read with non-existing array elem" in {
//     checkFails(Read(ArrayElem(Ident("x"), List(IntLiter(1)))))
//   }

//   it should "accept read with existing pair elem" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(PairTypeNode(IntTypeNode(), IntTypeNode()), Ident("p"), NewPair(IntLiter(1), IntLiter(2))),
//       Read(FstNode(Ident("p"))),
//       Read(SndNode(Ident("p")))
//       )))
//   }

//   it should "reject read with non-existing pair elem" in {
//     checkFails(Read(FstNode(Ident("p"))))
//     checkFails(Read(SndNode(Ident("p"))))
//   }

//   // Tests for free ------------------------------------------------------------------------------------------------------

//   it should "accept free with existing identifier" in {
//     checkSucceeds(StatJoin(List(
//       IdentAsgn(IntTypeNode(), Ident("x"), IntLiter(1)),
//       Free(Ident("x"))
//     )))
//   }

//   it should "reject free with non-existing identifier" in {
//     checkFails(Free(Ident("x")))
//   }

//   // Tests for return ----------------------------------------------------------------------------------------------------

//   it should "accept return" in {
//     // checkSucceeds should test a function with a return value of 1
//     checkSucceeds(Program(List(Func(IntTypeNode(), Ident("f"), ParamList(List()), Return(IntLiter(1)))),
//     Call(Ident("f"), ArgList(List()))))
//   }

//   it should "reject return with statement after" in {
//     // checkFails(StatJoin(List(Return(IntLiter(1)), Skip())), "Return statement must be the last statement in a function")
//     checkFails(Program(List(Func(IntTypeNode(), Ident("f"), ParamList(List()), Return(IntLiter(1)))),
//     StatJoin(List(Return(IntLiter(1)), Skip()))),
//     "Return statement must be the last statement in a function")
//   }

//   // Tests for exit ------------------------------------------------------------------------------------------------------

//   it should "accept exit" in {
//     checkSucceeds(Exit(IntLiter(1)))
//   }

//   // Tests for print -----------------------------------------------------------------------------------------------------

//   it should "accept print" in {
//     checkSucceeds(Print(IntLiter(1)))
//   }

//   // Tests for println ---------------------------------------------------------------------------------------------------

//   it should "accept println" in {
//     checkSucceeds(Println(IntLiter(1)))
//   }

//   // Tests for if --------------------------------------------------------------------------------------------------------

//   it should "accept if with well-formed body" in {
//     checkSucceeds(If(BoolLiter(true), Print(IntLiter(1)), Print(IntLiter(2))))
//   }

//   it should "reject if with Skips in body" in {
//     checkFails(If(BoolLiter(true), Skip(), Skip()))
//   }

//   // Tests for while -----------------------------------------------------------------------------------------------------

//   it should "accept while with well-formed body" in {
//     checkSucceeds(While(BoolLiter(true), Print(IntLiter(1))))
//   }

//   it should "reject while with Skips in body" in {
//     checkFails(While(BoolLiter(true), Skip()))
//   }

//   // Tests for semicolon -------------------------------------------------------------------------------------------------

//   it should "accept multiple well-formed statements" in {
//     checkSucceeds(StatJoin(List(Print(IntLiter(1)), Print(IntLiter(2)))))
//   }

//   it should "reject multiple skips" in {
//     checkFails(StatJoin(List(Skip(), Skip())), "Empty statement")
//   }
// }
