package test.frontend.integration.semantic.Read

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class Read extends AnyFlatSpec {

  "semanticErr - read tests: readIntoBadFst.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/read/readIntoBadFst.wacc")
  }

  "semanticErr - read tests: readIntoBadSnd.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/read/readIntoBadSnd.wacc")
  }

  "semanticErr - read tests: readTypeErr01.wacc" should "return exit code 200" in {
    throwsSemanticError("invalid/semanticErr/read/readTypeErr01.wacc")
  }

}
