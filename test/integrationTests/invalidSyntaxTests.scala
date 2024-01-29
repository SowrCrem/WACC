import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import scala.sys.process._
import wacc.Main
import wacc.IntLiter
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.io.Source._
import scala.collection.mutable


class InvalidSyntaxTests extends AnyFlatSpec {

  val txtFileName ="syntaxTestNames.txt"
  if (new java.io.File(txtFileName).length() == 0) {
    "sh test/wacc/invalid/syntaxErr/extractPathsToExamples.sh".!!
  }
  val source = fromFile(txtFileName)
  val testNamesSet : Array[String] = try source.getLines().toArray finally source.close()

  "invalid syntax tests" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/invalid/syntaxErr/basic/badComment.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
  }

  // "invalid syntax tests" should "return exit code 100" in {
  //   for (path: String <- testNamesList) {
  //     println("Testing " + path)
  //     val exitCode = Main.compile(Array(path))
  //     println("Exit Code: " + exitCode)
  //     exitCode shouldBe 100
  //   }
  //   pending
  // }
}
