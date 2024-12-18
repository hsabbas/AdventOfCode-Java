package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day16 {
    static int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    record Coords(int x, int y) {}
    public static void main(String[] args) throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day16/input.txt"))){
            grid = reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        }

        Coords reindeer = null;
        for(int row = grid.length - 1; row > 0; row--) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == 'S') {
                    reindeer = new Coords(col, row);
                    break;
                }
            }
            if(reindeer != null) {
                break;
            }
        }
        assert reindeer != null;

        System.out.println(travelMaze(grid, reindeer, 0, new HashSet<>(List.of(reindeer)), 0, new HashMap<>()));
    }

    private static int travelMaze(char[][] grid, Coords reindeer, int currDirection, Set<Coords> currPath, int sum, Map<Coords, Integer> memo) {
        if(grid[reindeer.y][reindeer.x] == 'E') {
            return sum;
        }

        if(memo.containsKey(reindeer) && memo.get(reindeer) < sum) {
            return Integer.MAX_VALUE;
        }

        memo.put(reindeer, sum);

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < directions.length; i++) {
            Coords next = move(reindeer, directions[i]);
            if(currPath.contains(next) || grid[next.y][next.x] == '#') {
                continue;
            }
            currPath.add(next);

            int points;
            if(i == currDirection){
                points = travelMaze(grid, next, i, currPath, sum + 1, memo);
            } else {
                points = travelMaze(grid, next, i, currPath, sum + 1001, memo);
            }

            if(points == Integer.MAX_VALUE) {
                currPath.remove(next);
                continue;
            }
            min = Math.min(min, points);

            currPath.remove(next);
        }

        return min;
    }

    private static Coords move(Coords object, int[] direction) {
        return new Coords(object.x + direction[0], object.y + direction[1]);
    }
}
