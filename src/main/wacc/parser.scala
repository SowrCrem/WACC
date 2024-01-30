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
object parser {

  val arrayelemParser: Parsley[Expr] =
    ArrayElem(Ident(lexer.ident), some("[" *> exprParser <* "]"))
  val intParser: Parsley[Expr] = integer.map(n => {
    if (n.isValidInt) IntLiter(n.toInt) else Error("Integer too large")
  })
  val boolParser: Parsley[Expr] =
    ("true" as BoolLiter(true)) | ("false" as BoolLiter(false))
  val charParser: Parsley[Expr] = CharLiter(lexer.char)
  val stringParser: Parsley[Expr] = StringLiter(lexer.string)
  val identifierParser: Parsley[Expr] = Ident(lexer.ident)
  val bracketsParser: Parsley[Expr] = Brackets("(" ~> exprParser <~ ")")

  val atoms = intParser | boolParser | charParser | stringParser | identifierParser | bracketsParser | arrayelemParser

  // Pair Parser

  val newpairParser: Parsley[Expr] = {
    val newpair = "newpair" ~> ("(" ~> exprParser <~ ",") <~> (exprParser <~ ")")
    newpair.map(x => NewPair(x._1, x._2))
  }
  val pairLitParser: Parsley[Expr] = ("null" as Null()) | newpairParser

  // Expression Parser
  val exprParser: Parsley[Expr] = precedence(
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

  // Type Parser

  val intType: Parsley[BaseType] = "int" as IntType()
  val boolType: Parsley[BaseType] = "bool" as BoolType()
  val charType: Parsley[BaseType] = "char" as CharType()
  val stringType: Parsley[BaseType] = "string" as StringType()

  val baseType: Parsley[BaseType] = intType | boolType | charType | stringType


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
    val identAsgn = typeParser.debug("typeparsing") <~> identifierParser <~ "=" <~> exprParser
    identAsgn.map(x => IdentAsgn(x._1._1, x._1._2, x._2))
  }

  val asgnEqParser: Parsley[Stat] = {
    val asgnEq = exprParser <~ "=" <~> exprParser
    asgnEq.map(x => AsgnEq(x._1, x._2))
  }

  val statAtoms: Parsley[Stat] = {
    skipParser | identAsgnParser.debug("ident") | asgnEqParser |
      readParser | freeParser | returnParser |
      exitParser | printParser | printlnParser |
      ifParser | whileParser | beginParser

  }

  val statJoinParser: Parsley[Stat] = StatJoin(sepBy1(statAtoms, ";"))

  val stmtParser: Parsley[Stat] =
    atomic((statAtoms <~ notFollowedBy(";"))) | statJoinParser

  private val program: Parsley[Node] = stmtParser

  val parser = fully(program)

  def parse(input: String): Result[String, Node] = parser.parse(input)

}
