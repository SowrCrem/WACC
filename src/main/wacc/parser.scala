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
      atomic(classFieldParser) | identifierParser | bracketsParser

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
    
    /* EXTENSION - Bitwise Operators */
    Ops(Prefix)(BitNot <# "~"),
    
    Ops(InfixL)(Mul <# "*"),
    Ops(InfixL)(Div <# "/"),
    Ops(InfixL)(Mod <# "%"),
    Ops(InfixL)(Plus <# "+"),
    Ops(InfixL)(Minus <# "-"),

    /* EXTENSION - Bitwise Operators */
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

  /* EXTENSION - Void Types */
  lazy val voidTypeParser: Parsley[VoidTypeNode] = VoidTypeNode <# "void"

  /* EXTENSION - Simple Classes */
  lazy val classTypeParser: Parsley[ClassTypeNode] = ClassTypeNode("object" ~> identifierParser)

  lazy val typeParser: Parsley[TypeNode] =
    atomic(arrayTypeParser) | baseTypeParser | pairTypeParser | 
      voidTypeParser | classTypeParser

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

  val callParser: Parsley[Call] = 
    Call("call" ~> identifierParser, "(" ~> sepBy(exprParser,",") <~ ")")

  /* EXTENSION - Simple Classes */
  val newClassParser: Parsley[Expr] = 
    NewClass("new" ~> identifierParser, "(" ~> sepBy(exprParser,",") <~ ")")

  val classCallParser: Parsley[Expr] =
    ClassCall("call" ~> identifierParser <~ ".",
              Call(identifierParser, "(" ~> sepBy(exprParser,",") <~ ")"))

  val classFieldParser: Parsley[Expr] = 
    ClassField(identifierParser, "." ~> identifierParser)

  val assignRhs = {
    val assignRhs = exprParser | pairLitParser | 
      newClassParser | atomic(classCallParser) | callParser
    assignRhs
  }

  val identAsgnParser: Parsley[IdentAsgn] = 
    IdentAsgn(typeParser, identifierParser, "=" ~> assignRhs)

  val assignLhs = {
    val assignLhs = atomic(arrayelemParser) | pairLitParser |
      atomic(classFieldParser) | identifierParser
    assignLhs
  }

  val readParser: Parsley[Stat] = "read" ~> Read(assignLhs)

  val asgnEqParser: Parsley[Stat] = {
    AsgnEq(assignLhs, "=" ~> assignRhs)
  }

  lazy val lazyStatParser : Parsley[Stat] = {
    val lazyStat = LazyStat("lazy" ~> statAtoms)
    lazyStat
  }

  /* EXTENSION - Void Types */
  val callVoidParser: Parsley[Stat] = 
    CallVoid("call" ~> identifierParser, "(" ~> sepBy(exprParser,",") <~ ")")

  /* EXTENSION - Simple Classes */
  val classCallVoidParser: Parsley[Stat] =
    ClassCallVoid("call" ~> identifierParser <~ ".",
                  CallVoid(identifierParser, "(" ~> sepBy(exprParser,",") <~ ")"))
  val exceptionParser : Parsley[ExceptionType] = 
    ExceptionType("Exception" ~> ":" ~> lexer.exceptionName)
  
  val catchParser : Parsley[CatchStmt] = 
    CatchStmt("catch" ~> "(" ~> exceptionParser <~ ")", "{" ~> stmtParser <~ "}")

  val tryCatchParser : Parsley[Stat] = 
    TryCatchStat("try" ~> "{" ~> stmtParser <~ "}", many(catchParser))

  val statAtoms: Parsley[Stat] = {
    skipParser | tryCatchParser | identAsgnParser |lazyStatParser|  asgnEqParser |
      readParser | freeParser | returnParser |
      exitParser | printParser | printlnParser |
      ifParser | whileParser | beginParser | 
        atomic(classCallVoidParser) |  callVoidParser  
  }


  val stmtParser: Parsley[List[Stat]] = sepBy1(statAtoms, ";")

  // -- Param Parser ----------------------------------------------- //

  val paramParser: Parsley[Param] = Param(typeParser, identifierParser)

  val paramListParser: Parsley[ParamList] =
    ParamList(sepBy(paramParser, ","))

  // -- Function Parser -------------------------------------------- //

  val funcParser: Parsley[Func] = 
    Func(typeParser, 
         identifierParser, 
         "(" ~> paramListParser <~ ")", 
         "is" ~> stmtParser <~ "end")

  /* EXTENSION - Simple Classes */
  // -- Class Parser ---------------------------------------------- //

  val accessModParser: Parsley[AccessMod] = 
    Public <# "public" | Private <# "private"

  val classFuncParser: Parsley[ClassFunc] = 
    ClassFunc(accessModParser, funcParser)

  val constructorParser: Parsley[Constructor] = 
    Constructor(identifierParser, 
                "(" ~> paramListParser <~ ")", 
                "is" ~> stmtParser <~ "end")

  val fieldDeclParser: Parsley[FieldDecl] = 
    FieldDecl(accessModParser, typeParser, identifierParser)

  val fieldDeclListParser: Parsley[List[FieldDecl]] = 
    sepBy(fieldDeclParser, ";")

  val classParser: Parsley[Class] = 
    Class("class" ~> identifierParser, 
          "is" ~> fieldDeclListParser, 
          "constructor" ~> constructorParser,
          many(classFuncParser) <~ "end")

  // -- Program Parser --------------------------------------------- //
  val program: Parsley[Program] = 
    Program("begin" ~> atomic(many(classParser)), 
            many(atomic(funcParser)), 
            stmtParser <~ "end")

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

  def validFunction(func: Func): Boolean =
    func match {
      case Func(funcType, _, _, stmts) => {
        funcType match {
          case VoidTypeNode() => noReturnStatements(stmts)
          case _              => validEndingStatement(stmts)
        }
      }
    }

  def validFunctions(classes: List[Class], funcs: List[Func]): Boolean = {
    val classesValidity = classes.forall(cls =>
      cls match {
        case Class(_, _, constr@Constructor(_, _, stmts), classFuncs) => {
          noReturnStatements(stmts) &&
          classFuncs.forall(classFunc =>
            classFunc match {
              case ClassFunc(_, func) => 
                validFunction(func) 
            }
          )
        }
      }
    )

    classesValidity && funcs.forall(func => validFunction(func))
  }
}
