package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day16 {
    static int[][] directions = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    record Coords(int x, int y) {}
    record Step(Coords coords, int direction) {}
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

        List<Set<Coords>> bestPaths = new ArrayList<>();
        int bestScore =
                travelMaze(grid, reindeer, 0,
                        new HashSet<>(List.of(reindeer)),
                        0, Integer.MAX_VALUE,
                        new HashMap<>(), bestPaths);


        Set<Coords> bestSpots = new HashSet<>();
        for(Set<Coords> path : bestPaths) {
            bestSpots.addAll(path);
        }

        System.out.println(bestScore);
        System.out.println(bestSpots.size());
    }

    private static int travelMaze(char[][] grid,
                                  Coords reindeer,
                                  int currDirection,
                                  Set<Coords> currPath,
                                  int sum,
                                  int best,
                                  Map<Step, Integer> memo,
                                  List<Set<Coords>> bestPaths) {
        if(grid[reindeer.y][reindeer.x] == 'E') {
            if(sum == best) {
                Set<Coords> bestPath = new HashSet<>(currPath);
                bestPaths.add(bestPath);
            } else if (sum < best) {
                Set<Coords> bestPath = new HashSet<>(currPath);
                bestPaths.clear();
                bestPaths.add(bestPath);
            }
            return sum;
        }

        if(sum >= best) {
            return Integer.MAX_VALUE;
        }

        memo.put(new Step(reindeer, currDirection), sum);

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < directions.length; i++) {
            Coords next = move(reindeer, directions[i]);
            if(currPath.contains(next) || grid[next.y][next.x] == '#') {
                continue;
            }

            int points;
            Step coordsDir = new Step(next, i);
            if(i == currDirection){
                if(memo.containsKey(coordsDir) && memo.get(coordsDir) < sum + 1) {
                    continue;
                }
                currPath.add(next);
                points = travelMaze(grid, next, i, currPath, sum + 1, best, memo, bestPaths);
            } else {
                if(memo.containsKey(coordsDir) && memo.get(coordsDir) < sum + 1001) {
                    continue;
                }
                currPath.add(next);
                points = travelMaze(grid, next, i, currPath, sum + 1001, best, memo, bestPaths);
            }

            if(points != Integer.MAX_VALUE) {
                min = Math.min(min, points);
                best = Math.min(best, min);
            }

            currPath.remove(next);
        }

        return min;
    }

    private static Coords move(Coords object, int[] direction) {
        return new Coords(object.x + direction[0], object.y + direction[1]);
    }
}
