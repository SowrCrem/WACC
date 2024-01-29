package wacc
import parsley.Parsley
import parsley.token.{Lexer, predicate}
import parsley.token.descriptions._
import parsley.token.errors._
import parsley.token.numeric._
import parsley.token.symbol._
import parsley.character.{noneOf, string}

object lexer {
  private val desc = LexicalDesc.plain.copy(
    nameDesc = NameDesc.plain.copy(
      identifierStart = predicate.Basic(_.isLetter),
      identifierLetter = predicate.Basic(_.isLetterOrDigit)
    ),
    symbolDesc = SymbolDesc.plain.copy(
      hardKeywords = Set(
        "len",
        "ord",
        "chr",
        "int",
        "bool",
        "char",
        "string",
        "pair",
        "skip",
        "read",
        "free",
        "return",
        "exit",
        "print",
        "println",
        "if",
        "then",
        "else",
        "fi",
        "while",
        "do",
        "done",
        "begin",
        "end",
        "newpair",
        "call",
        "fst",
        "snd",
        "true",
        "false",
        "null"
      )
    )
  )
  private val lexer = new Lexer(desc)


  val integer = lexer.lexeme.integer.decimal
  val implicits = lexer.lexeme.symbol.implicits
  val char: Parsley[Char] = lexer.lexeme.character.ascii
  val string: Parsley[String] = lexer.lexeme.string.ascii
  val ident: Parsley[String] = lexer.lexeme.names.identifier

  def fully[A](p: Parsley[A]): Parsley[A] = lexer.fully(p)
}
