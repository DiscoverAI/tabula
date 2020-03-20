package com.github.discoverai.tabula

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers


class DatamartHelperTest extends AnyFeatureSpec with Matchers {

  val intentInputData = Seq(
    IntentInputData("Bye", Intent("bye")),
    IntentInputData("Goodbye", Intent("bye")),
    IntentInputData("Hi", Intent("greet")),
    IntentInputData("Hey", Intent("greet")),
    IntentInputData("Hi bot", Intent("greet")),
    IntentInputData("Thanks !", Intent("thank"))
  )

  Feature("Generate labels") {
    Scenario("should generate labels from sequence of intent input data") {
      DatamartHelper.labels(intentInputData) shouldBe Map("bye" -> 0, "greet" -> 1, "thank" -> 2)
    }
  }

  Feature("Generate datamart") {
    Scenario("should generate datamart lines from sequence of intent input data") {
      val datamartLines = DatamartHelper.datamart(intentInputData, Map("bye" -> 0, "greet" -> 1, "thank" -> 2))
      datamartLines should contain theSameElementsAs Seq(
        "0,\"Bye\"",
        "0,\"Goodbye\"",
        "1,\"Hi\"",
        "1,\"Hey\"",
        "1,\"Hi bot\"",
        "2,\"Thanks !\""
      )
    }
  }
}
