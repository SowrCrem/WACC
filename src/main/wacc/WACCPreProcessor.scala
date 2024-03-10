package wacc

import scala.collection.mutable.HashMap
import scala.util.matching.Regex

object macroPreProcessor {
  val macroMap = new HashMap[String, String]()

  val definePattern: Regex = """~DEF (\w+) (.+)""".r
  val macroUsagePattern: String => Regex = macroName => raw"\b$macroName\b".r

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
