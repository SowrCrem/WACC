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
    ArrayElem(Ident(lexer.ident), some("[" *> exprParser <* "]"))
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
    intParser.debug("int") | boolParser | charParser | stringParser | atomic(
      identifierParser
    ) | bracketsParser | atomic(arrayelemParser)

  // Pair Parser

  lazy val newpairParser: Parsley[Expr] = {
    val newpair =
      "newpair" ~> ("(" ~> exprParser <~ ",") <~> (exprParser <~ ")")
    newpair.map(x => NewPair(x._1, x._2))
  }
  lazy val pairLitParser: Parsley[Expr] = ("null" as Null()) | newpairParser

  // Expression Parser
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
  ).debug("exprparser")

  // Type Parser

  lazy val intType: Parsley[BaseType] = "int" as IntType()
  lazy val boolType: Parsley[BaseType] = "bool" as BoolType()
  lazy val charType: Parsley[BaseType] = "char" as CharType()
  lazy val stringType: Parsley[BaseType] = "string" as StringType()

  lazy val baseType: Parsley[BaseType] =
    intType | boolType | charType | stringType

  lazy val arrayType: Parsley[ArrayType] = {
    val arrayType = (baseType | pairType) <~ "[]"
    arrayType.map(x => ArrayType(x))
  }

  lazy val pairElemTypeParser: Parsley[PairElemType] = baseType | arrayType

  lazy val pairType: Parsley[PairType] = {
    val pairType =
      "pair" ~> ("(" ~> pairElemTypeParser <~ ",") <~> (pairElemTypeParser <~ ")")
    pairType.map(x => PairType(x._1, x._2))
  }.debug("type of pair")

  lazy val typeParser: Parsley[Type] = baseType | atomic(arrayType) | pairType

  // Statement Parser

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

  val identAsgnParser: Parsley[Stat] = {
    val identAsgn = typeParser.debug("typeparsing") <~> atomic(
      identifierParser.debug("identifiername")
    ) <~ "=" <~> exprParser
    identAsgn.map(x => IdentAsgn(x._1._1, x._1._2, x._2))
  }

  val asgnEqParser: Parsley[Stat] = {
    val asgnEq = exprParser <~ "=" <~> exprParser
    asgnEq.map(x => AsgnEq(x._1, x._2))
  }

  val statAtoms: Parsley[Stat] = {
    skipParser | identAsgnParser.debug("ident") | asgnEqParser.debug("asgneq") |
      readParser.debug("read") | freeParser.debug("free") | returnParser.debug(
        "return"
      ) |
      exitParser.debug("exit") | printParser.debug(
        "print"
      ) | printlnParser |
      ifParser.debug("if") | whileParser.debug("while") | beginParser.debug(
        "begin"
      )

  }

  val statJoinParser: Parsley[Stat] = StatJoin(sepBy1(statAtoms, ";"))

  val stmtParser: Parsley[Stat] =
    atomic((atomic(statAtoms) <~ notFollowedBy(";"))) | statJoinParser

  // Param Parser

  val paramParser: Parsley[Param] = {
    val param = typeParser <~> ident.map(x => Ident(x))
    param.map(x => Param(x._1, x._2))
  }

  val paramListParser: Parsley[ParamList] =
    ParamList(sepBy(paramParser, ","))

  // Function Parser

  val funcParser: Parsley[Func] = Func(
    typeParser,
    ident.map(x => Ident(x)),
    "(" ~> paramListParser <~ ")",
    "is" ~> stmtParser <~ "end"
  )

  val program: Parsley[Node] =
    Program("begin" ~> many(atomic(funcParser)), stmtParser <~ "end")

  val parser = fully(program)

  def parse(input: String): Result[String, Node] = parser.parse(input)

}
