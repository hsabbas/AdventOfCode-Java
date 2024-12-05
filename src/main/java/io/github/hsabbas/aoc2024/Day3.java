package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day3/input.txt"));
        String input = bufferedReader.lines().collect(Collectors.joining());

        int total = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(input)
                .results()
                .mapToInt(result -> Integer.parseInt(result.group(1)) * Integer.parseInt(result.group(2)))
                .sum();
        System.out.println(total);

        String input2 = input.replaceAll("don't\\(\\).*?(do\\(\\)|$)", "");
        long total2 = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(input2)
                .results()
                .mapToInt(result -> Integer.parseInt(result.group(1)) * Integer.parseInt(result.group(2)))
                .sum();
        System.out.println(total2);
    }
}
