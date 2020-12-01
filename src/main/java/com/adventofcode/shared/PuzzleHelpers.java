package com.adventofcode.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
}
