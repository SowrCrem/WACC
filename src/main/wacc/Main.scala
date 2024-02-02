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
        val fileContent = ("cat " + filename).!!
        parser.parser.parse(fileContent) match {
          case Success(node) => {
            println(s"$fileContent = $node")
            0
          }
          case Failure(msg) => {
            // println(msg)
            println("failed")
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
