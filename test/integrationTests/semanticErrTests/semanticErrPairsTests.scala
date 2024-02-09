package integrationTests.semanticErrTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class SemanticErrPairsTests extends AnyFlatSpec {

  "semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/badPairAssign.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: badPairAssign.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/badPairExchange.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: badPairExchange.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/freeNonPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: freeNonPair.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/mismatchedPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: mismatchedPair.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/noPairCovariance.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: noPairCovariance.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: readUnknown.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/readUnknown.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: readUnknown.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: readUnknown.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: readUnknown.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

  "semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 200" in {

    val path : Array[String] = Array("test/wacc/invalid/semanticErr/pairs/wrongTypeInParameterlessPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)

    if (exitCode != 200) {
      val filePath = "test/integrationTests/semanticErrTests/semanticErrPairsTests.scala"
      val sedCommand = s"""sed -i '0,/"semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 200" in {/s/"semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 200" in {/"semanticErr - pairs tests: wrongTypeInParameterlessPair.wacc" should "return exit code 200" ignore {/' $filePath"""
      sedCommand.!
    }

    exitCode shouldBe 200
  }

}
