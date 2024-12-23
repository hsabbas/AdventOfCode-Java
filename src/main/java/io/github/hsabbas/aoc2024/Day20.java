package io.github.hsabbas.aoc2024;

import io.github.hsabbas.aoc2024.common.Coords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.hsabbas.aoc2024.common.CoordsUtil.*;

public class Day20 {
    public static int[][] cheatMoves = new int[][]{
            {-1, -1}, {1, -1}, {1, 1}, {-1, 1},
            {-2, 0}, {2, 0}, {0, -2}, {0, 2}
    };

    public static void main(String[] args) throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day20/input.txt"))){
            grid = reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        }

        Coords end = null;

        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == 'E') {
                    end = new Coords(col, row);
                    break;
                }
            }
            if(end != null) {
                break;
            }
        }
        assert end != null;

        Map<Coords, Integer> distanceLeft = new HashMap<>();
        List<Coords> track = new ArrayList<>();
        Coords next = end;
        Coords current = end;
        int distance = 0;

        while(grid[next.y()][next.x()] != 'S') {
            track.add(current);
            distanceLeft.put(current, distance);
            for (int[] direction : directions) {
                next = move(current, direction);
                if (!inBounds(grid, next) || distanceLeft.containsKey(next) || grid[next.y()][next.x()] == '#') {
                    continue;
                }
                distance++;
                current = next;
                break;
            }
        }
        track.add(current);
        distanceLeft.put(current, distance);

        solve1(distanceLeft);
        solve2(distanceLeft, track);
    }

    private static void solve1(Map<Coords, Integer> distanceLeft){
        Coords next;
        int count = 0;
        for(Coords coords : distanceLeft.keySet()) {
            for(int[] cheatMove : cheatMoves) {
                next = move(coords, cheatMove);
                if(!distanceLeft.containsKey(next)){
                    continue;
                }
                if(distanceLeft.get(coords) - distanceLeft.get(next) >= 102) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static void solve2(Map<Coords, Integer> distanceLeft, List<Coords> track) {
        int count = 0;

        for(int i = track.size() - 1; i > 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                Coords cheatS = track.get(i);
                Coords cheatE = track.get(j);
                int picos = Math.abs(cheatS.x() - cheatE.x()) + Math.abs(cheatS.y() - cheatE.y());
                if(picos > 20){
                    continue;
                }
                if(distanceLeft.get(cheatS) - distanceLeft.get(cheatE) - picos >= 100) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
