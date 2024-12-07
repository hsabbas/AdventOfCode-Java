package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day7 {
    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException {
        List<List<Long>> equations;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day7/input.txt"))){
            equations = reader.lines().map(line -> Arrays.stream(line.split(":? ")).map(Long::parseLong).toList()).toList();
        }

        System.out.println(concatNums(1234L, 4321L));

        long total = 0;
        long total2 = 0;
        for(List<Long> equation : equations) {
            if(isPossible(equation, equation.get(0), equation.get(1), 2)){
                total += equation.get(0);
                total2 += equation.get(0);
            } else if(isPossible2(equation, equation.get(0), equation.get(1), 2)){
                total2 += equation.get(0);
            }
        }
        System.out.println(total);
        System.out.println(total2);
    }

    private static boolean isPossible(List<Long> equation, long needed, long current, int index) {
        if(current > needed){
            return false;
        }

        if(index == equation.size()){
            return current == needed;
        }

        return isPossible(equation, needed, current + equation.get(index), index + 1)
                || isPossible(equation, needed, current * equation.get(index), index + 1);
    }

    private static boolean isPossible2(List<Long> equation, long needed, long current, int index) {
        if(current > needed){
            return false;
        }

        if(index == equation.size()){
            return current == needed;
        }

        return isPossible2(equation, needed, current + equation.get(index), index + 1)
                || isPossible2(equation, needed, current * equation.get(index), index + 1)
                || isPossible2(equation, needed, concatNums(current, equation.get(index)), index + 1);
    }

    private static long concatNums(long num1, Long num2) {
        return Long.parseLong(num1 + String.valueOf(num2));
    }

}