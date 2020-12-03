package com.adventofcode.AOC2020

import com.adventofcode.AOC2020.PuzzleConstants._
import com.adventofcode.shared.PuzzleHelpersScala

object PuzzleSolutionsScala {

  def main(args: Array[String]): Unit = {
    val p1lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_1_INPUT)
    val p2lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_2_INPUT)
    val p3lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_3_INPUT)

    println(f"Day #1 part #1: ${day1(p1lines, 2)}")
    println(f"Day #1 part #2: ${day1(p1lines, 3)}")

    println(f"Day #2 part #1: ${day2part1(p2lines)}")
    println(f"Day #2 part #2: ${day2part2(p2lines)}")

    println(f"Day #3 part #1: ${day3part2(p3lines)}")
    println(f"Day #3 part #2: ${day3part2(p3lines)}")
  }

  def day1(input: Array[String], combinations: Int): Int = {
    input.map(_.toInt).combinations(combinations)
      .filter(x => x.sum == 2020)
      .toList.head.product
  }

  def day2part1(input: Array[String]): Int = {
    val pattern = """(\d+)-(\d+) (\w): (\w+)""".r
    val counterArray = for {
      line <- input
      pattern(minNum, maxNum, letter, pWord) = line
      occurrences = pWord.count(_ == letter.charAt(0))
      if (minNum.toInt to maxNum.toInt).contains(occurrences)
    }
      yield 1
    counterArray.sum
  }

  def day2part2(input: Array[String]): Int = {
    val pattern = """(\d+)-(\d+) (\w): (\w+)""".r
    val counterArray = for {
      line <- input
      pattern(minNum, maxNum, letter, pWord) = line
      charAtMin = pWord.charAt(minNum.toInt-1)==letter.charAt(0)
      charAtMax = pWord.charAt(maxNum.toInt-1)==letter.charAt(0)
      if (charAtMin && !charAtMax) || (!charAtMin && charAtMax)
    }
      yield 1
    counterArray.sum
  }

  def day3part1(input: Array[String]): Int = -1

  def day3part2(input: Array[String]): Int = -1
}
