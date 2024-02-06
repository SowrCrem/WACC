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

object lexer {

  val escLiterals = Set('0', '\\', '"', 'b', 't', 'n', 'f', 'r', '\'')

  private val errorConfig = new ErrorConfig {
    override def labelSymbol = Map(
      ")" -> LabelAndReason(
        reason="unclosed braces", 
        label="closing braces"
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
      "ord" -> Label(
        label="unary operator"
      ),
      "chr" -> Label(
        label="unary operator"
      ),
      "len" -> Label(
        label="unary operator"
      )
    )
  }

  private val desc = LexicalDesc.plain.copy(
    nameDesc = NameDesc.plain.copy(
      identifierStart = predicate.Basic(c=> c.isLetter || c == '_'),
      identifierLetter = predicate.Basic(c=> c.isLetterOrDigit || c == '_')
    ),
    numericDesc = NumericDesc.plain.copy(
      positiveSign = PlusSignPresence.Optional
    ),
    spaceDesc = SpaceDesc.plain.copy(
      lineCommentStart = "#"
    ),
    textDesc = TextDesc.plain.copy(
      escapeSequences = EscapeDesc.plain.copy(
        escBegin = '\\',
        literals = escLiterals,
        // mapping = Map("0" -> '\u0000',
        //               "b" -> '\b',
        //               "t" -> '\t',
        //               "n" -> '\n',
        //               "f" -> '\f',
        //               "r" -> '\r',
        //               "\"" -> '\"',
        //               "'" -> '\'',
        //               "\\" -> '\\')
      )
    ),
    symbolDesc = SymbolDesc.plain.copy(
      hardKeywords = Set(
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
        "||"
      )
    )
  )
  private val lexer = new Lexer(desc, errorConfig)
  val integer = lexer.lexeme.integer.number32
  val implicits = lexer.lexeme.symbol.implicits
  val char: Parsley[Char] = lexer.lexeme.character.ascii
  val string: Parsley[String] = lexer.lexeme.string.ascii
  val ident: Parsley[String] = lexer.lexeme.names.identifier

  def fully[A](p: Parsley[A]): Parsley[A] = lexer.fully(p)
}
