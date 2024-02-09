package wacc

import parsley.{Success, Failure}
import scala.sys.process._

object Main {
  def main(args: Array[String]): Unit = {
    // println("hello WACC!")
    // args.foreach { println(_) }
    // println("Num Args= " + args.length)
    val exitCode = compile(args)
    // println("Exit Code: " + exitCode)
    sys.exit(exitCode)
  }

  def compile(args: Array[String]): Int = {
    args.headOption match {
      case Some(filename) => {
        // if (filename.contains("semanticErr")) {
        //   return 200
        // }
        val fileContent = ("cat " + filename).!!
        parser.parser.parse(fileContent) match {
          case Success(node) => node match {
            // Go through each function in the node's function list and check if parser.validFunction(func) is true
            // If it is, then continue, else return an error with code 100 (syntax)
            case Program(funcList, stat) => {
              funcList.foreach(func => parser.validFunction(func))
              // if any are false, we need to return 100. Otherwise, we can do semantic analysis
              if (funcList.exists(func => !parser.validFunction(func))) {
                println("Syntax Analysis reported an Error: Non-terminating branches found")
                100
              } else {
                semanticChecker.check(node) match {
                  // Perform semantic analysis
                  // Use the semanticChecker object
                  case Right(exitCode) => {
                    println("exit code: " + exitCode)
                    0
                  }
                  case Left(msg) => {
                    println(msg)
                    println("Semantic Analysis reported an Error")
                    200
                  }
                }
              }
            }
            case _ => {
              println("Syntax Analysis reported an Error: Program is not Well-Formed")
              100
            }
          }
          case Failure(msg) => {
            println(msg)
            println("Syntax Analysis reported an Error")
            100
          }
        }
      }
      case None => {
        println("please enter an expression")
        -1
      }
    }
  }
}
