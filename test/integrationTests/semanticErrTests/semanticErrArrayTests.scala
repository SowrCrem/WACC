import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class SemanticErrArrayTests extends AnyFlatSpec {

  "semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/arrayIndexComplexNotInt.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: arrayIndexNotInt.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/arrayIndexNotInt.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: arrayMultipleIndexError.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/arrayMultipleIndexError.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: badIndex.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/badIndex.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: indexUndefIdent.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/indexUndefIdent.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: mixingTypesInArrays.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/mixingTypesInArrays.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: noArrayCovariance.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/noArrayCovariance.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: nonMatchingArrays.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/nonMatchingArrays.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: noStringIndex.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/noStringIndex.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: wrongArrayDimension.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/wrongArrayDimension.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

  "semanticErr - array tests: wrongArrayType.wacc" should "return exit code 200" in {

                val path : Array[String] = Array("test/wacc/invalid/semanticErr/array/wrongArrayType.wacc")
                val exitCode = Main.compile(path)
                println("Exit Code: " + exitCode)
                exitCode shouldBe 200
            }

}
