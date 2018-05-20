import org.scalatest.{FeatureSpec, Matchers}

class LibrarySuite extends FeatureSpec with Matchers {
  feature("test") {
    scenario("example") {
      true shouldBe true
    }
  }
}
