package io.github.hsabbas.aoc2024;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day13 {
    record Vec2(long x, long y) {}
    record Machine(Vec2 moveA, Vec2 moveB, Vec2 prize){}
    static final long offset = 10000000000000L;
    public static void main(String[] args) throws IOException {
        File file = new File("C:/AdventOfCode2024/Day13/input.txt");

        String uglyRegex = "Button A: X\\+(\\d+), Y\\+(\\d+)\\r?\\n" +
                           "Button B: X\\+(\\d+), Y\\+(\\d+)\\r?\\n" +
                           "Prize: X=(\\d+), Y=(\\d+)";

        Scanner scanner = new Scanner(file);
        List<Machine> machines = scanner.findAll(Pattern.compile(uglyRegex))
                .map(r -> new Machine(new Vec2(Integer.parseInt(r.group(1)), Integer.parseInt(r.group(2))),
                                new Vec2(Integer.parseInt(r.group(3)), Integer.parseInt(r.group(4))),
                                new Vec2(Long.parseLong(r.group(5)), Long.parseLong(r.group(6)))))
                .toList();

        solve1(machines);
        solve2(machines);
    }

    private static void solve1(List<Machine> machines) {
        long total = 0;
        for(Machine machine : machines) {
            long cost = findCost(machine.prize, machine.moveA, machine.moveB);
            if(cost != Integer.MAX_VALUE){
                total += cost;
            }
        }
        System.out.println(total);
    }

    private static void solve2(List<Machine> machines) {
        long total = 0;
        for(Machine machine : machines) {
            Vec2 newPrize = new Vec2(machine.prize.x + offset, machine.prize.y + offset);
            long cost = findCost(newPrize, machine.moveA, machine.moveB);
            if(cost != Integer.MAX_VALUE){
                total += cost;
            }
        }
        System.out.println(total);
    }

    private static long findCost(Vec2 prize, Vec2 moveA, Vec2 moveB) {
        long bNumerator = prize.y * moveA.x - prize.x * moveA.y;
        long bDenominator = moveA.x * moveB.y - moveA.y * moveB.x;
        if(bNumerator % bDenominator != 0) {
            return 0;
        }
        long bPresses = bNumerator / bDenominator;
        long aNumerator = prize.x - moveB.x * bPresses;
        if(aNumerator % moveA.x != 0) {
            return 0;
        }
        long aPresses = aNumerator / moveA.x;
        return bPresses + aPresses * 3;
    }
}