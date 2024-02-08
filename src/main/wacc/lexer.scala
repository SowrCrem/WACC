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


object lexer {

  val escLiterals = Set('0', '\\', '"', 'b', 't', 'n', 'f', 'r', '\'')

  private val errorConfig = new ErrorConfig {
    override def labelSymbol = Map(
      ")" -> LabelAndReason(
        reason="unclosed braces", 
        label="closing braces"
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
        label="statement"
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
    )
  }

  private val desc = LexicalDesc.plain.copy(
    nameDesc = NameDesc.plain.copy(
      identifierStart = predicate.Basic(c => c.isLetter || c == '_'),
      identifierLetter = predicate.Basic(c => c.isLetterOrDigit || c == '_')
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
        literals = escLiterals
        // mapping = Map("0" -> '\u0000')
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

val builder = new WaccErrorBuilder with LexToken {
    def tokens = Seq(
        lexer.nonlexeme.integer.decimal.map(n => s"integer $n"),
        lexer.nonlexeme.names.identifier.map(v => s"identifier $v")
    ) ++ desc.symbolDesc.hardKeywords.map { k =>
        lexer.nonlexeme.symbol(k).as(s"keyword $k")
    }
}

  private val lexer = new Lexer(desc, errorConfig)
  val integer = lexer.lexeme.integer.number32
  val implicits = lexer.lexeme.symbol.implicits



  
  private val escapeChar: Parsley[Char] = {
    implicits.implicitSymbol("0").as('\u0000') |  
    implicits.implicitSymbol("\\").as('\\')
  }
  
  val char: Parsley[Char] = lexer.lexeme.character.ascii
  val string: Parsley[String] = lexer.lexeme.string.ascii
  val ident: Parsley[String] = lexer.lexeme.names.identifier

  def fully[A](p: Parsley[A]): Parsley[A] = lexer.fully(p)
}
