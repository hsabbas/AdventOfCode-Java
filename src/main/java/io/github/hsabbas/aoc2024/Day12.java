package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day12 {
    static int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public static void main(String[] args) throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day12/test-input.txt"))){
            grid = reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        }
        solve1(grid);
    }

    private static void solve1(char[][] grid){
        int cost = 0;
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < m; col++) {
                if(!visited[row][col]) {
                    Region region = new Region();
                    findRegion(grid, grid[row][col], row, col, region, visited);
                    cost += region.area * region.perimeter;
                }
            }
        }
        System.out.println(cost);
    }

    private static void findRegion(char[][] grid, char plant, int row, int col, Region region, boolean[][] visited) {
        visited[row][col] = true;
        region.area++;
        region.perimeter += 4;
        for(int[] direction : directions) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if(inBounds(grid, nextRow, nextCol) && grid[nextRow][nextCol] == plant) {
                region.perimeter--;
                if(!visited[nextRow][nextCol]) {
                    findRegion(grid, plant, nextRow, nextCol, region, visited);
                }
            }
        }
    }
    
    private static boolean inBounds(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
    }
}


class Region {
    int area;
    int perimeter;
}