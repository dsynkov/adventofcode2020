package com.adventofcode.AOC2020

import com.adventofcode.AOC2020.PuzzleConstants._
import com.adventofcode.shared.PuzzleHelpersScala

object PuzzleSolutionsScala {

  def main(args: Array[String]): Unit = {
    val p1lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_1_INPUT)
    val p2lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_2_INPUT)
    val p3lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_3_INPUT)
    val p4lines = PuzzleHelpersScala.readDay4LinesFromInputFile(PUZZLE_4_INPUT)
    val p5lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_5_INPUT)
    val p6lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_6_INPUT)

    println(f"Day #1 part #1: ${day1(p1lines, 2)}")
    println(f"Day #1 part #2: ${day1(p1lines, 3)}")

    println(f"Day #2 part #1: ${day2part1(p2lines)}")
    println(f"Day #2 part #2: ${day2part2(p2lines)}")

    println(f"Day #3 part #1: ${day3part1(p3lines, 3, 1)}")
    println(f"Day #3 part #2: ${day3part2(p3lines)}")

    println(f"Day #4 part #1: ${day4part1(p4lines)}")
    println(f"Day #4 part #2: ${day4part2(p4lines)}")

    val seatIds = day5part1(p5lines)
    println(f"Day #5 part #1: ${seatIds.max}")
    println(f"Day #5 part #2: ${day5part2(seatIds)}")

    println(f"Day #6 part #1: ${day4part1(p6lines)}")
    println(f"Day #6 part #2: ${day4part2(p6lines)}")
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

  def day3part1(input: Array[String], right: Int, down: Int): Long = {
    /*
    Use modulo to take into account
    input expanding indefinitely
    to the right.
     */
    var treeCounter = 0L
    var index = 0
    val lineLength = input.head.length
    for (i <- input.indices by down) {
      if (input(i).charAt(index).equals('#')) treeCounter += 1
      index = (index + right) % lineLength
    }
    treeCounter
  }

  def day3part2(input: Array[String]): Long =
    Array((1, 1), (3, 1), (5, 1), (7, 1), (1, 2))
      .map(tup => day3part1(input, tup._1, tup._2))
      .product

  def day4part1(input: Array[String]): Int = {
    val expectedFields = Array("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:")
    input.count(passport => expectedFields.forall(passport.contains))
  }


  def day4part2(input: Array[String]): Int = {
    val expectedFields = Array("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:")
    val validationsMap = Map(
      "cid" -> {v: String => true},
      "byr" -> {v: String => (1920 to 2002).contains(v.toInt)},
      "iyr" -> {v: String => (2010 to 2020).contains(v.toInt)},
      "eyr" -> {v: String => (2020 to 2030).contains(v.toInt)},
      "hcl" -> {v: String => v.matches("#[0-9a-fA-F]+")},
      "ecl" -> {v: String => Array("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(v)},
      "pid" -> {v: String => v.forall(_.isDigit) && v.length==9},
      "hgt" -> {v: String =>
        v.endsWith("cm") && (150 to 193).contains(v.replace("cm","").toInt) ||
          v.endsWith("in") && (59 to 70).contains(v.replace("in","").toInt)
      }
    )
    input
      .filter(line => expectedFields.forall(line.contains))
      .map(line => line.split("\\s")
        .flatMap(_.split(":"))
        .grouped(2).map(arr => arr(0) -> arr(1)).toMap
      ).count(field => !field.map{
        case(k,v) => validationsMap(k)(v)
      }.toArray.contains(false)
    )
  }

  def day5part1(input: Array[String]): Array[Int] = {
    val subMap = Map('F' -> '0', 'L' -> '0', 'B' -> '1', 'R' -> '1')
    input
      .map(s => s.splitAt(7))
      .map{case(x, y) =>
        val row = Integer.parseInt(x.map(c => subMap.getOrElse(c, c)), 2)
        val col = Integer.parseInt(y.map(c => subMap.getOrElse(c, c)), 2)
        row * 8 + col
      }
    }

  def day5part2(input: Array[Int]): Int =
    (input.min to input.max).sum - input.sum

  def day6part1(input: Array[String]): Int = -1

  def day6part2(input: Array[String]): Int = -1
}
