package com.adventofcode.AOC2020

import com.adventofcode.AOC2020.PuzzleConstants._
import com.adventofcode.shared.PuzzleHelpersScala

object PuzzleSolutionsScala {

  def main(args: Array[String]): Unit = {
    val p1lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_1_INPUT)

    println(f"Day #1 part #1: ${day1(p1lines, 2)}")
    println(f"Day #1 part #2: ${day1(p1lines, 3)}")

  }

  def day1(input: Array[String], combinations: Int): Int = {
    input.map(_.toInt).combinations(combinations)
      .filter(x => x.sum == 2020)
      .toList.head.product
  }
}
