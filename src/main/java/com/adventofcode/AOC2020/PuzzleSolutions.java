package com.adventofcode.AOC2020;

import com.adventofcode.AOC2020.extras.Day4PassportValidator;
import com.adventofcode.shared.PuzzleHelpers;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.adventofcode.AOC2020.PuzzleConstants.*;

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
        System.out.printf("Day #3 part #1: %d\n", day3part1(p3lines, 3, 1));
        System.out.printf("Day #3 part #2: %d\n", day3part2(p3lines));

        List<String> p4lines = PuzzleHelpers.readLinesWithSplitOnBlankFromInputFile(PUZZLE_4_INPUT);
        System.out.printf("Day #4 part #1: %d\n", day4part1(p4lines));
        System.out.printf("Day #4 part #2: %d\n", day4part2(p4lines));

        String[] p5lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_5_INPUT);
        List<Integer> seatIds = day5part1(p5lines);
        System.out.printf("Day #5 part #1: %d\n", Collections.max(seatIds));
        System.out.printf("Day #5 part #2: %d\n", day5part2(seatIds));

        List<String> p6lines = PuzzleHelpers.readLinesWithSplitOnBlankFromInputFile(PUZZLE_6_INPUT);
        System.out.printf("Day #6 part #1: %d\n", day6part1(p6lines));
        System.out.printf("Day #6 part #2: %d\n", day6part2(p6lines));

        String[] p7lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_7_INPUT);
        System.out.printf("Day #7 part #1: %d\n", day7part1(p7lines));
        System.out.printf("Day #7 part #2: %d\n", day7part2(p7lines));

        String[] p8lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_8_INPUT);
        System.out.printf("Day #8 part #1: %d\n", day8part1(p8lines));
        System.out.printf("Day #8 part #2: %d\n", day8part2(p8lines));

        String[] p9lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_9_INPUT);
        System.out.printf("Day #9 part #1: %d\n", day9part1(p9lines));
        System.out.printf("Day #9 part #2: %d\n", day9part2(p9lines));

        String[] p10lines = PuzzleHelpers.readLinesFromInputFile(PUZZLE_10_INPUT);
        System.out.printf("Day #10 part #1: %d\n", day10part1(p10lines));
        System.out.printf("Day #10 part #2: %d\n", day10part2(p10lines));
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

    private static Long day3part1(String[] input, int right, int down) {

        Long treeCounter = 0L;

        String dupPath;

        for (int i=down, k=1; i < input.length; i += down, k++) {

            String path = input[i];

            // Since path is indefinite, duplicate
            dupPath = StringUtils.repeat(path, i +1);

            int moveRightBy = right == 1 ? k : right * i;
            char charAtPos = dupPath.charAt(moveRightBy);
            boolean isTree = charAtPos=='#';

            StringBuilder sb = new StringBuilder(dupPath);

            if (isTree) {
                treeCounter++;
                sb.setCharAt(moveRightBy, 'X');
                dupPath = sb.toString();
            }

//            System.out.printf("Index: %-10d Right By: %-10d Tree: %-10s %s\n", i, moveRightBy, isTree, dupPath);
        }

        return treeCounter;
    }

    private static long day3part2(String[] input) {
        int[][] slopes = {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        List<Long> treeCounts = new ArrayList<>();
        for (int[] slope : slopes) {
            treeCounts.add(day3part1(input, slope[0], slope[1]));
        }
        return treeCounts.stream().reduce(1L, (x, y) -> (x * y));
    }

    /**
     * Field "cid":" is excluded since
     * we can treat it as optional.
     */
    private static int day4part1(List<String> input) {
        String[] expectedFields = {"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:"};
        int valid = input.size();
        for (String line: input) {
            for (String field: expectedFields) {
                if (!line.contains(field)) {
                    valid--;
                    break;
                }
            }
        }
        return valid;
    }

    private static int day4part2(List<String> input) {

        String[] expectedFields = {"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:"};

        int valid = 0;

        for (String line: input) {

            String[] fields = line.split(" ");

            boolean isValid = true;

            // Skip if missing fields
            if (fields.length < 7) continue;
            for (String field: expectedFields) {
                if (!line.contains(field)) {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) continue;;

            List<Boolean> validations = new ArrayList<>();

            for (String field: fields) {

                String[] fieldParts = field.split(":");
                String prefix = fieldParts[0];
                String value = fieldParts[1];

                switch(prefix) {
                    case "byr":
                        isValid = Day4PassportValidator.byrIsValid(Integer.parseInt(value));
                        break;
                    case "iyr":
                        isValid = Day4PassportValidator.iyrIsValid(Integer.parseInt(value));
                        break;
                    case "eyr":
                        isValid = Day4PassportValidator.eyrIsValid(Integer.parseInt(value));
                        break;
                    case "hgt":
                        isValid = Day4PassportValidator.hgtIsValid(value);
                        break;
                    case "hcl":
                        isValid = Day4PassportValidator.hclIsValid(value);
                        break;
                    case "ecl":
                        isValid = Day4PassportValidator.eclIsValid(value);
                        break;
                    case "pid":
                        isValid = Day4PassportValidator.pidIsValid(value);
                        break;
                    case "cid":
                        break;
                    default:
                        validations.add(false);
                        break;
                }
                validations.add(isValid);
            }
            if (!validations.contains(false)) {
                valid++;
            }
        }
        return valid;
    }

    private static List<Integer> day5part1(String[] input) {
        List<Integer> seatIds = new ArrayList<>();
        for (String boardingPass: input) {
            int row = searchSeat(boardingPass.substring(0, 7), 'B', 0, 127);
            int col = searchSeat(boardingPass.substring(7, 10), 'R', 0, 7);
            seatIds.add(row * 8 + col);
        }
        return seatIds;
    }

    private static int day5part2(List<Integer> seatIds) {
        int minSeat = Collections.min(seatIds);
        int maxSeat = Collections.max(seatIds);
        return IntStream
                .range(minSeat, maxSeat)
                .filter(i -> !seatIds.contains(i))
                .toArray()[0];
    }

    private static int searchSeat(String boardingPass, char upperChar, double x, double y) {
        double mid = x / 2 + y / 2;
        for (char c: boardingPass.toCharArray()) {
            if (c == upperChar) {
                x = Math.ceil(mid);
                mid = x / 2 + Math.floor(y) / 2;
            } else {
                y = mid;
                mid = x / 2 + y / 2;
            }
        }
        return (int) Math.round(mid);
    }

    private static int day6part1(List<String> input) {
        return input.stream().map(s -> s
                .replaceAll("\\s+", "")
                .chars().boxed().collect(Collectors.toSet()).size())
                .mapToInt(Integer::intValue).sum();
    }

    private static long day6part2(List<String> input) {
        return input.stream()
                .map(group -> group.split("\\s+"))
                .mapToLong(passengers -> {
                    Set<Integer> charSet = passengers[0]
                            .chars().boxed()
                            .collect(Collectors.toSet());
                    Arrays.stream(passengers)
                            .forEach(p -> charSet.retainAll(p.chars().boxed().collect(Collectors.toSet())));
                    return charSet.size();
                }).sum();
    }

    private static int day7part1(String[] input) {
        Map<String, List<Map<String, Integer>>> bagMap = getBagMap(input);
        return getColorCount("shiny gold", bagMap, new ArrayList<>());
    }

    private static int day7part2(String[] input) {
        Map<String, List<Map<String, Integer>>> bagMap = getBagMap(input);
        return getBagCount("shiny gold", bagMap) - 1;
    }

    private static Map<String, List<Map<String, Integer>>> getBagMap(String[] input) {
        Map<String, List<Map<String, Integer>>> bagMap = new HashMap<>();
        String spaceDelim = " ";
        for (String line: input) {
            String rootColor = String.join(spaceDelim, Arrays.copyOfRange(line.split(spaceDelim), 0, 2));
            String[] innerColors = line.split("contain")[1].trim().split(",");
            List<Map<String, Integer>> innerList = new ArrayList<>();
            if (!innerColors[0].startsWith("no")) {
                for (String innerColor: innerColors) {
                    String[] innerSplit = innerColor.trim().split(spaceDelim);
                    Integer innerNum = Integer.parseInt(innerSplit[0]);
                    String innerName = String.join(spaceDelim, Arrays.copyOfRange(innerSplit, 1, 3));
                    Map<String, Integer> innerMap = new HashMap<>();
                    innerMap.put(innerName, innerNum);
                    innerList.add(innerMap);
                }
            }
            bagMap.put(rootColor, innerList);
        }
        return bagMap;
    }

    private static int getColorCount(String color, Map<String, List<Map<String, Integer>>> bagMap, List<String> bagList) {
        for (Map.Entry<String, List<Map<String, Integer>>> entry: bagMap.entrySet()) {
            String nextColor = entry.getKey();
            List<Map<String, Integer>> bags = entry.getValue();
            for (Map<String, Integer> bag : bags) {
                if (bag.keySet().contains(color)) {
                    bagList.add(nextColor);
                    getColorCount(nextColor, bagMap, bagList);
                }
            }
        }
        return new HashSet<>(bagList).size();
    }

    /**
     * TODO: Revisit initial attempt at DFS solution.
     * See https://github.com/VitaminJai/AOC2020/blob/main/Day7/day7.2.py#L12-L19
     * for Python solution.
     */
    private static int getBagCount(String color, Map<String, List<Map<String, Integer>>> bagMap) {
        int total = 1;
        if (bagMap.get(color).isEmpty()) {
            return 1;
        }
        for (Map<String, Integer> bag : bagMap.get(color)) {
            for (Map.Entry<String, Integer> entry: bag.entrySet()) {
                String nextColor = entry.getKey();
                Integer count = entry.getValue();
                total += count * getBagCount(nextColor, bagMap);
            }
        }
        return total;
    }

    private static int day8part1(String[] input) {

        int accumulator = 0;
        Set<Map<Integer, String>> seen = new HashSet<>();

        for (int i = 0; i < input.length;) {

            String instruction = input[i];
            String[] split = instruction.split("\\s+");
            String op = split[0];
            int value = Integer.parseInt(split[1]);

            Map<Integer, String> instructionMap = new HashMap<>();
            instructionMap.put(i, instruction);

            if (seen.contains(instructionMap)) {
                break;
            }

            if (op.equals("acc")) {
                accumulator += value;
                i++;
            } else if (op.equals("jmp")) {
                i += value;
            } else { // "nop" assumed
                i++;
                continue;
            }
            seen.add(instructionMap);
        }
        return accumulator;
    }

    private static int day8part2(String[] input) {
        return findBrokenInstruction(input, input, 0);
    }

    private static int findBrokenInstruction(String[] runningInput, String[] originalInput, int indexOfLastJmpOrNop) {

        int accumulator = 0;
        Set<Map<Integer, String>> seen = new HashSet<>();

        for (int i = 0; i < runningInput.length;) {

            String instruction = runningInput[i];
            String[] split = instruction.split("\\s+");
            String op = split[0];
            int value = Integer.parseInt(split[1]);

            Map<Integer, String> instructionMap = new HashMap<>();
            instructionMap.put(i, instruction);

            if (seen.contains(instructionMap)) {
                System.out.printf("Infinite loop detected with instruction: %s\n", instructionMap);
                String[] remainingInputArray = Arrays.copyOfRange(runningInput, indexOfLastJmpOrNop, runningInput.length);
                System.out.printf("Remaining input arrays is %s\n", remainingInputArray.length);

                String lastJmpOrNop = Arrays
                        .stream(remainingInputArray)
                        .filter(s -> s.startsWith("nop") || s.startsWith("jmp"))
                        .findFirst()
                        .get();

                String replacementInstruction;
                if (lastJmpOrNop.startsWith("jmp")) {
                    replacementInstruction = lastJmpOrNop.replace("jmp", "nop");
                } else {
                    replacementInstruction = lastJmpOrNop.replace("nop", "jmp");
                }

                System.out.printf("Last 'jmp' or 'nop' is '%s' to be replaced with '%s'\n", lastJmpOrNop, replacementInstruction);

                int offset = runningInput.length - remainingInputArray.length;
                indexOfLastJmpOrNop = offset + ArrayUtils.indexOf(remainingInputArray, lastJmpOrNop);
                System.out.printf("Index of last instruction is %d\n", indexOfLastJmpOrNop);

                String[] newInput = originalInput.clone();
                newInput[indexOfLastJmpOrNop] = replacementInstruction;
                return findBrokenInstruction(newInput, originalInput, indexOfLastJmpOrNop + 1);
            }

            String formatString = "Instruction: %s, Op: %s, Acc: %d Index: %s\n";

            if (op.equals("acc")) {
                accumulator += value;
                i++;
                System.out.printf(formatString, instruction, op, accumulator, i);
            } else if (op.equals("jmp")) {
                i += value;
                System.out.printf(formatString, instruction, op, accumulator, i);
            } else {
                i++;
                System.out.printf(formatString, instruction, op, accumulator, i);
                continue;
            }
            seen.add(instructionMap);
        }
        return accumulator;
    }

    /**
     * Revisit solution with two-pointer technique?
     */
    private static BigInteger day9part1(String[] input) {
        List<BigInteger> numbers = Arrays
                .stream(input)
                .map(BigInteger::new)
                .collect(Collectors.toList());
        int preambleSize = 25;
        for (int i = 0; i < numbers.size() - preambleSize; i++) {
            BigInteger target = numbers.get(i + preambleSize);
            List<BigInteger> subList = numbers.subList(i, preambleSize + i);
            if (!isTwoSum(subList, target)) {
                return target;
            }
        }
        return BigInteger.ONE;
    }
    private static BigInteger day9part2(String[] input) {
        List<BigInteger> numbers = Arrays
                .stream(input)
                .map(BigInteger::new)
                .collect(Collectors.toList());
        BigInteger invalidNum = day9part1(input);
        for (int i = 0; i < numbers.size(); i++) {
            // `j` is +2 of `i` since it's the ending range; this
            // way there will always be 2 numbers to sum in the list
            for (int j = i + 2; j < numbers.size(); j++) {
                List<BigInteger> numbersSubset = numbers.subList(i, j);
                BigInteger sum = numbersSubset.stream().reduce(BigInteger::add).orElse(BigInteger.ZERO);
//                System.out.printf("I: %d, J: %d, SUM: %-10d SUBSET: %s\n", i, j, sum, numbersSubset);
                if (sum.equals(invalidNum)) {
                    numbersSubset.sort(Comparator.comparing(BigInteger::intValue));
                    return numbersSubset.get(0).add(numbersSubset.get(numbersSubset.size()-1));
                }
            }
        }
        return BigInteger.ONE;
    }
    private static boolean isTwoSum(List<BigInteger> numbers, BigInteger target) {
        Set<BigInteger> set = new HashSet<>();
        for (BigInteger number : numbers) {
            BigInteger complement = target.subtract(number);
            if (set.contains(complement)) {
                return true;
            } else {
                set.add(number);
            }
        }
        return false;
    }

    private static int day10part1(String[] input) {
        return -1;
    }

    private static int day10part2(String[] input) {
        return -1;
    }
}
