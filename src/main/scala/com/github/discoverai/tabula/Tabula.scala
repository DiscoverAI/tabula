package com.github.discoverai.tabula

import java.io.PrintWriter
import java.nio.file.{Files, Paths}
import scala.util.Random

object Tabula {

  def writeDatamart(destinationFile: String, inputData: Seq[IntentInputData], labels: Map[String, Int]): Unit = {
    val datamartLines = DatamartHelper.datamart(inputData, labels)
    val datamartFile = Paths.get(destinationFile)
    val datamartWriter = new PrintWriter(Files.newOutputStream(datamartFile))
    datamartLines.foreach(line => datamartWriter.append(s"$line\n"))
    datamartWriter.close()
  }

  def main(args: Array[String]) {
    args match {
      case Array(inputFile, outputFolder) =>
        try {
          println(s"Start writing files for '$inputFile' to folder '$outputFolder'")
          val intentInputs: Seq[IntentInputData] = Random.shuffle(Parser.parseMdFile(inputFile))
          val labels = DatamartHelper.labels(intentInputs)
          val labelFile = Paths.get(outputFolder + "/labels.csv")
          val labelWriter = new PrintWriter(Files.newOutputStream(labelFile))
          labels.foreach(label => labelWriter.append(s"${label._2},${label._1}\n"))
          labelWriter.close()

          val trainAndTestData = intentInputs.splitAt((intentInputs.length * 0.8).toInt)

          writeDatamart(outputFolder + "/train.csv", trainAndTestData._1, labels)
          writeDatamart(outputFolder + "/test.csv", trainAndTestData._2, labels)

          val headers = "label,sentence"
          val headersFile = Paths.get(outputFolder + "/headers.csv")
          Files.write(headersFile, headers.getBytes)

          println("Done writing files")
        } catch {
          case e: IllegalArgumentException => print(e.getMessage)
          case _: Throwable => println("Make sure your files and folders exist!")
        }
      case _ =>
        println("Usage: tabula [INTENT.MD FILE] [OUTPUT FOLDER]")
    }
  }
}
