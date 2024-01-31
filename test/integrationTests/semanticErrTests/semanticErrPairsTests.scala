import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrPairsTests extends AnyFlatSpec {

  "semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/badPairAssign.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/badPairExchange.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/freeNonPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/mismatchedPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: nonMatchingPairs.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/nonMatchingPairs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/noPairCovariance.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: readUnknown.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/readUnknown.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 200" in {
    pending

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/wrongTypeInParameterlessPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 200
  }

}
