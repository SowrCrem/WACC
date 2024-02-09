package wacc

import parsley.{Success, Failure}

object Main {
  def main(args: Array[String]): Unit = {
    // println("hello WACC!")
    // args.foreach { println(_) }
    // println("Num Args= " + args.length)
    val exitCode = compile(args)
    // println("Exit Code: " + exitCode)
    sys.exit(exitCode)
  }

  def syntaxError(msg: String = ""): Int = {
    println("Syntax Analysis reported an Error: " + msg)
    100
  }

  def semanticCheck(node: Node): Int = {
    semanticChecker.check(node) match {
      case Right(exitCode) => {
        println("exit code: " + exitCode)
        0
      }
      case Left(msg) => {
        println("Semantic Analysis reported an Error: " + msg)
        200
      }
    }
  }

  def compile(args: Array[String]): Int = args.headOption match {
      case Some(filename) => parser.parse(filename) match {
        case Success(node) => node match {
          case Program(funcList, _) => parser.validFunctions(funcList) match {
            case true  => semanticCheck(node)
            case false => syntaxError("Non-terminating branches found")
          }
          case _ => syntaxError("Program is not Well-Formed")
        }
        case Failure(msg) => syntaxError(msg)
      }
      case None => {
        println("please enter an expression")
        -1
      }
    }
  }
