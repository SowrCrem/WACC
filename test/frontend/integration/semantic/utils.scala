package frontend.integration.semantic

import wacc.Main
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

object Utils {
  def throwsSemanticError(path: String): Assertion = {
    // If current directory is not the root of the project, then add a ../ to the start of the path
    var newPath = "test/wacc/" + path
    if (!new java.io.File("src/main/wacc/Main.scala").exists) {
      newPath = "../" + newPath
    }
    val exitCode = Main.compile(Array(newPath))
    println("Exit Code: " + exitCode)
    // if (exitCode != 200) {
    //   val filePath = "test/integration/semantic/checkArrays.scala"
    //   val sedCommand = s"""sed -i '0,/"semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" in {/s/"semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" in {/"semanticErr - array tests: arrayIndexComplexNotInt.wacc" should "return exit code 200" ignore {/' $filePath"""
    //   sedCommand.!
    // }
    exitCode shouldBe 200
  }
}
