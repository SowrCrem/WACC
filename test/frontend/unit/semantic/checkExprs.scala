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
//   Or
// }
// import parsley.{Failure, Result, Success}
// import wacc.parser._
// import wacc.lexer._
// import org.scalactic.Bool
// import org.scalatest.compatible.Assertion
// import wacc.SymbolTable
// import org.scalatest.BeforeAndAfterEach
// import wacc.TypeChecker

// class checkExprs extends AnyFlatSpec with BeforeAndAfterEach {

//   var symbolTable: SymbolTable = _
//   var typeChecker: TypeChecker = _

//   // Testing Functions -------------------------------------------------------------------------------------------------

//   override def beforeEach(): Unit = {
//     symbolTable = new SymbolTable(None)
//     typeChecker = new TypeChecker(symbolTable)
//   }

//   def checkSucceeds(position: Position): Assertion = noException should be thrownBy typeChecker.check(position)

//   def checkFails(position: Position, errorMessage: String): Assertion = {
//     val e = intercept[SemanticError] {
//       typeChecker.check(position)
//     }
//     e.getMessage shouldBe errorMessage
//   }

//   // Tests for Unary Operator ------------------------------------------------------------------------------------------

//   it should "accept boolean negations" in {
//     checkSucceeds(Not(BoolLiter(true)))
//     checkSucceeds(Not(BoolLiter(false)))
//   }

//   it should "reject negations with non-bool types" in {
//     checkFails(Not(IntLiter(1)),          "Expected bool type, but got int type")
//     checkFails(Not(CharLiter('a')),       "Expected bool type, but got char type")
//     checkFails(Not(StringLiter("hello")), "Expected bool type, but got string type")
//     checkFails(Not(Null()),               "Expected bool type, but got null type")
//   }

//   it should "accept negations with brackets" in {
//     checkSucceeds(Not(Brackets(BoolLiter(true))))
//     checkSucceeds(Not(Brackets(BoolLiter(false))))
//   }

//   it should "accept integer negations" in {
//     checkSucceeds(Neg(IntLiter(1)))
//   }

//   it should "reject negations with non-int types" in {
//     checkFails(Neg(BoolLiter(true)),      "Expected int type, but got bool type")
//     checkFails(Neg(CharLiter('a')),       "Expected int type, but got char type")
//     checkFails(Neg(StringLiter("hello")), "Expected int type, but got string type")
//     checkFails(Neg(Null()),               "Expected int type, but got null type")
//   }

//   it should "accept len" in {
//     checkSucceeds(Len(StringLiter("hello")))
//     checkSucceeds(Len(ArrayLiter(List(IntLiter(1), IntLiter(2), IntLiter(3)))))
//   }

//   it should "reject len with non-array or non-string types" in {
//     checkFails(Len(BoolLiter(true)), "Expected array or string type, but got bool type")
//     checkFails(Len(IntLiter(1)),     "Expected array or string type, but got int type")
//     checkFails(Len(CharLiter('a')),  "Expected array or string type, but got char type")
//     checkFails(Len(Null()),          "Expected array or string type, but got null type")
//   }

//   it should "accept ord" in {
//     checkSucceeds(Ord(CharLiter('a')))
//   }

//   it should "reject ord with non-char types" in {
//     checkFails(Ord(BoolLiter(true)),      "Expected char type, but got bool type")
//     checkFails(Ord(IntLiter(1)),          "Expected char type, but got int type")
//     checkFails(Ord(StringLiter("hello")), "Expected char type, but got string type")
//     checkFails(Ord(Null()),               "Expected char type, but got null type")
//   }

//   it should "accept chr" in {
//     checkSucceeds(Chr(IntLiter(97)))
//   }

//   it should "reject chr with non-int types" in {
//     checkFails(Chr(BoolLiter(true)),      "Expected int type, but got bool type")
//     checkFails(Chr(CharLiter('a')),       "Expected int type, but got char type")
//     checkFails(Chr(StringLiter("hello")), "Expected int type, but got string type")
//     checkFails(Chr(Null()),               "Expected int type, but got null type")
//   }

//   // Tests for Binary Operator -----------------------------------------------------------------------------------------

//   it should "accept multiplication" in {
//     checkSucceeds(Mul(IntLiter(1), IntLiter(2)))
//     checkSucceeds(Mul(Mul(IntLiter(1), IntLiter(2)), IntLiter(3)))
//   }

//   it should "reject multiplication with non-int types" in {
//     checkFails(Mul(BoolLiter(true), IntLiter(1)),      "Expected int type, but got bool type")
//     checkFails(Mul(CharLiter('a'), IntLiter(1)),       "Expected int type, but got char type")
//     checkFails(Mul(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(Mul(Null(), IntLiter(1)),               "Expected int type, but got null type")
//   }

//   it should "accept division" in {
//     checkSucceeds(Div(IntLiter(1), IntLiter(2)))
//     checkSucceeds(Div(Div(IntLiter(1), IntLiter(2)), IntLiter(3)))
//   }

//   it should "reject division with non-int types" in {
//     checkFails(Div(BoolLiter(true), IntLiter(1)),      "Expected int type, but got bool type")
//     checkFails(Div(CharLiter('a'), IntLiter(1)),       "Expected int type, but got char type")
//     checkFails(Div(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(Div(Null(), IntLiter(1)),               "Expected int type, but got null type")
//   }

//   it should "accept modulo" in {
//     checkSucceeds(Mod(IntLiter(1), IntLiter(2)))
//     checkSucceeds(Mod(Mod(IntLiter(1), IntLiter(2)), IntLiter(3)))
//   }

//   it should "reject modulo with non-int types" in {
//     checkFails(Mod(BoolLiter(true), IntLiter(1)),      "Expected int type, but got bool type")
//     checkFails(Mod(CharLiter('a'), IntLiter(1)),       "Expected int type, but got char type")
//     checkFails(Mod(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(Mod(Null(), IntLiter(1)),               "Expected int type, but got null type")
//   }

//   it should "accept addition" in {
//     checkSucceeds(Plus(IntLiter(1), IntLiter(2)))
//     checkSucceeds(Plus(Plus(IntLiter(1), IntLiter(2)), IntLiter(3)))
//   }

//   it should "reject addition with non-int types" in {
//     checkFails(Plus(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(Plus(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(Plus(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(Plus(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept subtraction" in {
//     checkSucceeds(Minus(IntLiter(1), IntLiter(2)))
//     checkSucceeds(Minus(Minus(IntLiter(1), IntLiter(2)), IntLiter(3)))
//   }

//   it should "reject subtraction with non-int types" in {
//     checkFails(Minus(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(Minus(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(Minus(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(Minus(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept greater than" in {
//     checkSucceeds(GreaterThan(IntLiter(1), IntLiter(2)))
//   }

//   it should "reject greater than with non-int types" in {
//     checkFails(GreaterThan(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(GreaterThan(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(GreaterThan(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(GreaterThan(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept greater than or equal" in {
//     checkSucceeds(GreaterThanEq(IntLiter(1), IntLiter(2)))
//   }

//   it should "reject greater than or equal with non-int types" in {
//     checkFails(GreaterThanEq(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(GreaterThanEq(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(GreaterThanEq(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(GreaterThanEq(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept less than" in {
//     checkSucceeds(LessThan(IntLiter(1), IntLiter(2)))
//   }

//   it should "reject less than with non-int types" in {
//     checkFails(LessThan(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(LessThan(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(LessThan(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(LessThan(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept less than or equal" in {
//     checkSucceeds(LessThanEq(IntLiter(1), IntLiter(2)))
//   }

//   it should "reject less than or equal with non-int types" in {
//     checkFails(LessThanEq(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(LessThanEq(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(LessThanEq(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(LessThanEq(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept equals" in {
//     checkSucceeds(Equals(IntLiter(1), IntLiter(2)))
//     checkSucceeds(Equals(CharLiter('a'), CharLiter('b')))
//     checkSucceeds(Equals(StringLiter("hello"), StringLiter("world")))
//     checkSucceeds(Equals(Null(), Null()))
//   }

//   it should "reject equals with non-matching types" in {
//     checkFails(Equals(BoolLiter(true), IntLiter(1)), "Type mismatch: expected bool, got int")
//     checkFails(Equals(CharLiter('a'), IntLiter(1)), "Type mismatch: expected char, got int")
//     checkFails(Equals(StringLiter("hello"), IntLiter(1)), "Type mismatch: expected string, got int")
//     checkFails(Equals(Null(), IntLiter(1)), "Type mismatch: expected null, got int")
//   }

//   it should "accept not equals" in {
//     checkSucceeds(NotEquals(IntLiter(1), IntLiter(2)))
//   }

//   it should "reject not equals with non-int types" in {
//     checkFails(NotEquals(BoolLiter(true), IntLiter(1)), "Expected int type, but got bool type")
//     checkFails(NotEquals(CharLiter('a'), IntLiter(1)), "Expected int type, but got char type")
//     checkFails(NotEquals(StringLiter("hello"), IntLiter(1)), "Expected int type, but got string type")
//     checkFails(NotEquals(Null(), IntLiter(1)), "Expected int type, but got null type")
//   }

//   it should "accept and" in {
//     checkSucceeds(And(BoolLiter(true), BoolLiter(false)))
//   }

//   it should "reject and with non-bool types" in {
//     checkFails(And(IntLiter(1), BoolLiter(true)), "Expected bool type, but got int type")
//     checkFails(And(CharLiter('a'), BoolLiter(true)), "Expected bool type, but got char type")
//     checkFails(And(StringLiter("hello"), BoolLiter(true)), "Expected bool type, but got string type")
//     checkFails(And(Null(), BoolLiter(true)), "Expected bool type, but got null type")
//   }

//   it should "accept or" in {
//     checkSucceeds(Or(BoolLiter(true), BoolLiter(false)))
//   }

//   it should "reject or with non-bool types" in {
//     checkFails(Or(IntLiter(1), BoolLiter(true)), "Expected bool type, but got int type")
//     checkFails(Or(CharLiter('a'), BoolLiter(true)), "Expected bool type, but got char type")
//     checkFails(Or(StringLiter("hello"), BoolLiter(true)), "Expected bool type, but got string type")
//     checkFails(Or(Null(), BoolLiter(true)), "Expected bool type, but got null type")
//   }

//   // Tests for ArrayElem ----------------------------------------------------------------------------------------
  
//   it should "accept array elements" in {
//     checkSucceeds(ArrayElem(Ident("arr"), List(IntLiter(0))))
//   }

//   it should "accept 2d array elements" in {
//     checkSucceeds(ArrayElem(Ident("arr"), List(IntLiter(0), IntLiter(1))))
//   }

// }