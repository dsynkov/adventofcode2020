package com.adventofcode.AOC2020;

import com.adventofcode.shared.PuzzleHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.adventofcode.AOC2020.PuzzleConstants.PUZZLE_1_INPUT;

public class PuzzleSolutions {

    public static void main(String[] args) {

        String[] p1lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_1_INPUT);

        System.out.printf("Day #1 part #1: %d\n", day1part1(p1lines));
        System.out.printf("Day #1 part #2: %d\n", day1part2(p1lines));
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
}
