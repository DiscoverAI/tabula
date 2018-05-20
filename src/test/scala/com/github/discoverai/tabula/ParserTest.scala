package com.github.discoverai.tabula

import org.scalatest.{FeatureSpec, Matchers}

class ParserTest extends FeatureSpec with Matchers {
  feature("parse an md file line with intents") {
    scenario("should parse empty line and return no data") {
      Parser.parseMDFileLine((Seq(), null), "") shouldBe(Seq(), null)

      Parser.parseMDFileLine((null, null), "\n") shouldBe(null, null)
    }

    scenario("should parse intent line and return new intent") {
      Parser.parseMDFileLine((null, null), "## intent:foo") shouldBe(null, Intent("foo"))
    }

    scenario("should parse intent line and return accumulator with new intent added") {
      val parsedLine = Parser.parseMDFileLine((Seq(IntentInputData("foobar", Intent("foo"))), Intent("foo")), "## intent:bar")
      parsedLine shouldBe(Seq(IntentInputData("foobar", Intent("foo"))), Intent("bar"))
    }

    scenario("should parse input data line and return accumulator with new input data") {
      val parsedLine = Parser.parseMDFileLine((Seq(), Intent("foo")), "- foobar")
      parsedLine shouldBe(Seq(IntentInputData("foobar", Intent("foo"))), Intent("foo"))
    }

    scenario("should parse input data line and return accumulator with new input data added") {
      val parsedLine = Parser.parseMDFileLine((Seq(IntentInputData("foobar", Intent("foo"))), Intent("foo")), "- foobaz")
      parsedLine shouldBe(Seq(IntentInputData("foobar", Intent("foo")), IntentInputData("foobaz", Intent("foo"))), Intent("foo"))
    }
  }

  feature("Parse a whole md file") {
    scenario("should throw exception when not parsing md file") {
      the[IllegalArgumentException] thrownBy Parser.parseMdFile("./src/test/resources/intents.txt") should have message "Not a valid .md file"
    }

    scenario("should return sequence with  multiple intent input data") {
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
}
