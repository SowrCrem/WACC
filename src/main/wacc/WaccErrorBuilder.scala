package wacc
import parsley.errors.ErrorBuilder
import parsley.errors.Token
import parsley.errors.tokenextractors._

case class WaccError(pos: (Int, Int), lines: WaccErrorLines)

sealed trait WaccErrorLines
case class VanillaError(
    unexpected: Option[WaccErrorItem],
    expecteds: Set[WaccErrorItem],
    reasons: Set[String]) extends WaccErrorLines
case class SpecializedError(msgs: Set[String]) extends WaccErrorLines

sealed trait WaccErrorItem
case class WaccRaw(item: String) extends WaccErrorItem
case class WaccNamed(item: String) extends WaccErrorItem
case object WaccEndOfInput extends WaccErrorItem

abstract class WaccErrorBuilder extends ErrorBuilder[WaccError] {

    type Position = (Int, Int)
    type Source = Unit
    type ErrorInfoLines = WaccErrorLines
    type Item = WaccErrorItem
    type Raw = WaccRaw
    type Named = WaccNamed
    type EndOfInput = WaccEndOfInput.type
    type Message = String
    type Messages = Set[String]
    type ExpectedItems = Set[WaccErrorItem]
    type ExpectedLine = Set[WaccErrorItem]
    type UnexpectedLine = Option[WaccErrorItem]
    type LineInfo = Unit

    def format(pos: (Int, Int), source: Unit, lines: WaccErrorLines): WaccError = WaccError((0, 0), SpecializedError(Set("hello world")))
    def vanillaError (
        unexpected: Option[WaccErrorItem], 
        expected: Set[WaccErrorItem], reasons: Set[String], 
        line: Unit
    ): WaccErrorLines = VanillaError(unexpected, expected, reasons)
    def specializedError(
        msgs: Set[String],
        line: Unit
    ): WaccErrorLines = SpecializedError(msgs)
        def pos(line: Int, col: Int): (Int, Int) = (line, col)
    def source(sourceName: Option[String]): Unit = ()
    def combineExpectedItems(alts: Set[WaccErrorItem]): Set[WaccErrorItem] = alts
    def combineMessages(alts: Seq[String]): Set[String] = alts.toSet
    def unexpected(item: Option[WaccErrorItem]): Option[WaccErrorItem] = item
    def expected(alts: Set[WaccErrorItem]): Set[WaccErrorItem] = alts
    def message(msg: String): String = msg
    def reason(msg: String): String = msg
    def raw(item: String): WaccRaw = WaccRaw(item)
    def named(item: String): WaccNamed = WaccNamed(item)
    val endOfInput: WaccEndOfInput.type = WaccEndOfInput

    val numLinesAfter: Int = 0
    val numLinesBefore: Int = 0
    def lineInfo(
        line: String,
        linesBefore: Seq[String],
        linesAfter: Seq[String],
        errorPointsAt: Int, errorWidth: Int
      ): Unit = ()

    // The implementation of this is usually provided by a mixed-in
    // token extractor, discussed in `tokenextractors`
    def unexpectedToken(
        cs: Iterable[Char],
        amountOfInputParserWanted: Int,
        lexicalError: Boolean
      ): Token = ???    
}
