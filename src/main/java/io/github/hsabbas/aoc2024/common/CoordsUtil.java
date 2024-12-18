package io.github.hsabbas.aoc2024.common;

public class CoordsUtil {
    public static boolean inBounds(char[][] grid, Coords coords){
        return coords.y() >= 0 && coords.y() < grid.length && coords.x() >= 0 && coords.x() < grid[coords.y()].length;
    }

    public static Coords move(Coords coords, int[] direction){
        return new Coords(coords.x() + direction[0], coords.y() + direction[1]);
    }

    public static int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
}
