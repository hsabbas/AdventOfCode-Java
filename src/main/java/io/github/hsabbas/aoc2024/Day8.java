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
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                if(grid[y][x] == '.'){
                    continue;
                }
                if(!frequencies.containsKey(grid[y][x])) {
                    frequencies.put(grid[y][x], new ArrayList<>());
                }
                frequencies.get(grid[y][x]).add(new Coords(x, y));
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

                    Coords inline = new Coords(second.x - distance.x, second.y - distance.y);
                    if(inBounds(grid, inline) && grid[inline.y][inline.x] != '#'){
                        grid[inline.y][inline.x] = '#';
                        partOne++;
                    }

                    inline = new Coords(first.x + distance.x, first.y + distance.y);
                    if(inBounds(grid, inline) && grid[inline.y][inline.x] != '#'){
                        grid[inline.y][inline.x] = '#';
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

                    Coords inline = new Coords(second.x - distance.x, second.y - distance.y);
                    while(inBounds(grid, inline)){
                        if(grid[inline.y][inline.x] != '#'){
                            grid[inline.y][inline.x] = '#';
                            partTwo++;
                        }
                        inline = new Coords(inline.x - distance.x, inline.y - distance.y);
                    }

                    inline = new Coords(first.x + distance.x, first.y + distance.y);
                    while(inBounds(grid, inline)){
                        if(grid[inline.y][inline.x] != '#'){
                            grid[inline.y][inline.x] = '#';
                            partTwo++;
                        }
                        inline = new Coords(inline.x + distance.x, inline.y + distance.y);
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
