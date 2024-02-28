package test.backend.integration

import wacc._
import test.Utils._
import org.scalactic.Bool
import parsley.{Failure, Result, Success}
import sys.process._
import scala.collection.mutable.ListBuffer
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.compatible.Assertion
import org.scalatest.matchers.should.Matchers._

class Scope extends AnyFlatSpec with BeforeAndAfterEach {

  override protected def afterEach(): Unit = {
    semanticChecker.reset()
    X86IRGenerator.reset()
  }

    it should "run valid/scope/ifNested.wacc" in {
        val path = constructPath(List("valid", "scope", "ifNested1.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/scope/ifNested2.wacc" in {
        val path = constructPath(List("valid", "scope", "ifNested2.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/scope/scopeIfRedefine.wacc" in {
        val path = constructPath(List("valid", "scope", "scopeIfRedefine.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/scope/scopeBasic.wacc" in {
        val path = constructPath(List("valid", "scope", "scopeBasic.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/scope/scopeVars.wacc" in {
        val path = constructPath(List("valid", "scope", "scopeVars.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/scope/scopeSimpleRedefine.wacc" in {
        val path = constructPath(List("valid", "scope", "scopeSimpleRedefine.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }

    it should "run valid/scope/splitScope.wacc" in {
        val path = constructPath(List("valid", "scope", "splitScope.wacc"))
        runSucceeds(path, "Hello World!", 0)
    }
}
  
