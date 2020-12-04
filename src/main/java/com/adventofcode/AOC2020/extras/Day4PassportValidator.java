package com.adventofcode.AOC2020.extras;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4PassportValidator {

    public static boolean byrIsValid(int byr) {
        return byr >= 1920 && byr <= 2002;
    }

    public static boolean iyrIsValid(int iyr) {
        return iyr >= 2010 && iyr <= 2020;
    }

    public static boolean eyrIsValid(int eyr) {
        return eyr >= 2020 && eyr <= 2030;
    }

    public static boolean hgtIsValid(String hgt) {
        if (hgt.endsWith("cm")) {
            int cm = Integer.parseInt(hgt.replace("cm", ""));
            return cm >= 150 && cm <= 193;
        } else if (hgt.endsWith("in")) {
            int in = Integer.parseInt(hgt.replace("in", ""));
            return in >= 59 && in <= 70;
        }
        return false;
    }

    public static boolean hclIsValid(String hcl) {
        Matcher m = Pattern.compile("#[0-9a-fA-F]+").matcher(hcl);
        return m.matches();
    }

    public static boolean eclIsValid(String ecl) {
        return Stream
                .of("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                .collect(Collectors.toList())
                .contains(ecl);
    }

    public static boolean pidIsValid(String pid) {
        boolean isValid = true;
        for (char c: pid.toCharArray()) {
            if (!Character.isDigit(c)) {
                isValid = false;
            }
        }
        if (pid.length()!=9) {
            isValid = false;
        }
        return isValid;
    }
}
