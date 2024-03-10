package wacc

import scala.collection.mutable.HashMap
import scala.util.matching.Regex
import scala.collection.mutable.ListBuffer

/** Macro Constraints for WACC:
  * @constraint A macro definition is of the form ~DEF MACRO_NAME := MACRO_BODY END_IF
  * @constraint A macro definition must be defined before the start of the main program
  * @constraint A macro definition must be at the start of a new line (excluding whitespace)
  * @constraint The start of the program must be on a newline and cannot have any text before "begin"
*/


object macroPreProcessor {
  val macroMap = new HashMap[String, String]()

  val definePattern: Regex = """~DEF (\w+) (.+)""".r
  val macroAsgnOp: String = ":="
  val macroUsagePattern: String => Regex = macroName => raw"\b$macroName\b".r


  /**
    * Given a line of code, this function will return the useful content of the line
    * i.e. by removing all comments and leading whitespace from the line
    *
    * @param line
    * @return useful content of the line i.e. parts of the code which will be evaluated
    */
  def getUsefulContent(line : String) : String = line.split("#").head.trim()

  /**
    * Given a macro definition, this function will add the macro to the macro map
    *
    * @param macroDef
    */
  def addToMacroMap(macroDef: String): Unit = {
    ???
  }

  /**
    * Given the full input source code, this function will return a list of strings,
    * each string containing a single macro definition. All macros must be defined before the
    * start of a program.
    *
    * @param sourceCode full input wacc code
    * @return String for the remaining program code after removing all macro definitions
    */
  def makeMacroMap(sourceCode: String): Either[String, List[String]] = {
    /* Split the input code into an array containing each line */
    val lines = sourceCode.split("\n")
    val macroDef = new StringBuilder
    var i = 0
    while (i < lines.size) {
      var l = lines(i)
      /* Remove all comments for each line*/
      var relevantContent = getUsefulContent(l)
      if (relevantContent.startsWith("~DEF")) {
        /* Add each line to the macro buffer while we do not end the macro */
        while (!relevantContent.contains("END_DEF")) {
          macroDef.append(relevantContent)
          i += 1
          if (i == lines.size) {
            return Left(s"Syntax Error in Line $i \n    Macro definition must end with END_DEF")
          }
          l = lines(i)
          relevantContent = getUsefulContent(l)
        } 
        val macroCode = relevantContent.split("END_DEF").head
        macroDef.append(macroCode)
        addToMacroMap(macroDef.toString()) // Process macro definition and add to macro map
        macroDef.clear()
        i += 1
      } else if (relevantContent.isBlank()) {
        i += 1
      } else {
        /* Any other inputs should either fail or be the start of the program 
           Failing cases will be handled by the lexer/parser */
        Right(lines.slice(i, lines.size).toList)
      }
    }
    Right(List())
  }

  def preprocess(sourceCode: String): Either[String, String] = {
    // Extract macro definitions from the source code
    val macros = definePattern
      .findAllMatchIn(sourceCode)
      .foreach(m => macroMap.put(m.group(1), m.group(2)))

       // Perform textual substitution for each macro definition
    val x = macroMap.foldLeft(sourceCode) { case (code, (name, replacement)) =>
      macroUsagePattern(name).replaceAllIn(code, replacement)
    }

    Right(x)
  }

  val testinputOne : String = "~DEF MACRO_1 = 5 \n begin println MACRO_1 end"
}
