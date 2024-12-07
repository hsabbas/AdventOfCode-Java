package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day6 {
    record Position(int x, int y){}
    public static void main(String[] args) throws IOException{
        solve();
    }

    private static void solve() throws IOException{
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day6/input.txt"))){
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }

        char direction = 'U';
        Position guard = null;
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '^') {
                    guard = new Position(col, row);
                    grid[row][col] = direction;
                }
            }
        }
        if(guard == null) {
            System.out.println("Could not find guard");
            System.exit(0);
        }

        Position start = new Position(guard.x, guard.y);
        
        int partOne = 1;
        int partTwo = 0;

        while (inBounds(grid, guard)) {
            if(grid[guard.y][guard.x] == '.'){
                partOne++;
                grid[guard.y][guard.x] = direction;
            }

            Position next = getNextPosition(guard, direction);
            if(!inBounds(grid, next)){
                break;
            }
            if(grid[next.y][next.x] == '#'){
                direction = getRightTurn(direction);
                next = getNextPosition(guard, direction);
            }

            guard = next;
        }

        System.out.println(partOne);
        System.out.println(partTwo);
    }

    private static Position getNextPosition(Position position, char direction){
        return switch (direction) {
            case 'U' -> new Position(position.x, position.y - 1);
            case 'R' -> new Position(position.x + 1, position.y);
            case 'D' -> new Position(position.x, position.y + 1);
            default -> new Position(position.x - 1, position.y);
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

    private static boolean inBounds(char[][] grid, Position guard) {
        return guard.y >= 0 && guard.y < grid.length && guard.x >= 0 && guard.x < grid[guard.y].length;
    }
}