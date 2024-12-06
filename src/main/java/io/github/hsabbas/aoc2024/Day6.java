package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day6 {
    public static void main(String[] args) throws IOException{
        solve();
    }

    private static void solve() throws IOException{
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day6/input.txt"))){
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }

        char direction = 'U';
        Position guard = new Position();
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '^') {
                    guard.y = row;
                    guard.x = col;
                    grid[row][col] = direction;
                }
            }
        }
        
        int partOne = 1;
        int partTwo = 0;

        Map<Position, Set<Character>> visited = new HashMap<>();

        while (inBounds(grid, guard)) {
            if(grid[guard.y][guard.x] == '.'){
                partOne++;
                grid[guard.y][guard.x] = direction;
            }

            char loopCheckDir = getRightTurn(direction);
            Position loopCheckPos = getNextPosition(guard, loopCheckDir);
            while (inBounds(grid, loopCheckPos) && grid[loopCheckPos.y][loopCheckPos.x] != '#'){
                boolean working = true;
                for(Position test : visited.keySet()){
                    if(loopCheckPos.equals(test)){
                        //How could this be false?
                        working = visited.containsKey(loopCheckPos);
                        break;
                    }
                }
                if(!working){
                    System.out.println(working);
                }
                if(visited.containsKey(loopCheckPos) && visited.get(loopCheckPos).contains(loopCheckDir)){
                    System.out.println("Test");
                    partTwo++;
                    break;
                }
                loopCheckPos = getNextPosition(loopCheckPos, loopCheckDir);
            }


            if(!visited.containsKey(guard)){
                visited.put(guard, new HashSet<>());
            }
            visited.get(guard).add(direction);

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

class Position{
    public int x = 0, y = 0;

    public Position() {}

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != Position.class){
            return false;
        }
        return equals((Position) obj);
    }

    public boolean equals(Position other) {
        return x == other.x && y == other.y;
    }
}