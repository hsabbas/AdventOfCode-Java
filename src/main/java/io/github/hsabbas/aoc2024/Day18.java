package io.github.hsabbas.aoc2024;

import io.github.hsabbas.aoc2024.common.Coords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static io.github.hsabbas.aoc2024.common.CoordsUtil.inBounds;
import static io.github.hsabbas.aoc2024.common.CoordsUtil.move;

public class Day18 {
    static int[][] directions = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int width = 71;
    static final int height = 71;
    static int partOneBytes = 1024;

    public static void main(String[] args) throws IOException {
        char[][] grid = new char[height][width];
        List<int[]> bytes;
        try(BufferedReader br = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day18/input.txt"))){
            bytes = br.lines().map(line -> Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray()).toList();
        }

        solve1(grid, bytes);
        solve2(grid, bytes);
    }

    private static void solve1(char[][] grid, List<int[]> bytes) {
        for(int i = 0; i < partOneBytes; i++) {
            grid[bytes.get(i)[1]][bytes.get(i)[0]] = '#';
        }

        Map<Coords, Integer> shortestToCoords = new HashMap<>();
        findShortestExit(grid, new Coords(0, 0), 0, new HashSet<>(), shortestToCoords);
        int leastSteps = shortestToCoords.get(new Coords(width - 1, height - 1));
        System.out.println(leastSteps);
    }

    private static void findShortestExit(char[][] grid, Coords current, int steps, Set<Coords> currPath, Map<Coords, Integer> memo) {
        memo.put(current, steps);

        if(current.x() == width - 1 && current.y() == height - 1){
            return;
        }

        for (int[] direction : directions) {
            Coords next = move(current, direction);
            if (!inBounds(grid, next) || grid[next.y()][next.x()] == '#'
                    || (memo.containsKey(next) && memo.get(next) <= steps + 1)
                    || currPath.contains(next)) {
                continue;
            }
            currPath.add(next);
            findShortestExit(grid, next, steps + 1, currPath, memo);
            currPath.remove(next);
        }
    }

    private static void solve2(char[][] grid, List<int[]> bytes) {
        for(int i = partOneBytes; i < bytes.size(); i ++) {
            grid[bytes.get(i)[1]][bytes.get(i)[0]] = '#';

            boolean foundExit = findExit(grid, new Coords(0, 0), new HashSet<>(), new HashSet<>());
            if(!foundExit) {
                System.out.println(bytes.get(i)[0] + "," + bytes.get(i)[1]);
                break;
            }
        }
    }

    private static boolean findExit(char[][] grid, Coords current, Set<Coords> currPath, Set<Coords> deadends) {
        if(current.x() == width - 1 && current.y() == height - 1){
            return true;
        }

        boolean exitFound;
        for (int[] direction : directions) {
            Coords next = move(current, direction);
            if (!inBounds(grid, next) || grid[next.y()][next.x()] == '#'
                    || currPath.contains(next) || deadends.contains(next)) {
                continue;
            }

            currPath.add(next);
            exitFound = findExit(grid, next, currPath, deadends);
            currPath.remove(next);

            if (exitFound) {
                return true;
            }

            deadends.add(next);
        }

        return false;
    }
}
