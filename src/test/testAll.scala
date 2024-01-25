import java.io.File
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import sys.process._

class CompilerTester extends AnyFlatSpec {

    // Define test for the waccCompiler script

    "waccCompiler" should "return 200 for semantic error files" in {
        val exitCode = "./compile /src/test/invalid/semanticErr/array/badIndex.wacc".!
        assert(exitCode == 200)
    }

    it should "return 100 for syntactic error files" in {
        val exitCode = "./compile src/test/invalid/syntaxErr/basic/badComment.wacc".!
        assert(exitCode == 100)
    }

    it should "return 0 for valid files" in {
        val exitCode = "./compile test/valid/basic/exit/exit-1.wacc".!
        assert(exitCode == 0)
    }
}