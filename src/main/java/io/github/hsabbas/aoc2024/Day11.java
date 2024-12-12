package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day11 {

    record NumAndBlinks(long num, int blinks) {}

    public static void main(String[] args) throws IOException {
        int[] numbers;
        try(BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day11/input.txt"))){
            numbers = Arrays.stream(reader.readLine()
                    .split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        solve(numbers, 25);
        solve(numbers, 75);
    }

    private static void solve(int[] numbers, int blinks) {
        Map<NumAndBlinks, Long> prevFound = new HashMap<>();
        long total = 0;
        for(int number : numbers) {
            total += splitStone(number, blinks, prevFound);
        }
        System.out.println(total);
    }

    private static long splitStone(long number, int blinks, Map<NumAndBlinks, Long> prevFound) {
        if(blinks == 0) {
            return 1;
        }

        NumAndBlinks current = new NumAndBlinks(number, blinks);
        if(prevFound.containsKey(current)) {
            return prevFound.get(current);
        }

        long total;
        if(number == 0){
            total = splitStone(1, blinks - 1, prevFound);
        } else {
            long splitInd = findSplit(number);
            if(splitInd != 0) {
                total = splitStone( number % (int) Math.pow(10, splitInd), blinks - 1, prevFound)
                        + splitStone(number / (int) Math.pow(10, splitInd), blinks - 1, prevFound);
            } else {
                total = splitStone(number * 2024, blinks - 1, prevFound);
            }
        }

        prevFound.put(current, total);
        return total;
    }

    private static long findSplit(long number) {
        int pow = 1;
        while(number % Math.pow(10, pow) != number) {
            pow++;
        }
        return pow % 2 == 0 ? pow / 2 : 0;
    }
}
