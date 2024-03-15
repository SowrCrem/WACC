package wacc

import scala.collection.mutable.HashMap
import scala.util.matching.Regex
import scala.collection.mutable.ListBuffer

/** Macro Constraints for WACC:
  * @constraint A macro definition is of the form ~DEF MACRO_NAME := MACRO_BODY END_IF
  * @constraint A macro definition must be defined before the start of the main program
  * @constraint A macro definition must be at the start of a new line (excluding whitespace)
  * @constraint The start of the program must be on a newline and cannot have any text before "begin"
  * @constraint A macro name cannot contain spaces
  * @constraint A macro can only be defined once
  * @constraint A macro name cannot start with a digit
  * @constraint A macro name can only contain letters and digits
*/

object macroPreProcessor {
  val macroMap = new HashMap[String, String]()

  val definePattern: Regex = """~DEF (\w+) (.+)""".r
  val macroAsgnOp: String = ":="
  val macroUsagePattern: String => Regex = macroName => raw"\b$macroName\b".r
  val validMacroName: Regex = """([a-zA-Z_][a-zA-Z0-9_]*)\\
    ((\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*(,\\s*([a-zA-Z_][a-zA-Z0-9_]*))*)?\\)""".r


  /**
    * Given a line of code, this function will return the useful content of the line
    * i.e. by removing all comments and leading whitespace from the line
    *
    * @param line
    * @return useful content of the line i.e. parts of the code which will be evaluated
    */
  private def getUsefulContent(line : String) : String = line.split("#").headOption.getOrElse("").trim()

  /**
    * Given a macro definition, this function will add the macro to the macro map
    * 
    * @param macroDef
    * 
    * @pre macroDef begins with "~DEF" and ends with "END_DEF"
    */
  private def addToMacroMap(macroDef: String, lineNo : Int): Either[String, String] = {
    macroDef.stripPrefix("@DEF").trim.split(macroAsgnOp) match {
      case Array(name, body) => {
        val macroName = name.stripTrailing()
        if (macroName.charAt(0).isDigit) {
          return Left(s"Syntax Error in line $lineNo: Macro name cannot start with a digit")
        }
        
        macroName.foreach(c => {
          if (!c.isLetterOrDigit && c != '_') {
            return Left(s"""Syntax Error in line $lineNo: Macro name, $macroName,
             can only contain letters and digits or underscores, but found: $c""")
          }
        })
        if (macroName.contains(" ")) {
          Left(s"Syntax Error in line $lineNo: Macro names cannot contain spaces in $macroName")
        }
        val macroBody = body.split("END_DEF").headOption // this is only empty if body == "END_DEF"
        if (macroBody.isEmpty) {
          Left(s"Syntax Error in line $lineNo: Macro body cannot be empty")
        }
        else if (macroMap.contains(macroName)) {
          return Left(s"Syntax Error in line $lineNo: Macro $macroName has already been defined")
        }
        macroMap.put(macroName, macroBody.get)        
      }
    }
    Right("Macro definition added to macro map")
  }

  /**
    * Given the full input source code, this function will return a list of strings,
    * each string containing a single macro definition. All macros must be defined before the
    * start of a program.
    *
    * @param sourceCode full input wacc code
    * @return String for the remaining program code after removing all macro definitions
    */
  private def makeMacroMap(sourceCode: String): Either[String, String] = {

    /* Split the input code into an array containing each line */
    val lines = sourceCode.split("\n")

    val macroDef = new StringBuilder
    var i = 0
    val numLines = lines.size

    while (i < numLines) {

      var l = lines(i)
      /* Remove all comments for each line*/
      var relevantContent = getUsefulContent(l)
      // println("OuterLooping")
      // println(s"i = $i")
      
      if (relevantContent.startsWith("@DEF")) {

        /* Add each line to the macro buffer while we do not end the macro */
        while (!relevantContent.contains("END_DEF")) {
          // println("InnerLooping")
          // println(s"i = $i")
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
        addToMacroMap(macroDef.toString(), i) match {
          case Left(err) => return Left(err)
          case Right(x) => {
            macroDef.clear()
            i += 1
          }
        }
      } else if (relevantContent.isBlank()) {
        i += 1
      } else {
        
        /* Any other inputs should either fail or be the start of the program 
           Failing cases will be handled by the lexer/parser */
        macroMap.foreach(println)
        return Right(lines.slice(i, lines.size).toList.mkString("\n"))

      }
    }
    Right("")
  }

  def preprocess(sourceCode: String): Either[String, String] = {
    // Extract macro definitions from the source code
    makeMacroMap(sourceCode) match {
      case Left(err) => Left(err)
      case Right(prog : String) => {
        // Perform textual substitution for each macro definition
        Right(macroMap.foldLeft(prog) { case (code, (name, replacement)) =>
          macroUsagePattern(name).replaceAllIn(code, replacement)
        })
       } 
      }
    }

  val testinputOne : String = "~DEF MACRO_1 = 5 \n begin println MACRO_1 end"
}
