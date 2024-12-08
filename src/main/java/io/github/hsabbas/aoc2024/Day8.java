package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {
    public static void main(String[] args) throws IOException {
        solve1();
    }

    record Coords(int x, int y){}
    private static void solve1() throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day8/input.txt"))){
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }

        Map<Character, List<Coords>> frequencies = new HashMap<>();
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '.'){
                    continue;
                }
                if(!frequencies.containsKey(grid[row][col])) {
                    frequencies.put(grid[row][col], new ArrayList<>());
                }
                frequencies.get(grid[row][col]).add(new Coords(col, row));
            }
        }

        int partOne = 0;
        for(char freq : frequencies.keySet()) {
            List<Coords> positions = frequencies.get(freq);
            for(int i = 0; i < positions.size() - 1; i++) {
                Coords first = positions.get(i);
                for(int j = i + 1; j < positions.size(); j++) {
                    Coords second = positions.get(j);
                    Coords distance = new Coords(first.x - second.x, first.y - second.y);
                    Coords test1 = new Coords(second.x - distance.x, second.y - distance.y);
                    if(inBounds(grid, test1) && grid[test1.y][test1.x] != '#'){
                        grid[test1.y][test1.x] = '#';
                        partOne++;
                    }
                    Coords test2 = new Coords(first.x + distance.x, first.y + distance.y);
                    if(inBounds(grid, test2) && grid[test2.y][test2.x] != '#'){
                        grid[test2.y][test2.x] = '#';
                        partOne++;
                    }
                }
            }
        }
        System.out.println(partOne);

        int partTwo = partOne;
        for(char freq : frequencies.keySet()) {
            List<Coords> positions = frequencies.get(freq);
            for(int i = 0; i < positions.size() - 1; i++) {
                Coords first = positions.get(i);
                for(int j = i + 1; j < positions.size(); j++) {
                    Coords second = positions.get(j);
                    if(grid[second.y][second.x] != '#') {
                        grid[second.y][second.x] = '#';
                        partTwo++;
                    }
                    if(grid[first.y][first.x] != '#') {
                        grid[first.y][first.x] = '#';
                        partTwo++;
                    }
                    Coords distance = new Coords(first.x - second.x, first.y - second.y);
                    Coords test1 = new Coords(second.x - distance.x, second.y - distance.y);
                    while(inBounds(grid, test1)){
                        if(grid[test1.y][test1.x] != '#'){
                            grid[test1.y][test1.x] = '#';
                            partTwo++;
                        }
                        test1 = new Coords(test1.x - distance.x, test1.y - distance.y);
                    }
                    Coords test2 = new Coords(first.x + distance.x, first.y + distance.y);
                    while(inBounds(grid, test2)){
                        if(grid[test2.y][test2.x] != '#'){
                            grid[test2.y][test2.x] = '#';
                            partTwo++;
                        }
                        test2 = new Coords(test2.x + distance.x, test2.y + distance.y);
                    }
                }
            }
        }
        System.out.println(partTwo);
    }

    private static boolean inBounds(char[][] grid, Coords position) {
        return position.y >= 0 && position.y < grid.length && position.x >= 0 && position.x < grid[position.y].length;
    }
}
