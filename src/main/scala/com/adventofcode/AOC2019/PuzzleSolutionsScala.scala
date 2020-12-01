package com.adventofcode.AOC2019

import com.adventofcode.AOC2019.PuzzleConstants._
import com.adventofcode.shared.PuzzleHelpers

import scala.collection.mutable

object PuzzleSolutionsScala {

  def main(args: Array[String]): Unit = {
    val p1lines = PuzzleHelpers.readLinesFromResourceFile(PUZZLE_1_INPUT)
    val p2lines = PuzzleHelpers.readLinesFromResourceFile(PUZZLE_2_INPUT)

    println(f"Day #1 part #1: ${day1part1(p1lines)}")
    println(f"Day #1 part #2: ${day1part2(p1lines)}")

    println(f"Day #2 part #1: ${day2part1(p2lines)}")
    println(f"Day #2 part #2: ${day2part2(p2lines)}")
  }

  def day1part1(input: Array[String]): Int =  input.map(_.toInt).sum

  def day1part2(input: Array[String]): Int = {
    var sum : Int = 0
    val frequencies = mutable.MutableList[Int]()
    while (true) {
      for (i <- input.map(_.toInt)) {
        sum = sum + i
        if (frequencies.contains(sum)) {
          return sum
        }
        frequencies += sum
      }
    }
    -1
  }

  def day2part1(input: Array[String]): Int = {
    var hasTwoCounter = 0
    var hasThreeCounter = 0
    for (line <- input) {
      val freqMap = line.toCharArray.groupBy(identity).mapValues(_.length)
      if (freqMap.valuesIterator.exists(_.equals(2))) {
        hasTwoCounter += 1
      }
      if (freqMap.valuesIterator.exists(_.equals(3))) {
        hasThreeCounter += 1
      }
    }
    hasTwoCounter * hasThreeCounter
  }

  def day2part2(input: Array[String]): String = {
    for (left <- input) {
      for (right <- input) {
        var diffNum = 0
        var diffCharIndex = 0
        for (i <- 0 until left.length) {
          if (left(i) != right(i)) {
            diffNum += 1
            diffCharIndex = i
          }
        }
        if (diffNum==1) {
          val sb = new mutable.StringBuilder(left)
          return sb.deleteCharAt(diffCharIndex).toString()
        }
      }
    }
    null
  }
}
