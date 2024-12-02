package io.github.hsabbas.aoc2024;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day1 {
    public static void solve() throws IOException {
        int[] leftList = new int[1000];
        int[] rightList = new int[1000];
        Map<Integer, Integer> rightCounts = new HashMap<>();

        File input = new File("C:/AdventOfCode2024/Day1/input.txt");
        int index = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(input))){
            for(String line : reader.lines().toList()) {
                String[] numbers = line.split("   ");
                leftList[index] = Integer.parseInt(numbers[0]);
                int right = Integer.parseInt(numbers[1]);
                rightList[index] = right;
                rightCounts.put(right, rightCounts.getOrDefault(right, 0) + 1);
                index++;
            }
        }

        Arrays.sort(leftList);
        Arrays.sort(rightList);

        long difference = 0;
        long similarity = 0;
        for(int i = 0; i < leftList.length; i++) {
            difference += Math.abs(leftList[i] - rightList[i]);
            similarity += (long) leftList[i] * rightCounts.getOrDefault(leftList[i], 0);
        }

        System.out.println("Answer to problem 1: " + difference);
        System.out.println("Answer to problem 2: " + similarity);
    }

    public static void main(String[] args) throws IOException {
        solve();
    }
}
