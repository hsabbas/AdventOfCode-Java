package io.github.hsabbas.aoc2024.common;

public class GridUtils {
    public static void printGrid(char[][] grid) {
        for (char[] chars : grid) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
