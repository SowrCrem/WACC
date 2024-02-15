// package test.frontend.unit.syntax

// import wacc.Main
// import test.Utils._
// import wacc.lexer._
// import wacc.parser._
// import wacc.{
//   Program,
//   Expr,
//   Neg,
//   Null,
//   Exit,
//   Error,
//   Ident,
//   TypeNode,
//   Position,
//   Brackets,
//   IdentAsgn,
//   IntLiter,
//   BoolLiter,
//   CharLiter,
//   StringLiter,
//   IntTypeNode,
//   BoolTypeNode,
//   CharTypeNode,
//   StringTypeNode
// }
// import java.lang.StringBuilder
// import parsley.{Failure, Result, Success}
// import org.scalactic.{Fail, Bool}
// import org.scalatest.compatible.Assertion
// import org.scalatest.flatspec.AnyFlatSpec
// import org.scalatest.matchers.should.Matchers._


// class parseAtoms extends AnyFlatSpec {

//   // Tests for IntLiter -----------------------------------------------------------------------------------------------
//   "the parser" should "parse unsigned int literals" in {
//     parseSucceeds("123", IntLiter(123)(pos))
//   }

//   it should "parse signed positive int literals" in {
//     parseSucceeds("+123", IntLiter(123)(pos))
//   }

//   it should "parse signed negative int literals" in {
//     parseSucceeds("-123", Neg(IntLiter(123)(pos))(pos))
//   }

//   it should "parse maximum signed int literals" in {
//     parseSucceeds("2147483647", IntLiter(2147483647)(pos))
//   }

//   it should "parse minimum signed int literals" ignore {
//     parseSucceeds("-2147483648", IntLiter(-2147483648)(pos))
//   }

//   it should "reject int literals outside of the range of a 32-bit signed int" in {
//     val errBuilder = new StringBuilder()
//     errBuilder.append("(line 1, column 12):\n")
//     errBuilder.append("  literal is not within the range -2147483648 to 2147483647\n")
//     errBuilder.append("  >begin exit 2147483648 end\n")
//     errBuilder.append("              ^^^^^^^^^^")
//     parseFails("2147483648", errBuilder.toString())

//     val errBuilder2 = new StringBuilder()
//     errBuilder2.append("(line 1, column 13):\n")
//     errBuilder2.append("  literal is not within the range -2147483648 to 2147483647\n")
//     errBuilder2.append("  >begin exit -2147483649 end\n")
//     errBuilder2.append("               ^^^^^^^^^^")
//     parseFails("-2147483649", errBuilder2.toString())
//   }

//   it should "reject doubly positively signed int literals" in {
//     val errorBuilder = new StringBuilder()
//     errorBuilder.append("(line 1, column 13):\n")
//     errorBuilder.append("  unexpected \"+\"\n")
//     errorBuilder.append("  expected digit\n")
//     errorBuilder.append("  >begin exit ++123 end\n")
//     errorBuilder.append("               ^")
//     parseFails("++123", errorBuilder.toString())
//   }

//   it should "parse many negative int literals" in {
//     parseSucceeds("--123", Neg(Neg(IntLiter(123)(pos))(pos))(pos) )
//     parseSucceeds("---123", Neg(Neg(Neg(IntLiter(123)(pos))(pos))(pos))(pos) )
//     parseSucceeds("----123", Neg(Neg(Neg(Neg(IntLiter(123)(pos))(pos))(pos))(pos))(pos) )
//   }

//   // Tests for BoolLiter ----------------------------------------------------------------------------------------------
//   it should "parse true bool literals" in {
//     parseSucceeds("true", BoolLiter(true)(pos))
//   }

//   it should "parse false bool literals" in {
//     parseSucceeds("false", BoolLiter(false)(pos))
//   }

//   // Tests for CharLiter ----------------------------------------------------------------------------------------------
//   it should "parse char literals" in {
//     parseSucceeds("\'a\'", CharLiter('a')(pos))
//   }

//   it should "parse escaped char literals" in {
//     parseSucceeds("\'\\0\'" , CharLiter('\u0000')(pos))
//     parseSucceeds("\'\\b\'" , CharLiter('\b')(pos))
//     parseSucceeds("\'\\n\'" , CharLiter('\n')(pos))
//     parseSucceeds("\'\\f\'" , CharLiter('\f')(pos))
//     parseSucceeds("\'\\r\'" , CharLiter('\r')(pos))
//     parseSucceeds("\'\\t\'" , CharLiter('\t')(pos))
//     parseSucceeds("\'\\\'\'", CharLiter('\'')(pos))
//   }

//   it should "reject invalid escaped char literals" in {
//     parseFails("\'\\a\'")
//     parseFails("\'\\z\'")
//     parseFails("\'\\ \'")
//     parseFails("\'\\x\'")
//     parseFails("\'\\u\'")
//     parseFails("\'\\U\'")
//   }

//   it should "reject invalid char literals" in {
    
//     // TODO: Should we include empty char? If not, write a test making sure its not rejected
//     parseFails("\'\'") // Empty char
//     parseFails("\'\\\'") // Parsing " '\' " should fail
//     parseFails("\'\"\'") // Double quote
//     parseSucceeds("\'\\\"\'", CharLiter('\"')(pos))
//     parseFails("\'\'\'") // Single quote
//     parseSucceeds("\'\\\'\'", CharLiter('\'')(pos))
//   }

//   // Tests for StringLiter --------------------------------------------------------------------------------------------

//   it should "parse empty string literals" in {
//     parseSucceeds("\"\"", StringLiter("")(pos))
//   }

//   it should "parse string literals" in {
//     parseSucceeds("\"hello\"", StringLiter("hello")(pos))
//     parseSucceeds("\"hello world\"", StringLiter("hello world")(pos))
//   }

//   it should "parse escaped string literals" in {
    
//     parseSucceeds("\"\\0\"" , StringLiter("\u0000")(pos))
//     parseSucceeds("\"\\b\"" , StringLiter("\b")(pos))
//     parseSucceeds("\"\\n\"" , StringLiter("\n")(pos))
//     parseSucceeds("\"\\f\"" , StringLiter("\f")(pos))
//     parseSucceeds("\"\\r\"" , StringLiter("\r")(pos))
//     parseSucceeds("\"\\t\"" , StringLiter("\t")(pos))
//     parseSucceeds("\"\""    , StringLiter("")(pos))   // Empty char
//     parseSucceeds("\"\\\'\"", StringLiter("\'")(pos)) // Single quote
//   }

//   it should "reject invalid string literals" in {
//     parseFails("\"\\\"") // Backslash
//     parseFails("\"\"\"") // Double quote
//     parseFails("\"\'\"") // Single quote
//   }

//   it should "reject invalid escaped string literals" in {
    
//     parseFails("\"\\a\"" )
//     parseFails("\"\\z\"" )
//     // parseFails("\"\\\'\"")
//     // parseFails("\"\\\"\"")
//     parseFails("\"\\x\"" )
//     parseFails("\"\\u\"" )
//     parseFails("\"\\U\"" )
//   }

//   // Tests for Null (PairLiter) ---------------------------------------------------------------------------------------
//   it should "parse null literals" in {
//     parseSucceeds("null", Null()(pos))
//   }

//   // Tests for Brackets -----------------------------------------------------------------------------------------------
//   it should "parse brackets" in {
//     parseSucceeds("(123)"  , Brackets(IntLiter(123)(pos))(pos) )
//     parseSucceeds("(true)" , Brackets(BoolLiter(true)(pos))(pos) )
//     parseSucceeds("(\'a\')", Brackets(CharLiter('a')(pos))(pos))
//     parseSucceeds("(\"\")" , Brackets(StringLiter("")(pos))(pos))
//     parseSucceeds("(null)" , Brackets(Null()(pos))(pos))
//     parseSucceeds("(hello)", Brackets(Ident("hello")(pos))(pos))
//   }

//   it should "parse double brackets" ignore {
//     parseSucceeds("((123))"  , Brackets(Brackets(IntLiter(123)(pos))(pos))(pos))
//     parseSucceeds("((true))" , Brackets(Brackets(BoolLiter(true)(pos))(pos))(pos))
//     parseSucceeds("((\'a\'))", Brackets(Brackets(CharLiter('a')(pos))(pos))(pos))
//     parseSucceeds("((\"\")"  , Brackets(Brackets(StringLiter("")(pos))(pos))(pos))
//     parseSucceeds("((null))" , Brackets(Brackets(Null()(pos))(pos))(pos))
//     parseSucceeds("((hello))", Brackets(Brackets(Ident("hello")(pos))(pos))(pos))
//   }
// }
