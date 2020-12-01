package com.adventofcode.shared;

import com.adventofcode.AOC2019.PuzzleSolutions;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PuzzleHelpers {

    public static String[] readLinesFromResourceFile(String path) {
        InputStream stream = PuzzleSolutions.class.getResourceAsStream(path);
        String[] lines = {};
        try {
            lines = IOUtils.toString(stream, StandardCharsets.UTF_8).split("\\r?\\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
