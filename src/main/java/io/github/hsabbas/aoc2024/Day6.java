package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6 {
    public static void main(String[] args) throws IOException{
        solve();
    }

    private static void solve() throws IOException{
        String[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day6/input.txt"))){
            grid = reader.lines().map(line -> line.split("")).toArray(String[][]::new);
        }
        String direction = "U";
        int[] guard = new int[2];
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col].equals("^")) {
                    guard[0] = row;
                    guard[1] = col;
                    grid[row][col] += direction;
                }
            }
        }
        int total = 1;
        int total2 = 0;

        while (inBounds(grid, guard)) {
            while (inBounds(grid, guard) && !grid[guard[0]][guard[1]].equals("#")) {
                if(grid[guard[0]][guard[1]].equals(".")){
                    total++;
                    grid[guard[0]][guard[1]] += direction;
                }

                int[] loopCheck = {guard[0], guard[1]};
                String checkDirection = getRightTurn(direction);
                moveGuard(loopCheck, checkDirection);
                while(inBounds(grid, loopCheck) && !grid[loopCheck[0]][loopCheck[1]].equals("#")){
                    if(grid[loopCheck[0]][loopCheck[1]].contains(checkDirection)){
                        total2++;
                        break;
                    }
                    moveGuard(loopCheck, checkDirection);
                }

                moveGuard(guard, direction);
            }

            if(!inBounds(grid, guard)){
                break;
            }

            if(grid[guard[0]][guard[1]].equals("#")) {
                moveGuard(guard, getRightTurn(getRightTurn(direction)));
                direction = getRightTurn(direction);
            }
        }
        System.out.println(total);
        System.out.println(total2);
    }

    private static void moveGuard(int[] guard, String direction) {
        switch (direction) {
            case "U":
                guard[0]--;
                break;
            case "R":
                guard[1]++;
                break;
            case "D":
                guard[0]++;
                break;
            case "L":
                guard[1]--;
                break;
        }
    }

    private static String getRightTurn(String direction){
        return switch (direction) {
            case "U" -> "R";
            case "R" -> "D";
            case "D" -> "L";
            default -> "U";
        };
    }

    private static boolean inBounds(String [][] grid, int[] guard) {
        return guard[0] >= 0 && guard[0] < grid.length && guard[1] >= 0 && guard[1] < grid[guard[0]].length;
    }
}
