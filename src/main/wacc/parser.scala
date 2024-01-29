package wacc
import parsley.{Parsley, Result}
import parsley.expr.{chain, precedence, Ops, InfixL, Atoms, Prefix, InfixN, InfixR}
import parsley.Parsley.{some, many}
import parsley.errors.ErrorBuilder
import parsley.combinator._
import lexer.implicits.implicitSymbol
import lexer.{integer, fully, char, implicits, ident, string}

object parser {
  // def parse(input: String): Result[String, Node] = parser.parse(input)
  val parser = fully(program)

  private lazy val program: Parsley[Node] = expr

  private val add = (x: BigInt, y: BigInt) => x + y
  private val sub = (x: BigInt, y: BigInt) => x - y

  // Atom Parsers
  val arrayelemParser: Parsley[Expr] =
    ArrayElem(Ident(lexer.ident), some("[" *> expr <* "]"))
  val intParser: Parsley[Expr] = integer.map(n => {
    if (n.isValidInt) IntLiter(n.toInt) else Error("Integer too large")
  })
  val boolParser: Parsley[Expr] =
    ("true" as BoolLiter(true)) | ("false" as BoolLiter(false))
  val charParser: Parsley[Expr] = CharLiter(lexer.char)
  val stringParser: Parsley[Expr] = StringLiter(lexer.string)
  val identifierParser: Parsley[Expr] = Ident(lexer.ident)
  val bracketsParser: Parsley[Expr] = Brackets("(" ~> expr <~ ")")
  val pairLitParser: Parsley[Expr] = "null" as Null()
  val expr: Parsley[Expr] = precedence(
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


}
