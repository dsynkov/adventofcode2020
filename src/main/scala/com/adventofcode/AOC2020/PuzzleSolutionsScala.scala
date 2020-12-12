package com.adventofcode.AOC2020

import com.adventofcode.AOC2020.PuzzleConstants._
import com.adventofcode.shared.PuzzleHelpersScala

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object PuzzleSolutionsScala {

  // For Day 7
  type BagMap = mutable.Map[String, mutable.ListBuffer[mutable.Map[String, Int]]]

  def main(args: Array[String]): Unit = {
    val p1lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_1_INPUT)
    val p2lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_2_INPUT)
    val p3lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_3_INPUT)
    val p4lines = PuzzleHelpersScala.readLinesWithSplitOnBlankFromInputFile(PUZZLE_4_INPUT)
    val p5lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_5_INPUT)
    val p6lines = PuzzleHelpersScala.readLinesWithSplitOnBlankFromInputFile(PUZZLE_6_INPUT)
    val p7lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_7_INPUT)
    val p8lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_8_INPUT)
    val p9lines = PuzzleHelpersScala.readLinesFromInputFile(PUZZLE_9_INPUT)

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

    println(f"Day #6 part #1: ${day6part1(p6lines)}")
    println(f"Day #6 part #2: ${day6part2(p6lines)}")

    println(f"Day #7 part #1: ${day7part1(p7lines)}")
    println(f"Day #7 part #2: ${day7part2(p7lines)}")

    println(f"Day #8 part #1: ${day8part1(p8lines)}")
    println(f"Day #8 part #2: ${day8part2(p8lines)}")

    println(f"Day #9 part #1: ${day9part1(p9lines)}")
    println(f"Day #9 part #2: ${day9part2(p9lines)}")
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

  def day6part1(input: Array[String]): Int = {
    input.map(_.replaceAll("\\s+", "").toSet.size).sum
  }

  def day6part2(input: Array[String]): Int = {
    var counter = 0
    for (group <- input) {
      val passengers = group.split("\\s+")
      if (passengers.length==1) {
        counter += passengers.head.length
      } else {
        val intersection = passengers.reduce((x, y) => x.intersect(y))
        counter += intersection.length
      }
    }
    counter
  }

  def day7part1(input: Array[String]): Int = {
    val bagMap = getBagMap(input)
    getColorCount("shiny gold", bagMap)
  }

  def day7part2(input: Array[String]): Int = {
    val bagMap: BagMap = getBagMap(input)
    getBagCount("shiny gold", bagMap) - 1
  }

  def getBagMap(input: Array[String]): BagMap = {
    val bagMap: BagMap = mutable.Map()
    for (line <- input) {
      val rootColor = line.split("\\s+").slice(0, 2).mkString(" ")
      val innerColors = line.split("contain")(1).trim.split(",")
      val innerList: ListBuffer[mutable.Map[String, Int]] = ListBuffer()
      if (!innerColors(0).startsWith("no")) {
        for (innerColor <- innerColors) {
          val innerSplit = innerColor.trim.split("\\s+")
          val innerNum = Integer.parseInt(innerSplit(0))
          val innerName = innerSplit.slice(1, 3).mkString(" ")
          innerList.append(mutable.Map(innerName -> innerNum))
        }
      }
      bagMap.put(rootColor, innerList)
    }
    bagMap
  }

  def getColorCount(color: String, bagMap: BagMap, bagList: ListBuffer[String] = ListBuffer()): Int = {
    for ((nextColor, bags) <- bagMap) {
      for (bag <- bags) {
        if (bag.keySet.contains(color)) {
          bagList.append(nextColor)
          getColorCount(nextColor, bagMap, bagList)
        }
      }
    }
    bagList.toSet.size
  }

  /**
    * TODO: Revisit initial attempt at DFS solution.
    * See https://github.com/VitaminJai/AOC2020/blob/main/Day7/day7.2.py#L12-L19
    * for Python solution.
    */
  def getBagCount(color: String, bagMap: BagMap): Int = {
    var total = 1
    if (bagMap(color).isEmpty) {
      return 1
    }
    for (bag <- bagMap(color)) {
      for ((nextColor, count) <- bag) {
        total += count * getBagCount(nextColor, bagMap)
      }
    }
    total
  }

  def day8part1(input: Array[String]): Int = {
    -1
  }

  def day8part2(input: Array[String]): Int = {
    -1
  }

  def day9part1(input: Array[String]): Int = {
    -1
  }

  def day9part2(input: Array[String]): Int = {
    -1
  }
}
