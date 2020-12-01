package com.adventofcode.shared

import scala.io.Source

object PuzzleHelpersScala {

  def readLinesFromInputFile(path: String): Array[String] = {
    Source.fromFile(path).getLines.toArray
  }
}
