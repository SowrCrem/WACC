import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidPairsTests extends AnyFlatSpec {

  "valid - pairs tests: checkRefPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/checkRefPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: createPair02.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/createPair02.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: createPair03.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/createPair03.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: createPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/createPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: createRefPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/createRefPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: free.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/free.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: linkedList.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/linkedList.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: nestedPairLeftAssign.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/nestedPairLeftAssign.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: nestedPairRightExtract.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/nestedPairRightExtract.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: nestedPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/nestedPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: null.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/null.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: pairarray.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/pairarray.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: pairExchangeArrayOk.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/pairExchangeArrayOk.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: printNullPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/printNullPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: printNull.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/printNull.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: printPairOfNulls.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/printPairOfNulls.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: printPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/printPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: readPair.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/readPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: writeFst.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/writeFst.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - pairs tests: writeSnd.wacc" should "return exit code 0" in {
    pending

    val path : Array[String] = Array("test/wacc/valid/pairs/writeSnd.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}