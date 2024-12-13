package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {
    record Coords(int x, int y) {}
    static int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public static void main(String[] args) throws IOException {
        char[][] grid;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day12/input.txt"))){
            grid = reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);
        }
        List<Set<Coords>> regions = findRegions(grid);
        solve1(regions);
        solve2(regions);
    }

    private static void solve1(List<Set<Coords>> regions){
        int cost = 0;
        for(Set<Coords> region : regions) {
            cost += region.size() * findPerimeter(region);
        }
        System.out.println(cost);
    }

    private static void solve2(List<Set<Coords>> regions){
        int cost = 0;
        for(Set<Coords> region : regions) {
            cost += region.size() * findSides(region);
        }
        System.out.println(cost);
    }

    private static int findPerimeter(Set<Coords> region) {
        int perimeter = 0;
        for(Coords plot : region) {
            perimeter += 4;
            for(int[] direction : directions) {
                Coords next = new Coords(plot.x + direction[0], plot.y + direction[1]);
                if (region.contains(next)) {
                    perimeter--;
                }
            }
        }
        return perimeter;
    }

    private static int findSides(Set<Coords> region) {
        int corners = 0;
        for(Coords plot : region) {
            corners += countCorners(region, plot);
        }
        return corners;
    }

    private static int countCorners(Set<Coords> region, Coords plot) {
        int corners = 0;
        Coords topLeft = new Coords(plot.x - 1, plot.y - 1);
        Coords top = new Coords(plot.x, plot.y - 1);
        Coords left = new Coords(plot.x - 1, plot.y);
        Coords topRight = new Coords(plot.x + 1, plot.y - 1);
        Coords right = new Coords(plot.x + 1, plot.y);
        Coords bottomLeft = new Coords(plot.x - 1, plot.y + 1);
        Coords bottom = new Coords(plot.x, plot.y + 1);
        Coords bottomRight = new Coords(plot.x + 1, plot.y + 1);

        if(!region.contains(topLeft) && (region.contains(top) && region.contains(left))){
            corners++;
        }
        if(!region.contains(top) && !region.contains(left)){
            corners++;
        }
        if(!region.contains(topRight) && (region.contains(top) && region.contains(right))){
            corners++;
        }
        if(!region.contains(top) && !region.contains(right)){
            corners++;
        }
        if(!region.contains(bottomLeft) && (region.contains(bottom) && region.contains(left))){
            corners++;
        }
        if(!region.contains(bottom) && !region.contains(left)){
            corners++;
        }
        if(!region.contains(bottomRight) && (region.contains(bottom) && region.contains(right))){
            corners++;
        }
        if(!region.contains(bottom) && !region.contains(right)){
            corners++;
        }
        return corners;
    }


    private static List<Set<Coords>> findRegions(char[][] grid) {
        List<Set<Coords>> regions = new ArrayList<>();
        Set<Coords> visited = new HashSet<>();
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                Coords coords = new Coords(col, row);
                if(!visited.contains(coords)) {
                    Set<Coords> region = new HashSet<>();
                    fillRegion(grid, coords, region, visited);
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    private static void fillRegion(char[][] grid, Coords coords, Set<Coords> region, Set<Coords> visited){
        region.add(coords);
        visited.add(coords);
        for(int[] direction : directions) {
            Coords next = new Coords(coords.x + direction[0], coords.y + direction[1]);
            if(visited.contains(next)){
                continue;
            }
            if(inBounds(grid, next) && grid[next.y][next.x] == grid[coords.y][coords.x]){
                fillRegion(grid, next, region, visited);
            }
        }
    }

    private static boolean inBounds(char[][] grid, Coords position) {
        return position.y >= 0 && position.y < grid.length && position.x >= 0 && position.x < grid[position.y].length;
    }
}
