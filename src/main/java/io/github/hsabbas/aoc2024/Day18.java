package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day18 {
    static int[][] directions = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int width = 71;
    static final int height = 71;

    record Coords(int x, int y) {}

    public static void main(String[] args) throws IOException {
        int partOneBytes = 1024;
        char[][] grid = new char[height][width];
        try(BufferedReader br = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day18/input.txt"))){
            for(int i = 0; i < partOneBytes; i++) {
                int[] coordinates = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
                grid[coordinates[1]][coordinates[0]] = '#';
            }
        }

        Map<Coords, Integer> shortestToCoords = new HashMap<>();
        findExit(grid, new Coords(0, 0), 0, new HashSet<>(), shortestToCoords);
        int leastSteps = shortestToCoords.get(new Coords(width - 1, height - 1));
        System.out.println(leastSteps);
    }

    private static void findExit(char[][] grid, Coords current, int steps, Set<Coords> currPath, Map<Coords, Integer> memo) {
        memo.put(current, steps);

        if(current.x == width - 1 && current.y == height - 1){
            return;
        }

        for (int[] direction : directions) {
            Coords next = move(current, direction);
            if (!inBounds(grid, next) || grid[next.y][next.x] == '#'
                    || (memo.containsKey(next) && memo.get(next) <= steps + 1)) {
                continue;
            }
            currPath.add(next);
            findExit(grid, next, steps + 1, currPath, memo);
            currPath.remove(next);
        }
    }

    private static Coords move(Coords object, int[] direction) {
        return new Coords(object.x + direction[0], object.y + direction[1]);
    }

    private static boolean inBounds(char[][] grid, Coords position) {
        return position.y >= 0 && position.y < grid.length && position.x >= 0 && position.x < grid[position.y].length;
    }
}
