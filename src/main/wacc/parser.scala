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

object parser {

  lazy val arrayelemParser: Parsley[Expr] =
    ArrayElem(Ident(lexer.ident), some("[" ~> exprParser <~ "]"))
  lazy val intParser: Parsley[Expr] = IntLiter(lexer.integer)
  lazy val boolParser: Parsley[Expr] = BoolLiter(lexer.bool)
  lazy val charParser: Parsley[Expr] = CharLiter(lexer.char)
  lazy val stringParser: Parsley[Expr] = StringLiter(lexer.string)
  lazy val identifierParser: Parsley[Ident] = Ident(lexer.ident)
  lazy val bracketsParser: Parsley[Expr] = Brackets("(" ~> exprParser <~ ")")
  lazy val digit = oneOf('0' to '9')

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
    Ops(Prefix)(BitNot <# "~"),
    Ops(InfixL)(Mul <# "*"),
    Ops(InfixL)(Div <# "/"),
    Ops(InfixL)(Mod <# "%"),
    Ops(InfixL)(Plus <# "+"),
    Ops(InfixL)(Minus <# "-"),
    Ops(InfixL)(BitAnd <# "&"),
    Ops(InfixL)(BitOr <# "|"),
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

  lazy val baseTypeParser: Parsley[BaseTypeNode] =
    IntTypeNode <# "int"| BoolTypeNode <# "bool" | CharTypeNode <# "char" | 
    StringTypeNode <# "string"

  lazy val arrayTypeParser: Parsley[ArrayTypeNode] = 
    chain.postfix1(baseTypeParser <|> pairTypeParser)(ArrayTypeNode <# ("[" <~> "]"))

  lazy val pairElemTypeParser: Parsley[PairElemTypeNode] =
    atomic(arrayTypeParser) | baseTypeParser | Null <# "pair"

  lazy val pairTypeParser: Parsley[PairTypeNode] = 
    PairTypeNode("pair" ~> "(" ~> pairElemTypeParser <~ ",", pairElemTypeParser <~ ")")
    
  lazy val voidTypeParser: Parsley[VoidTypeNode] = 
    VoidTypeNode <# "void"

  lazy val typeParser: Parsley[TypeNode] =
    atomic(arrayTypeParser) | baseTypeParser | pairTypeParser | voidTypeParser

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

  val callVoidParser: Parsley[Stat] = 
    CallVoid("call" ~> identifierParser, "(" ~> sepBy(exprParser,",") <~ ")")

  val statAtoms: Parsley[Stat] = {
    skipParser | identAsgnParser | asgnEqParser |
      readParser | freeParser | returnParser |
      exitParser | printParser | printlnParser |
      ifParser | whileParser | beginParser |
      callVoidParser
  }

  val stmtParser: Parsley[List[Stat]] = sepBy1(statAtoms, ";")

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
  
  def noReturnStatements(stmts: List[Stat]): Boolean = 
    stmts.forall(stmt => stmt match {
      case Return(_)     => false
      case If(_, s1, s2) => noReturnStatements(s1) && noReturnStatements(s2)
      case While(_, s)   => noReturnStatements(s)
      case BeginEnd(s)   => noReturnStatements(s)
      case _             => true
    })

  def validEndingStatement(stmts: List[Stat]): Boolean = {
    stmts.last match {
      case Return(_)          => true
      case Exit(_)            => true
      case If(_, s1, s2)      => validEndingStatement(s1) && validEndingStatement(s2) 
      case While(_, s)        => validEndingStatement(s)
      case BeginEnd(s)        => validEndingStatement(s)
      case _                  => false
    } 
  }

  def validFunctions(funcs: List[Func]): Boolean = {
    funcs.forall(func => 
      func match {
        case Func(funcType, _, _, stmts) => {
          funcType match {
            case VoidTypeNode() => noReturnStatements(stmts)
            case _              => validEndingStatement(stmts)
          }
        }
      }
    )
  }
}
