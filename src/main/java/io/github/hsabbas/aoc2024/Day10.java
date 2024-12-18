package io.github.hsabbas.aoc2024;

import io.github.hsabbas.aoc2024.common.Coords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static io.github.hsabbas.aoc2024.common.CoordsUtil.inBounds;

public class Day10 {
    public static void main(String[] args) throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day10/input.txt"))){
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }

        solve1(grid);
        solve2(grid);
    }

    private static void solve1(char[][] grid) {
        int totalScore = 0;
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '0') {
                    totalScore += scoreTrailHead(grid, new Coords(col, row), '0', new HashSet<>());
                }
            }
        }
        System.out.println(totalScore);
    }

    private static int scoreTrailHead(char[][] grid, Coords currPos, char curr, Set<Coords> visited) {
        visited.add(currPos);
        if(curr == '9') {
            return 1;
        }

        int score = 0;
        Coords nextPosition = new Coords(currPos.x() + 1, currPos.y());
        if(inBounds(grid, nextPosition)
                && !visited.contains(nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            score += scoreTrailHead(grid, nextPosition, (char) (curr + 1), visited);
        }

        nextPosition = new Coords(currPos.x(), currPos.y() + 1);
        if(inBounds(grid, nextPosition)
                && !visited.contains(nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            score += scoreTrailHead(grid, nextPosition, (char) (curr + 1), visited);
        }

        nextPosition = new Coords(currPos.x() - 1, currPos.y());
        if(inBounds(grid, nextPosition)
                && !visited.contains(nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            score += scoreTrailHead(grid, nextPosition, (char) (curr + 1), visited);
        }

        nextPosition = new Coords(currPos.x(), currPos.y() - 1);
        if(inBounds(grid, nextPosition)
                && !visited.contains(nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            score += scoreTrailHead(grid, nextPosition, (char) (curr + 1), visited);
        }
        return score;
    }

    private static void solve2(char[][] grid) {
        int totalRating = 0;
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '0') {
                    totalRating += rateTrailHead(grid, new Coords(col, row), '0');
                }
            }
        }
        System.out.println(totalRating);
    }

    private static int rateTrailHead(char[][] grid, Coords currPos, char curr) {
        if(curr == '9') {
            return 1;
        }

        int rating = 0;
        Coords nextPosition = new Coords(currPos.x() + 1, currPos.y());
        if(inBounds(grid, nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            rating += rateTrailHead(grid, nextPosition, (char) (curr + 1));
        }

        nextPosition = new Coords(currPos.x(), currPos.y() + 1);
        if(inBounds(grid, nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            rating += rateTrailHead(grid, nextPosition, (char) (curr + 1));
        }

        nextPosition = new Coords(currPos.x() - 1, currPos.y());
        if(inBounds(grid, nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            rating += rateTrailHead(grid, nextPosition, (char) (curr + 1));
        }

        nextPosition = new Coords(currPos.x(), currPos.y() - 1);
        if(inBounds(grid, nextPosition)
                && grid[nextPosition.y()][nextPosition.x()] == curr + 1){
            rating += rateTrailHead(grid, nextPosition, (char) (curr + 1));
        }
        return rating;
    }
}
