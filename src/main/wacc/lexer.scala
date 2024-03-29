package wacc

import parsley.Parsley
import parsley.token.{Lexer, predicate}
import parsley.token.descriptions._
import parsley.token.errors._
import parsley.token.numeric._
import parsley.token.symbol._
import parsley.character.{noneOf, string}
import parsley.token.descriptions.numeric.NumericDesc
import parsley.token.descriptions.numeric.PlusSignPresence
import parsley.combinator._
import parsley.syntax._
import parsley.token.descriptions.text.TextDesc
import parsley.token.descriptions.text.EscapeDesc
import parsley.errors.Token
import parsley.errors.tokenextractors._
import parsley.token.predicate.Unicode

object lexer {

  private val escapeLiterals = Set('\\', '"', '\'')

  private val errorConfig = new ErrorConfig {
    override def labelSymbol = Map(
      ")" -> LabelAndReason(
        reason="unclosed braces", 
        label="closing braces"
      ),
      "(" -> Label(
        label="opening braces"
      ),
      "[" -> Label(
        label="opening braces"
      ),
      "\"" -> Label(
        label="quotation marks"
      ),
      "'" -> Label(
        label="quotation marks"
      ),
      "]" -> LabelAndReason(
        reason="unclosed braces", 
        label="closing braces"
      ),
      "[" -> LabelAndReason(
        reason="unclosed braces", 
        label="expected start of array"
      ),
      ">=" -> Label(
        label="comparison operator"
      ),
      "<=" -> Label(
        label="comparison operator"
      ),
      "<" -> Label(
        label="comparison operator"
      ),
      ">" -> Label(
        label="comparison operator"
      ),
      "==" -> Label(
        label="comparison operator"
      ),
      "!=" -> Label(
        label="comparison operator"
      ),
      "&&" -> Label(
        label="logical operator"
      ),
      "||" -> Label(
        label="logical operator"
      ),
      "!" -> Label(
        label="logical operator"
      ),
      "+" -> Label(
        label="arithmetic operator"
      ),
      "-" -> Label(
        label="arithmetic operator"
      ),
      "*" -> Label(
        label="arithmetic operator"
      ),
      "/" -> Label(
        label="arithmetic operator"
      ),
      "%" -> Label(
        label="arithmetic operator"
      ),

      /* EXTENSION - Bitwise Operators */
      "&" -> Label(
        label="bitwise operator"
      ),
      "|" -> Label(
        label="bitwise operator"
      ),
      "~" -> Label(
        label="bitwise operator"
      ),

      "ord" -> Label(
        label="unary operator"
      ),
      "chr" -> Label(
        label="unary operator"
      ),
      "len" -> Label(
        label="unary operator"
      ),
      "false" -> Label(
        label="boolean"
      ),
      "true" -> Label(
        label="boolean"
      ),
      ";" -> Label(
        label="semicolon"
      ),

      /* EXTENSION - Simple Classes */
      "class" -> Label(
        label="class"
      ),
      "constructor" -> Label(
        label="constructor"
      ),
      "new" -> Label(
        label="new"
      ),
      "public" -> Label(
        label="access modifier"
      ),
      "private" -> Label(
        label="access modifier"
      ),

      /* EXTENSION - Lazy Evaluation */
      "lazy" -> Label(
        label="lazy"
      ),

      /* EXTENSION - Void Types */
      "void" -> Label(
        label="type"
      ),
      
      "int" -> Label(
        label="type"
      ),
      "bool" -> Label(
        label="type"
      ),
      "char" -> Label(
        label="type"
      ),
      "string" -> Label(
        label="type"
      ),
      "pair" -> Label(
        label="type"
      ),
      "skip" -> Label(
        label="statement"
      ),
      "read" -> Label(
        label="statement"
      ),
      "free" -> Label(
        label="statement"
      ),
      "return" -> Label(
        label="statement"
      ),
      "exit" -> Label(
        label="exit statement"
      ),
      "print" -> Label(
        label="statement"
      ),
      "println" -> Label(
        label="statement"
      ),
      "while" -> Label(
        label="while loopS"
      ),
      "fst" -> Label(
        label="pair operator"
      ),
      "snd" -> Label(
        label="pair operator"
      ),
      "newpair" -> Label(
        label="pair operator"
      ),
      "call" -> Label(
        label="function call"
      ),
      "begin" -> Label(
        label="begin"
      ),
      "end" -> Label(
        label="end"
      ),
      "null" -> Label(
        label="null"
      ),
      /* EXTENSION - Lazy Evaluation */
      "lazy" -> Label(
        label="lazy declaration"
      ),
      /* EXTENSION - Exceptions */
      "try" -> Label(
        label="try block"
      ),
      "catch" -> Label(
        label="catch block"
      )
    )
  }

  private val desc = LexicalDesc.plain.copy(
    nameDesc = NameDesc.plain.copy(
      identifierStart = predicate.Basic(c => c.isLetter || c == '_'),
      identifierLetter = predicate.Basic(c => c.isLetterOrDigit || c == '_')
    ),
    numericDesc = NumericDesc.plain.copy(
      leadingZerosAllowed = true,
      integerNumbersCanBeHexadecimal = false,
      integerNumbersCanBeOctal = false,
      integerNumbersCanBeBinary = false
    ),
    spaceDesc = SpaceDesc.plain.copy(
      lineCommentStart = "#"
    ),
    textDesc = TextDesc.plain.copy(
      escapeSequences = EscapeDesc.plain.copy(
        escBegin = '\\',
        literals = Set('\\', '"', '\''),
        mapping = Map(
          "0" -> 0x00,
          "b" -> 0x08,
          "t" -> 0x09,
          "n" -> 0x0A,
          "f" -> 0x0C,
          "r" -> 0x0D
        )
      ),
      graphicCharacter = Unicode(c => c >= ' '.toInt && !escapeLiterals.contains(c.toChar)),
      characterLiteralEnd = '\'',
      stringEnds = Set(("\"", "\""))
    ),
    symbolDesc = SymbolDesc.plain.copy(
      hardKeywords = Set(
        /* EXTENSION - Void Types */
        "void",

        /* EXTENSION - Simple Classes */
        "class",
        "constructor",
        "new",
        "public",
        "private",

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
      ),
      hardOperators = Set(
        "*",
        "/",
        "+",
        "-",
        "!",
        "ord",
        "len",
        "chr",
        "%",
        ">",
        ">=",
        "<",
        "<=",
        "==",
        "!=",
        "&&",
        "||",

        /* EXTENSION - Bitwise Operators */
        "&",
        "|",
        "~",
      ),
      caseSensitive = true
    )
  )

val builder = new WaccErrorBuilder with LexToken {
    def tokens = Seq(
        lexer.nonlexeme.integer.decimal.map(n => s"integer $n"),
        lexer.nonlexeme.names.identifier.map(v => s"identifier $v")
    ) ++ desc.symbolDesc.hardKeywords.map { k =>
        lexer.nonlexeme.symbol(k).as(s"keyword $k")
    }
}

  private val lexer = new Lexer(desc, errorConfig)
  val integer: Parsley[Int] = lexer.lexeme.integer.decimal32
  val implicits = lexer.lexeme.symbol.implicits

  private val escapeChar: Parsley[Char] = {
    implicits.implicitSymbol("0").as('\u0000') |  
    implicits.implicitSymbol("\\").as('\\')
  }
  
  val char: Parsley[Char] = lexer.lexeme.character.ascii
  val string: Parsley[String] = lexer.lexeme.string.ascii
  val ident: Parsley[String] = lexer.lexeme.names.identifier
  val exceptionName: Parsley[String] = lexer.lexeme.names.identifier
  val bool: Parsley[Boolean] = 
    lexer.lexeme.symbol("true").as(true) | 
    lexer.lexeme.symbol("false").as(false)

  def fully[A](p: Parsley[A]): Parsley[A] = lexer.fully(p)
}
