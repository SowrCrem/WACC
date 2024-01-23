import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class part1 extends AnyFlatSpec {

  "arbitrary test" should "return true" in {
    true shouldBe true
  }

  "arb test 2" should "should fail" in {
    true shouldBe false
  }

}