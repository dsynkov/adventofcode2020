package com.adventofcode.AOC2019;

import com.adventofcode.shared.PuzzleHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.adventofcode.AOC2019.PuzzleConstants.*;

public class PuzzleSolutions {

    public static void main(String[] args) {
        String[] p1lines = PuzzleHelpers.readLinesFromResourceFile(PUZZLE_1_INPUT);
        String[] p2lines = PuzzleHelpers.readLinesFromResourceFile(PUZZLE_2_INPUT);

        System.out.printf("Day #1 part #1: %d\n", day1part1(p1lines));
        System.out.printf("Day #1 part #2: %d\n", day1part2(p1lines));

        System.out.printf("Day #2 part #1: %d\n", day2part1(p2lines));
        System.out.printf("Day #2 part #2: %s\n", day2part2(p2lines));
    }

    private static int day1part1(String[] input) {
        return Arrays.stream(input).mapToInt(Integer::parseInt).sum();
    }

    private static int day1part2(String[] input) {
        List<Integer> nums = Arrays
                .stream(input)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        ArrayList<Integer> frequencies = new ArrayList<>();
        int sum = 0;
        while (true) {
            for (Integer i : nums) {
                sum = sum + i;
                if (frequencies.contains(sum)) {
                    return sum;
                }
                frequencies.add(sum);
            }
        }
    }

    private static int day2part1(String[] input) {
        int hasTwoCounter = 0;
        int hasThreeCounter = 0;
        for (String line : input) {
            if (checkForOccurrences(line, 2)) {
                hasTwoCounter++;
            }
            if (checkForOccurrences(line, 3)) {
                hasThreeCounter++;
            }
        }
        return hasTwoCounter * hasThreeCounter;
    }

    private static String day2part2(String[] input) {
        for (String left: input) {
            for (String right: input) {
                String singleCharDiff = getSingleCharDiff(left, right, 1);
                if (singleCharDiff != null) {
                    return singleCharDiff;
                }
            }
        }
        return null;
    }

    private static String getSingleCharDiff(String left, String right, int target) {
        int diffNum = 0;
        char[] leftArr = left.toCharArray();
        char[] rightArr = right.toCharArray();
        int diffCharIndex = 0;
        for (int i = 0; i < left.toCharArray().length; i++) {
            if (leftArr[i] != rightArr[i]) {
                diffCharIndex = i;
                diffNum++;
            }
        }
        if (diffNum==target) {
            StringBuilder sb = new StringBuilder(left);
            return sb.deleteCharAt(diffCharIndex).toString();
        }
        return null;
    }

    private static boolean checkForOccurrences(String letters, int target) {
        for (char c : letters.toCharArray()) {
            long numOccurrences = letters.chars().filter(letter -> letter == c).count();
            if (numOccurrences == target) {
                return true;
            }
        }
        return false;
    }
}
