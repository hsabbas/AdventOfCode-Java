package io.github.hsabbas.aoc2024;

import io.github.hsabbas.aoc2024.common.Coords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static io.github.hsabbas.aoc2024.common.CoordsUtil.inBounds;

public class Day6 {
    record CoordsAndDir(Coords coords, char d) {}
    public static void main(String[] args) throws IOException{
        solve();
    }

    private static void solve() throws IOException{
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day6/input.txt"))){
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }

        char direction = 'U';
        Coords guard = null;
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '^') {
                    guard = new Coords(col, row);
                }
            }
        }
        if(guard == null) {
            System.out.println("Could not find guard");
            System.exit(0);
        }

        Coords start = new Coords(guard.x(), guard.y());
        Set<Coords> visited = new HashSet<>();
        int partOne = 1;
        int partTwo = 0;
        while (inBounds(grid, guard)) {
            visited.add(guard);
            if(grid[guard.y()][guard.x()] == '.'){
                partOne++;
                grid[guard.y()][guard.x()] = 'x';
            }

            Coords next = getNextCoords(guard, direction);
            if(!inBounds(grid, next)){
                break;
            }
            while (grid[next.y()][next.x()] == '#'){
                direction = getRightTurn(direction);
                next = getNextCoords(guard, direction);
            }

            guard = next;
        }

        for(Coords obstacle : visited) {
            grid[obstacle.y()][obstacle.x()] = '#';
            Coords testGuard = start;
            direction = 'U';
            Set<CoordsAndDir> path = new HashSet<>();
            while(inBounds(grid, testGuard)){
                CoordsAndDir current = new CoordsAndDir(new Coords(testGuard.x(), testGuard.y()), direction);
                if(path.contains(current)){
                    partTwo++;
                    break;
                }
                path.add(current);
                Coords next = getNextCoords(testGuard, direction);

                if(!inBounds(grid, next)){
                    break;
                }

                while(grid[next.y()][next.x()] == '#') {
                    direction = getRightTurn(direction);
                    next = getNextCoords(testGuard, direction);
                }
                testGuard = next;
            }
            grid[obstacle.y()][obstacle.x()] = '.';
        }

        System.out.println(partOne);
        System.out.println(partTwo);
    }

    private static Coords getNextCoords(Coords coords, char direction){
        return switch (direction) {
            case 'U' -> new Coords(coords.x(), coords.y() - 1);
            case 'R' -> new Coords(coords.x() + 1, coords.y());
            case 'D' -> new Coords(coords.x(), coords.y() + 1);
            default -> new Coords(coords.x() - 1, coords.y());
        };
    }

    private static char getRightTurn(char direction){
        return switch (direction) {
            case 'U' -> 'R';
            case 'R' -> 'D';
            case 'D' -> 'L';
            default -> 'U';
        };
    }
}