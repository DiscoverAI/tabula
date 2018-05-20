package com.github.discoverai.tabula

import scala.io.Source
import scala.util.matching.Regex

object Parser {
  def parseMdFile(filePath: String): Seq[IntentInputData] = {
    if (!filePath.endsWith(".md")) {
      throw new IllegalArgumentException("Not a valid .md file")
    }

    val lines = Source.fromFile(filePath).getLines.toSeq
    val initialAcc: (Seq[IntentInputData], Intent) = (Seq(), null)
    lines.foldLeft(initialAcc)(parseMDFileLine)._1
  }

  val intentPattern: Regex = "## intent:(\\S+)".r
  val intentInputDataPattern: Regex = "\\s*- (.+)".r

  def parseMDFileLine(acc: (Seq[IntentInputData], Intent), line: String): (Seq[IntentInputData], Intent) = {
    line match {
      case intentPattern(intent) =>
        (acc._1, Intent(intent))
      case intentInputDataPattern(sentence) =>
        val currentIntent = acc._2
        (acc._1 :+ IntentInputData(sentence, currentIntent), currentIntent)
      case _ =>
        acc
    }
  }
}

case class Intent(title: String)

case class IntentInputData(sentence: String, label: Intent)