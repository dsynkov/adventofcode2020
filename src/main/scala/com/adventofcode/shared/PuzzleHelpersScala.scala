package com.adventofcode.shared

import org.apache.commons.lang3.SystemUtils

import scala.io.Source

object PuzzleHelpersScala {

  def readLinesFromInputFile(path: String): Array[String] = {
    Source.fromFile(path).getLines.toArray
  }

  def readLinesWithSplitOnBlankFromInputFile(path: String): Array[String] = {
    val fileAsString = Source.fromFile(path).mkString
    val lineSep = if (SystemUtils.IS_OS_WINDOWS) "\r\n" else "\n"
    val blankLineSep = lineSep * 2
    fileAsString
      .split(blankLineSep)
      .map(_.split(lineSep).mkString(" "))
  }
}
