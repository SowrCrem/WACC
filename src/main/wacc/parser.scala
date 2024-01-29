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
import parsley.Parsley.{some, many}
import parsley.errors.ErrorBuilder
import parsley.combinator._
import lexer.implicits.implicitSymbol
import parsley.syntax.lift
import lexer.{integer, fully, char, implicits, ident, string}

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
  val pairLitParser: Parsley[Expr] = "null" as Null()

  // Expression Parser
  val exprParser: Parsley[Expr] = precedence(
    intParser | boolParser | charParser | stringParser | identifierParser | bracketsParser | arrayelemParser | pairLitParser
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

  val arrayType: Parsley[ArrayType] = {
    val arrayType = "[]" ~> typeParser
    arrayType.map(x => ArrayType(x))
  }
  val baseType: Parsley[BaseType] =
    intType | boolType | charType | stringType
  val pairElemTypeParser: Parsley[PairElemType] = baseType

  val pairType: Parsley[PairElemType] = {
    val pairType =
      "pair" ~> ("(" ~> pairElemTypeParser <~ ",") <~> (pairElemTypeParser <~ ")")
    pairType.map(x => PairType(x._1, x._2)) | "pair" as Null()
  }

  val typeParser: Parsley[Type] = baseType | arrayType | pairType

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
    val identAsgn = typeParser <~> identifierParser <~ "=" <~> exprParser
    identAsgn.map(x => IdentAsgn(x._1._1, x._1._2, x._2))
  }

  val asgnEqParser: Parsley[Stat] = {
    val asgnEq = exprParser <~ "=" <~> exprParser
    asgnEq.map(x => AsgnEq(x._1, x._2))
  }


  val stmtParser: Parsley[Stat] = {

    skipParser |
      identAsgnParser |
      asgnEqParser |
      readParser |
      freeParser |
      returnParser |
      exitParser |
      printParser |
      printlnParser |
      ifParser |
      whileParser |
      beginParser |
      statJoinParser
  }


  lazy val statJoinParser: Parsley[Stat] = {
    val statJoin = stmtParser <~ ";" <~> stmtParser
    statJoin.map(x => StatJoin(x._1, x._2))
  }

  private val program: Parsley[Node] = stmtParser

  val parser = fully(program)

  def parse(input: String): Result[String, Node] = parser.parse(input)

}
