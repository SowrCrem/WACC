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

object parser {

  
  lazy val arrayelemParser: Parsley[Expr] =
    ArrayElem(Ident(lexer.ident), some("[" ~> exprParser <~ "]"))
  lazy val intParser: Parsley[Expr] = integer.map(n => {
    if (n.isValidInt) IntLiter(n.toInt) else Error("Integer too large")
  })
  lazy val boolParser: Parsley[Expr] =
    ("true" as BoolLiter(true)) | ("false" as BoolLiter(false))
  lazy val charParser: Parsley[Expr] = CharLiter(lexer.char)
  lazy val stringParser: Parsley[Expr] = StringLiter(lexer.string)
  lazy val identifierParser: Parsley[Expr] = Ident(lexer.ident)
  lazy val bracketsParser: Parsley[Expr] = Brackets("(" ~> exprParser <~ ")")

  lazy val atoms =
    intParser | boolParser | charParser | stringParser |
      identifierParser | bracketsParser | arrayelemParser

  // -- Pair Parser ----------------------------------------------- //
 
  lazy val newpairParser: Parsley[Expr] = {
    val newpair =
      "newpair" ~> ("(" ~> exprParser <~ ",") <~> (exprParser <~ ")")
    newpair.map(x => NewPair(x._1, x._2))
  }

  lazy val pairLitParser: Parsley[Expr] = ("null" as Null()) | newpairParser

  // -- Expression Parsers ----------------------------------------- //

  lazy val exprParser: Parsley[Expr] = precedence(
    atoms | pairLitParser
  )(
    Ops(Prefix)(Not from "!"),
    Ops(Prefix)(Neg from "-"),
    Ops(Prefix)(Len from "len"),
    Ops(Prefix)(Ord from "ord"),
    Ops(Prefix)(Chr from "chr"),
    Ops(InfixL)(Mul from "*"),
    Ops(InfixL)(Div from "/"),
    Ops(InfixL)(Mod from "%"),
    Ops(InfixL)(Plus from "+"),
    Ops(InfixL)(Minus from "-"),
    Ops(InfixN)(GreaterThan from ">"),
    Ops(InfixN)(GreaterThanEq from ">="),
    Ops(InfixN)(LessThan from "<"),
    Ops(InfixN)(LessThanEq from "<="),
    Ops(InfixN)(Equals from "=="),
    Ops(InfixN)(NotEquals from "!="),
    Ops(InfixR)(And from "&&"),
    Ops(InfixR)(Or from "||")
  )

  val arrayLiteralParser: Parsley[Expr] = {
    val arrayLiteral = "[" ~> sepBy(exprParser, ",") <~ "]"
    arrayLiteral.map(x => ArrayLiter(x))
  }

  // -- Type Parsers ---------------------------------------------- //

  lazy val intType: Parsley[BaseTypeNode] = "int" as IntTypeNode()
  lazy val boolType: Parsley[BaseTypeNode] = "bool" as BoolTypeNode()
  lazy val charType: Parsley[BaseTypeNode] = "char" as CharTypeNode()
  lazy val stringType: Parsley[BaseTypeNode] = "string" as StringTypeNode()

  lazy val baseType: Parsley[BaseTypeNode] =
    intType | boolType | charType | stringType

  lazy val arrayType: Parsley[ArrayTypeNode] = {
    val arrayType = (baseType | pairType) <~ "[]"
    arrayType.map(x => ArrayTypeNode(x))
  }

  lazy val pairElemTypeParser: Parsley[PairElemTypeNode] = baseType | arrayType

  lazy val pairType: Parsley[PairTypeNode] = {
    val pairType =
      "pair" ~> ("(" ~> pairElemTypeParser <~ ",") <~> (pairElemTypeParser <~ ")")
    pairType.map(x => PairTypeNode(x._1, x._2))
  }

  lazy val typeParser: Parsley[TypeNode] = baseType | atomic(arrayType) | pairType

  // -- Statement Parsers ----------------------------------------- //

  val ifParser: Parsley[Stat] = {
    val ifStmt =
      "if" ~> exprParser <~ "then" <~> (stmtParser <~> "else" ~> stmtParser <~ "fi")
    ifStmt.map(x => If(x._1, x._2._1, x._2._2))
  }

  val whileParser: Parsley[Stat] = {
    val whileStmt = "while" ~> exprParser <~ "do" <~> (stmtParser <~ "done")
    whileStmt.map(x => While(x._1, x._2))
  }

  val skipParser: Parsley[Stat] = "skip" as Skip()

  val readParser: Parsley[Stat] = "read" ~> ident.map(x => Read(Ident(x)))

  val freeParser: Parsley[Stat] = "free" ~> exprParser.map(x => Free(x))

  val beginParser: Parsley[Stat] = "begin" ~> stmtParser <~ "end"

  val returnParser: Parsley[Stat] = "return" ~> exprParser.map(x => Return(x))

  val exitParser: Parsley[Stat] = "exit" ~> exprParser.map(x => Exit(x))

  val printParser: Parsley[Stat] = "print" ~> exprParser.map(x => Print(x))

  val printlnParser: Parsley[Stat] =
    "println" ~> exprParser.map(x => Println(x))

  val callParser: Parsley[Stat] = {
    val call = "call" ~> ident.map(x => Ident(x)) <~ "(" <~> sepBy(
      exprParser,
      ","
    ) <~ ")"
    call.map(x => Call(x._1, ArgList(x._2)))
  }

  val assignRhs = {
    val assignRhs = arrayLiteralParser | pairLitParser | callParser | exprParser
    assignRhs
  }

  val identAsgnParser: Parsley[Stat] = {
    val identAsgn = typeParser <~> atomic(
      identifierParser
    ) <~ "=" <~> assignRhs
    identAsgn.map(x => IdentAsgn(x._1._1, x._1._2, x._2))
  }

  val assignLhs = {
    val assignLhs = arrayelemParser | identifierParser
    assignLhs
  }

  val asgnEqParser: Parsley[Stat] = {
    val asgnEq = assignLhs <~ "=" <~> assignRhs
    asgnEq.map(x => AsgnEq(x._1, x._2))
  }

  val statAtoms: Parsley[Stat] = {
    skipParser | identAsgnParser | asgnEqParser |
      readParser | freeParser | returnParser |
      exitParser | printParser | printlnParser |
      ifParser | whileParser | beginParser

  }

  val statJoinParser: Parsley[Stat] = StatJoin(sepBy1(statAtoms, ";"))

  val stmtParser: Parsley[Stat] =
    (atomic(statAtoms) <~ notFollowedBy(";")) | statJoinParser

  // -- Param Parser ----------------------------------------------- //

  val paramParser: Parsley[Param] = {
    val param = typeParser <~> ident.map(x => Ident(x))
    param.map(x => Param(x._1, x._2))
  }

  val paramListParser: Parsley[ParamList] =
    ParamList(sepBy(paramParser, ","))

  // -- Function Parser -------------------------------------------- //

  val funcParser: Parsley[Func] = Func(
    typeParser,
    ident.map(x => Ident(x)),
    "(" ~> paramListParser <~ ")",
    "is" ~> stmtParser <~ "end"
  )

  // -- Program Parser --------------------------------------------- //
  val program: Parsley[Node] =
    Program("begin" ~> many(atomic(funcParser)), stmtParser <~ "end")


  // -- Parser ---------------------------------------------------- //
  val parser = fully(program)

  def parse(input: String): Result[String, Node] = parser.parse(input)

}
