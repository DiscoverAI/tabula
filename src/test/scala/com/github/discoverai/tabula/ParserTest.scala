package com.github.discoverai.tabula

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

class ParserTest extends AnyFeatureSpec with Matchers {
  Feature("parse an md file line with intents") {
    Scenario("should parse empty line and return no data") {
      Parser.parseMDFileLine((Seq(), null), "") shouldBe(Seq(), null)

      Parser.parseMDFileLine((null, null), "\n") shouldBe(null, null)
    }

    Scenario("should parse intent line and return new intent") {
      Parser.parseMDFileLine((null, null), "## intent:foo") shouldBe(null, Intent("foo"))
    }

    Scenario("should parse intent line and return accumulator with new intent added") {
      val parsedLine = Parser.parseMDFileLine((Seq(IntentInputData("foobar", Intent("foo"))), Intent("foo")), "## intent:bar")
      parsedLine shouldBe(Seq(IntentInputData("foobar", Intent("foo"))), Intent("bar"))
    }

    Scenario("should parse input data line and return accumulator with new input data") {
      val parsedLine = Parser.parseMDFileLine((Seq(), Intent("foo")), "- foobar")
      parsedLine shouldBe(Seq(IntentInputData("foobar", Intent("foo"))), Intent("foo"))
    }

    Scenario("should parse input data line and return accumulator with new input data added") {
      val parsedLine = Parser.parseMDFileLine((Seq(IntentInputData("foobar", Intent("foo"))), Intent("foo")), "- foobaz")
      parsedLine shouldBe(Seq(IntentInputData("foobar", Intent("foo")), IntentInputData("foobaz", Intent("foo"))), Intent("foo"))
    }
  }

  Feature("Parse a whole md file") {
    Scenario("should throw exception when not parsing md file") {
      the[IllegalArgumentException] thrownBy Parser.parseMdFile("./src/test/resources/intents.txt") should have message "Not a valid .md file"
    }

    Scenario("should return sequence with  multiple intent input data") {
      val parsedIntents = Parser.parseMdFile("./src/test/resources/intents.md")
      val expectedIntents = Seq(
        IntentInputData("Bye", Intent("bye")),
        IntentInputData("Goodbye", Intent("bye")),
        IntentInputData("Hi", Intent("greet")),
        IntentInputData("Hey", Intent("greet")),
        IntentInputData("Hi bot", Intent("greet")),
        IntentInputData("Thanks !", Intent("thank"))
      )
      parsedIntents shouldBe expectedIntents
    }
  }

  Feature("Split each intent grouped by label by given percent") {
    Scenario("should split by 50 50 of each intent set") {
      val givenIntents = Seq(
        IntentInputData("Bye", Intent("bye")),
        IntentInputData("Goodbye", Intent("bye")),
        IntentInputData("Hi", Intent("greet")),
        IntentInputData("Hey", Intent("greet")),
        IntentInputData("Hi bot", Intent("greet")),
        IntentInputData("Yo !", Intent("greet"))
      )

      val actual = Parser.splitDataset(0.5, givenIntents)
      val (actualTrainDataset, actualTestDataset) = actual
      val expectedTrainDataset = Seq(
        IntentInputData("Bye", Intent("bye")),
        IntentInputData("Hi", Intent("greet")),
        IntentInputData("Hey", Intent("greet"))
      )
      val expectedTestDataset = Seq(
        IntentInputData("Goodbye", Intent("bye")),
        IntentInputData("Hi bot", Intent("greet")),
        IntentInputData("Yo !", Intent("greet"))
      )

      actualTrainDataset should contain theSameElementsAs expectedTrainDataset
      actualTestDataset should contain theSameElementsAs expectedTestDataset
    }
  }
}
