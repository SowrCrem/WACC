package test.backend.unit

import wacc._
import org.scalatest.matchers.should.Matchers._
import test.Utils._
import parsley.{Failure, Result, Success}
import org.scalatest.{BeforeAndAfterEach, Assertion}
import org.scalatest.flatspec.AnyFlatSpec
import scala.collection.mutable.ListBuffer


class RegisterTrackerTest extends AnyFlatSpec {

  "register tracker" should "assignAndGetVar - Assign variable to register" in {
    val tracker = new RegisterTracker()
    val (reg, evictedFlag) = tracker.assignAndGetVar(0, "x")
    tracker.used should contain (VariableScope(0, "x") -> reg)
    tracker.available should not contain reg
    evictedFlag shouldBe false
  }

  it should "assignAndGetVar - Assign variable to stack when no registers available" in {
    pending
    val tracker = new RegisterTracker()
    // Assign all available registers
    for (num <- 1 to tracker.available.size) {
      tracker.assignAndGetVar(num, "x")
    }
    val (reg, evictedFlag) = tracker.assignAndGetVar(0, "y")
    evictedFlag shouldBe true
    tracker.used should contain (VariableScope(0, "y") -> reg)
    tracker.available should not contain reg
    tracker.stack should contain (VariableScope(1, "x"))
  }

  it should "exitLastScope - Deallocate variables in the most local scope" in {
    val tracker = new RegisterTracker()
    val (reg1, _) = tracker.assignAndGetVar(0, "x")
    val (reg2, _) = tracker.assignAndGetVar(1, "y")
    val (reg3, _) = tracker.assignAndGetVar(1, "z")
    tracker.exitLastScope(1, 2)
    tracker.used should contain (VariableScope(0, "x"), reg1)
    tracker.available should contain (reg2)
    tracker.available should contain (reg3)
    tracker.used should not contain reg2
    tracker.used should not contain reg3
    tracker.stack shouldBe empty
  }

}
