package io.github.hsabbas.aoc2024;

import io.github.hsabbas.aoc2024.common.Coords;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static io.github.hsabbas.aoc2024.common.CoordsUtil.inBounds;

public class Day15 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("C:/AdventOfCode2024/Day15/input.txt"));
        String line = scanner.nextLine();
        List<String> gridLines = new ArrayList<>();
        StringBuilder instructionsBldr = new StringBuilder();
        while (!line.isEmpty()) {
            gridLines.add(line);
            line = scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            instructionsBldr.append(line);
        }

        char[][] grid = gridLines.stream().map(String::toCharArray).toArray(char[][]::new);
        char[][] grid2 = new char[grid.length][grid[0].length * 2];
        Coords robot = new Coords(0, 0);
        Coords robot2 = new Coords(0, 0);

        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '.') {
                    grid2[row][col * 2] = '.';
                    grid2[row][col * 2 + 1] = '.';
                }
                if(grid[row][col] == '#') {
                    grid2[row][col * 2] = '#';
                    grid2[row][col * 2 + 1] = '#';
                }
                if(grid[row][col] == 'O') {
                    grid2[row][col * 2] = '[';
                    grid2[row][col * 2 + 1] = ']';
                }
                if(grid[row][col] == '@'){
                    robot = new Coords(col, row);
                    robot2 = new Coords(col*2, row);
                    grid[row][col] = '.';
                    grid2[row][col * 2] = '.';
                    grid2[row][col * 2 + 1] = '.';
                }
            }
        }

        String instructions = instructionsBldr.toString();

        solve1(grid, robot, instructions);
        solve2(grid2, robot2, instructions);
    }

    private static void solve1(char[][] grid, Coords robot, String instructions){
        for(int i = 0; i < instructions.length(); i++) {
            char direction = instructions.charAt(i);
            Coords next = moveObject(robot, direction);
            if(grid[next.y()][next.x()] == '.') {
                robot = next;
                continue;
            }
            if(grid[next.y()][next.x()] == '#'){
                continue;
            }

            int boxes = 0;
            while(grid[next.y()][next.x()] == 'O'){
                grid[next.y()][next.x()] = '.';
                boxes++;
                next = moveObject(next, direction);
            }
            direction = findReverse(direction);
            if(grid[next.y()][next.x()] != '.') {
                next = moveObject(next, direction);
            }
            while(boxes > 0) {
                grid[next.y()][next.x()] = 'O';
                boxes--;
                next = moveObject(next, direction);
            }

            robot = next;
        }

        int result = 0;
        for(int row = 0; row < grid.length; row ++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == 'O') {
                    result += col + row * 100;
                }
            }
        }
        System.out.println(result);
    }

    private static void solve2(char[][] grid, Coords robot, String instructions){
        for(int i = 0; i < instructions.length(); i++) {
            char direction = instructions.charAt(i);
            Coords next = moveObject(robot, direction);
            if(grid[next.y()][next.x()] == '.') {
                robot = next;
                continue;
            }
            if(grid[next.y()][next.x()] == '#'){
                continue;
            }

            int boxes = 0;
            if(direction == '<') {
                while(inBounds(grid, next) && grid[next.y()][next.x()] == ']'){
                    boxes++;
                    grid[next.y()][next.x()] = '.';
                    next = moveObject(next, direction);
                    grid[next.y()][next.x()] = '.';
                    next = moveObject(next, direction);
                }
                direction = findReverse(direction);
                if(grid[next.y()][next.x()] != '.') {
                    next = moveObject(next, direction);
                }
                while (boxes > 0) {
                    grid[next.y()][next.x()] = '[';
                    next = moveObject(next, direction);
                    grid[next.y()][next.x()] = ']';
                    next = moveObject(next, direction);
                    boxes--;
                }
            } else if(direction == '>') {
                while(inBounds(grid, next) && grid[next.y()][next.x()] == '['){
                    boxes++;
                    grid[next.y()][next.x()] = '.';
                    next = moveObject(next, direction);
                    grid[next.y()][next.x()] = '.';
                    next = moveObject(next, direction);
                }
                direction = findReverse(direction);
                if(grid[next.y()][next.x()] != '.') {
                    next = moveObject(next, direction);
                }
                while (boxes > 0) {
                    grid[next.y()][next.x()] = ']';
                    next = moveObject(next, direction);
                    grid[next.y()][next.x()] = '[';
                    next = moveObject(next, direction);
                    boxes--;
                }
            }
            if(direction == '^' || direction == 'v'){
                Set<Coords> moving = new HashSet<>();
                moving.add(robot);
                if(!pushDoubleWidthBoxes(grid, moving, direction)) {
                    continue;
                }
            }

            robot = next;
        }

        int result = 0;
        for(int row = 0; row < grid.length; row ++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] == '[') {
                    result += col + row * 100;
                }
            }
        }
        System.out.println(result);
    }

    private static boolean pushDoubleWidthBoxes(char[][] grid, Set<Coords> moving, char direction) {
        boolean freeAhead = true;
        Set<Coords> nextSpaces = new HashSet<>();
        for(Coords coords : moving) {
            Coords next = moveObject(coords, direction);
            if(grid[next.y()][next.x()] == '#') {
                return false;
            }
            if(grid[next.y()][next.x()] != '.') {
                freeAhead = false;
            }
            if(grid[next.y()][next.x()] == '[') {
                Coords rightHalf = new Coords(next.x() + 1, next.y());
                nextSpaces.add(rightHalf);
                nextSpaces.add(next);
            }
            if(grid[next.y()][next.x()] == ']') {
                Coords leftHalf = new Coords(next.x() - 1, next.y());
                nextSpaces.add(leftHalf);
                nextSpaces.add(next);
            }
        }

        if(freeAhead || pushDoubleWidthBoxes(grid, nextSpaces, direction)) {
            for(Coords coords : moving) {
                Coords next = moveObject(coords, direction);
                grid[next.y()][next.x()] = grid[coords.y()][coords.x()];
                grid[coords.y()][coords.x()] = '.';
            }
            return true;
        }
        return false;
    }

    private static Coords moveObject(Coords position, char dir) {
        return switch (dir) {
            case '<' -> new Coords(position.x() - 1, position.y());
            case '^' -> new Coords(position.x(), position.y() - 1);
            case '>' -> new Coords(position.x() + 1, position.y());
            case 'v' -> new Coords(position.x(), position.y() + 1);
            default -> new Coords(0,0);
        };
    }

    private static char findReverse(char dir){
        return switch (dir) {
            case '<' -> '>';
            case '^' -> 'v';
            case '>' -> '<';
            case 'v' -> '^';
            default -> ' ';
        };
    }
}
