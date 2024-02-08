package integrationTests.validTests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ValidFunctionTests extends AnyFlatSpec {

  "valid - nested_functions tests: fibonacciFullRec.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/fibonacciFullRec.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: fibonacciRecursive.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/fibonacciRecursive.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: fixedPointRealArithmetic.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/fixedPointRealArithmetic.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: functionConditionalReturn.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/functionConditionalReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: mutualRecursion.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/mutualRecursion.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: printInputTriangle.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/printInputTriangle.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: printTriangle.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/printTriangle.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - nested_functions tests: simpleRecursion.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/nested_functions/simpleRecursion.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: argScopeCanBeShadowed.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/argScopeCanBeShadowed.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: asciiTable.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/asciiTable.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionDeclaration.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionDeclaration.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionDoubleReturn.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionDoubleReturn.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionIfReturns.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionIfReturns.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionManyArguments.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionManyArguments.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionMultiReturns.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionMultiReturns.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionReturnPair.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionReturnPair.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionSimpleLoop.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionSimpleLoop.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionSimple.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionSimple.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: functionUpdateParameter.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/functionUpdateParameter.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: incFunction.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/incFunction.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: lotsOfLocals.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/lotsOfLocals.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: manyArgumentsChar.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/manyArgumentsChar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: manyArgumentsInt.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/manyArgumentsInt.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: negFunction.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/negFunction.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: punning.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/punning.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: sameArgName2.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/sameArgName2.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: sameArgName.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/sameArgName.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: sameNameAsVar.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/sameNameAsVar.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

  "valid - simple_functions tests: usesArgumentWhilstMakingArgument.wacc" should "return exit code 0" in {

    val path : Array[String] = Array("test/wacc/valid/function/simple_functions/usesArgumentWhilstMakingArgument.wacc")
    val exitCode = Main.compile(path)
    println("Exit Code: " + exitCode)
    exitCode shouldBe 0
  }

}
