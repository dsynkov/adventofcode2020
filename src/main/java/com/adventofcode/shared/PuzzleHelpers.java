package com.adventofcode.shared;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PuzzleHelpers {

    public static String[] readLinesFromInputFile(String path) {
        String[] lines = {};
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            lines = br.lines().toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<String> readDay4LinesFromInputFile(String path) {
        List<String> passports = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            String fileAsString = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
            String[] lines = fileAsString.split("\r\n\r\n");
            for (String line: lines) {
                String passport = String.join(" ", line.split("\r\n"));
                passports.add(passport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passports;
    }
}
