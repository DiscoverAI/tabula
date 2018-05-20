package com.github.discoverai.tabula

import scala.collection.parallel.ParSeq

object DatamartHelper {
  def labels(intentInputData: Seq[IntentInputData]): Map[String, Int] = {
    val labelData = intentInputData.map(_.label).distinct.map(_.title)
    labelData.zipWithIndex.toMap
  }

  def datamart(intentInputData: Seq[IntentInputData], labelsMap: Map[String, Int]): ParSeq[String] = {
    intentInputData.par.map({
      intent =>
        val label = labelsMap(intent.label.title)
        val sanitizedSentence = intent.sentence.replace("\"", "'")
        s"""$label,"$sanitizedSentence""""
    })
  }
}
