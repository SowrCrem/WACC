package wacc

import parsley.{Parsley, Result}
import parsley.expr.{
  chain,
  precedence,
  Ops,
  InfixL,
  Atoms,
  Prefix,
  InfixN,
  InfixR
}
import parsley.Parsley.{some, many, atomic}
import parsley.errors.ErrorBuilder
import parsley.combinator._
import lexer.implicits.implicitSymbol
import parsley.syntax.lift
import lexer.{integer, fully, char, implicits, ident, string}
import parsley.Parsley.notFollowedBy
import parsley.debug._
import parsley.Parsley.lookAhead
import parsley.character.noneOf
import parsley.{Success, Failure}
import scala.sys.process._
import parsley.character.oneOf

/* TODOs (tied to syntax.scala):
   [ ] Fix negate underflow error !!!

   [ ] Remove StatJoin and refactor Program AST to take List[Stat] instead of Stat
    [ ] Refactor AST validation to iterate through last statement in functions more easily

   [ ] Reduce use of atomics

   */

object parser {

  lazy val arrayelemParser: Parsley[Expr] =
    ArrayElem(Ident(lexer.ident), some("[" ~> exprParser <~ "]"))

  // lazy val intParser: Parsley[Expr] = lexer.integer.map {
  //   case (i, pos) if i < 0 => Neg(IntLiter(Math.abs(i))(pos))(pos)
  //   case (i, pos) => IntLiter(i)(pos)
  // }

  lazy val intParser: Parsley[Expr] = IntLiter(lexer.integer)
  lazy val digit = oneOf('0' to '9')

  lazy val boolParser: Parsley[Expr] = BoolLiter(lexer.bool)
  lazy val charParser: Parsley[Expr] = CharLiter(lexer.char)
  lazy val stringParser: Parsley[Expr] = StringLiter(lexer.string)
  lazy val identifierParser: Parsley[Ident] = Ident(lexer.ident)
  lazy val bracketsParser: Parsley[Expr] = Brackets("(" ~> exprParser <~ ")")

  lazy val atoms =
    atomic(arrayelemParser) | 
    intParser | boolParser | charParser | stringParser |
      identifierParser | bracketsParser

  // -- Pair Parser ----------------------------------------------- //

  lazy val newpairParser: Parsley[Expr] = 
    NewPair("newpair" ~> "(" ~> exprParser, "," ~> exprParser <~ ")")

  val fstParser: Parsley[Expr] = 
    some("fst") ~> notFollowedBy("null") ~> FstNode(assignLhs)

  val sndParser: Parsley[Expr] = 
    some("snd") ~> notFollowedBy("null") ~> SndNode(assignLhs)

  lazy val pairElemParser: Parsley[Expr] = fstParser | sndParser

  lazy val pairLitParser: Parsley[Expr] = 
    fstParser | sndParser | newpairParser | Null <# "null"

  // -- Expression Parsers ----------------------------------------- //

  lazy val exprParser: Parsley[Expr] = precedence(
    atoms | pairLitParser | arrayLiteralParser
  )(
    Ops(Prefix)(Not <# "!"),
    Ops(Prefix)(Neg <# atomic("-" <~ notFollowedBy(digit))),
    Ops(Prefix)(Len <# "len"),
    Ops(Prefix)(Ord <# "ord"),
    Ops(Prefix)(Chr <# "chr"),
    Ops(InfixL)(Mul <# "*"),
    Ops(InfixL)(Div <# "/"),
    Ops(InfixL)(Mod <# "%"),
    Ops(InfixL)(Plus <# "+"),
    Ops(InfixL)(Minus <# "-"),
    Ops(InfixN)(GreaterThan <# ">"),
    Ops(InfixN)(GreaterThanEq <# ">="),
    Ops(InfixN)(LessThan <# "<"),
    Ops(InfixN)(LessThanEq <# "<="),
    Ops(InfixN)(Equals <# "=="),
    Ops(InfixN)(NotEquals <# "!="),
    Ops(InfixR)(And <# "&&"),
    Ops(InfixR)(Or <# "||")
  )
  lazy val arrayLiteralParser: Parsley[Expr] = {
    val arrayLiteral = "[" ~> sepBy(exprParser, ",") <~ "]"
    ArrayLiter(arrayLiteral)
  }

  // -- Type Parsers ---------------------------------------------- //

  lazy val baseType: Parsley[BaseTypeNode] =
    IntTypeNode <# "int"| BoolTypeNode <# "bool" | CharTypeNode <# "char" | 
    StringTypeNode <# "string"

  lazy val arrayTypeParser: Parsley[ArrayTypeNode] = 
    chain.postfix1(baseType <|> pairType)(ArrayTypeNode <# ("[" <~> "]"))

  lazy val pairElemTypeParser: Parsley[PairElemTypeNode] =
    atomic(arrayTypeParser) | baseType | Null <# "pair"

  lazy val pairType: Parsley[PairTypeNode] = 
    PairTypeNode("pair" ~> "(" ~> pairElemTypeParser <~ ",", pairElemTypeParser <~ ")")

  lazy val typeParser: Parsley[TypeNode] =
    atomic(arrayTypeParser) | baseType | pairType

  // -- Statement Parsers ----------------------------------------- //

  val ifParser: Parsley[Stat] = 
    If("if" ~> exprParser, "then" ~> stmtParser, "else" ~> stmtParser <~ "fi")

  val whileParser: Parsley[Stat] = 
    While("while" ~> exprParser, "do" ~> stmtParser <~ "done")
    
  val skipParser: Parsley[Stat] = Skip <# "skip"

  val freeParser: Parsley[Stat] = Free("free" ~> exprParser)

  val beginParser: Parsley[Stat] = "begin" ~> BeginEnd(stmtParser) <~ "end"

  val returnParser: Parsley[Stat] = Return("return" ~> exprParser)

  val exitParser: Parsley[Stat] = Exit("exit" ~> exprParser)

  val printParser: Parsley[Stat] = 
    Print("print" ~> notFollowedBy(pairElemParser | arrayLiteralParser) ~> exprParser)

  val printlnParser: Parsley[Stat] = 
    Println("println" ~> notFollowedBy(pairElemParser | arrayLiteralParser) ~> exprParser)

  val callParser: Parsley[Stat] = 
    Call("call" ~> identifierParser, "(" ~> sepBy(exprParser,",") <~ ")")

  val assignRhs = {
    val assignRhs = exprParser | pairLitParser | callParser
    assignRhs
  }

  val identAsgnParser: Parsley[IdentAsgn] = 
    IdentAsgn(typeParser, atomic(identifierParser), "=" ~> assignRhs)

  val assignLhs = {
    val assignLhs = atomic(arrayelemParser) | identifierParser | pairLitParser
    assignLhs
  }

  val readParser: Parsley[Stat] = "read" ~> Read(assignLhs)

  val asgnEqParser: Parsley[Stat] = {
    AsgnEq(assignLhs, "=" ~> assignRhs)
  }

  val statAtoms: Parsley[Stat] = {
    skipParser | identAsgnParser | asgnEqParser |
      readParser | freeParser | returnParser |
      exitParser | printParser | printlnParser |
      ifParser | whileParser | beginParser
  }


  val stmtParser: Parsley[List[Stat]] = sepBy1(statAtoms, ";")
    // atomic(statAtoms <~ notFollowedBy(";")) | statJoinParser

  // -- Param Parser ----------------------------------------------- //

  val paramParser: Parsley[Param] = Param(typeParser, atomic(identifierParser))

  val paramListParser: Parsley[ParamList] =
    ParamList(sepBy(paramParser, ","))

  // -- Function Parser -------------------------------------------- //

  val funcParser: Parsley[Func] = 
    Func(typeParser, identifierParser,"(" ~> paramListParser <~ ")", "is" ~> stmtParser <~ "end")

  // -- Program Parser --------------------------------------------- //
  val program: Parsley[Program] = Program("begin" ~> many(atomic(funcParser)), stmtParser <~ "end")

  // -- Parser ---------------------------------------------------- //
  val parser = fully(program)

  def parse(input: String): Result[String, Program] = parser.parse(input)

  // -- AST Validation -------------------------------------------- //
  // def validEndingStatement(stmts: List[Stat]): Boolean = {
  //   stmts.last match {
  //     case Return(_)          => true
  //     case Exit(_)            => true
  //     case If(_, s1, s2)      => validEndingStatement(List(s1)) && validEndingStatement(List(s2)) 
  //     case While(_, s)        => validEndingStatement(List(s))
  //     case BeginEnd(s)        => validEndingStatement(List(s))
  //     case StatJoin(stats)    => validEndingStatement(stats)
  //     case _                  => false
  //   } 
  // }

  // def validEndingStatement(stmt: Stat): Boolean = stmt match {
  //   case (StatJoin(stmts)) => validEndingStatement(stmts)
  //   case stmt              => validEndingStatement(List(stmt))
  // }
  
  // def validFunction(func: Func): Boolean = {
  //   validEndingStatement(func.stat)
  // }

  // def validFunctions(funcs: List[Func]): Boolean = {
  //   funcs.forall(func => validFunction(func))
  // }
}
