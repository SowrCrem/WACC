import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrPairsTests extends AnyFlatSpec {

  "semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/badPairAssign.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/badPairExchange.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/freeNonPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/mismatchedPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: nonMatchingPairs.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/nonMatchingPairs.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/noPairCovariance.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: readUnknown.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/readUnknown.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

  "semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 100" in {
    val path : Array[String] = Array("test/wacc/semanticErr/Pairs/wrongTypeInParameterlessPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 100
        
    pending
  }

}
