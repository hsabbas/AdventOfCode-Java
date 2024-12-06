package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6 {
    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day6/input.txt"))){
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }
        int[] guard = new int[2];
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '^') {
                    guard[0] = row;
                    guard[1] = col;
                }
            }
        }
        int total = 1;
        int[] direction = {-1, 0};

        while (inBounds(grid, guard)) {
            while (inBounds(grid, guard) && grid[guard[0]][guard[1]] != '#') {
                if(grid[guard[0]][guard[1]] == '.'){
                    total++;
                    grid[guard[0]][guard[1]] = 'x';
                }
                guard[0] += direction[0];
                guard[1] += direction[1];
            }

            if(!inBounds(grid, guard)){
                break;
            }

            if(grid[guard[0]][guard[1]] == '#') {
                guard[0] -= direction[0];
                guard[1] -= direction[1];
                changeDirection(direction);
            }
        }
        System.out.println(total);
    }

    private static void changeDirection(int[] direction){
        if (Arrays.equals(direction, new int[]{-1, 0})) {
            direction[0] = 0;
            direction[1] = 1;
        } else if(Arrays.equals(direction, new int[]{0, 1})) {
            direction[0] = 1;
            direction[1] = 0;
        } else if(Arrays.equals(direction, new int[]{1, 0})){
            direction[0] = 0;
            direction[1] = -1;
        } else {
            direction[0] = -1;
            direction[1] = 0;
        }
    }

    private static boolean inBounds(char[][] grid, int[] guard) {
        return guard[0] >= 0 && guard[0] < grid.length && guard[1] >= 0 && guard[1] < grid[guard[0]].length;
    }
}
