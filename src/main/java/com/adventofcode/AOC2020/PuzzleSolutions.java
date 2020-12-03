package com.adventofcode.AOC2020;

import com.adventofcode.shared.PuzzleHelpers;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.adventofcode.AOC2020.PuzzleConstants.PUZZLE_1_INPUT;
import static com.adventofcode.AOC2020.PuzzleConstants.PUZZLE_2_INPUT;
import static com.adventofcode.AOC2020.PuzzleConstants.PUZZLE_3_INPUT;

public class PuzzleSolutions {

    public static void main(String[] args) {
        String[] p1lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_1_INPUT);
        System.out.printf("Day #1 part #1: %d\n", day1part1(p1lines));
        System.out.printf("Day #1 part #2: %d\n", day1part2(p1lines));

        String[] p2lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_2_INPUT);
        int[] day2result = day2(p2lines);
        System.out.printf("Day #2 part #1: %d\n", day2result[0]);
        System.out.printf("Day #2 part #2: %d\n", day2result[1]);

        String[] p3lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_3_INPUT);
        System.out.printf("Day #3 part #1: %d\n", day3part1(p3lines));
        System.out.printf("Day #3 part #2: %d\n", day3part2(p3lines));
    }

    private static int day1part1(String[] input) {
        List<Integer> nums = Arrays
                .stream(input)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        for (int left: nums) {
            for (int right: nums) {
                if (left + right == 2020) {
                    return left * right;
                }
            }
        }
        return -1;
    }

    private static int day1part2(String[] input) {
        List<Integer> nums = Arrays
                .stream(input)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        for (int left: nums) {
            for (int inner: nums) {
                for (int right : nums) {
                    if (left + inner + right == 2020) {
                        return left * inner * right;
                    }
                }
            }
        }
        return -1;
    }

    private static int[] day2(String[] input) {

        int part1Counter = 0;
        int part2Counter = 0;

        for (String line: input) {

            String[] strParts = line.split(":");
            String[] ruleParts = strParts[0].split(" ");
            String[] numParts = ruleParts[0].split("-");

            int minNum = Integer.parseInt(numParts[0].trim());
            int maxNum = Integer.parseInt(numParts[1].trim());

            String letter = ruleParts[1].trim();
            String pWord = strParts[1].trim();

            int letterMatches = StringUtils.countMatches(pWord, letter);

            Range<Integer> numRange = Range.between(minNum, maxNum);

            if (numRange.contains(letterMatches)) {
                part1Counter++;
            }

            // Take into account 1-based indexing
            boolean charAtMin = pWord.charAt(minNum-1)==letter.charAt(0);
            boolean charAtMax = pWord.charAt(maxNum-1)==letter.charAt(0);

            if ((charAtMin && !charAtMax) || (!charAtMin && charAtMax)) {
                part2Counter++;
            }
        }
        return new int[] {part1Counter, part2Counter};
    }

    private static int day3part1(String[] input) {
        return -1;
    }

    private static int day3part2(String[] input) {
        return -1;
    }
}
