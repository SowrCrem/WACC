package wacc

import parsley.{Success, Failure}
import scala.sys.process._

object Main {
  def main(args: Array[String]): Unit = {
    println("hello WACC!")
    args.foreach { println(_) }
    println("Num Args= " + args.length)
    val exitCode = compile(args)
    println("Exit Code: " + exitCode)
  }

  def compile(args: Array[String]): Int = {
    args.headOption match {
      case Some(filename) => {
        val expr = ("cat " + filename).!!
        parser.parser.parse(expr) match {
          case Success(x) => {
            println(s"$expr = $x")
            0
          }
          case Failure(msg) => {
            println(msg)
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
