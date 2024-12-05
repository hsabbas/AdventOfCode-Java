package io.github.hsabbas.aoc2024;

import java.io.*;
import java.util.*;

public class Day1 {
    public static void solve() throws IOException {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        Map<Integer, Integer> rightCounts = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day1/input.txt"))){
            reader.lines().map(line -> line.split(" +")).forEach(pair -> {
                leftList.add(Integer.parseInt(pair[0]));
                int right = Integer.parseInt(pair[1]);
                rightList.add(right);
                rightCounts.put(right, rightCounts.getOrDefault(right, 0) + 1);
            });
        }

        Collections.sort(leftList);
        Collections.sort(rightList);

        long difference = 0;
        long similarity = 0;
        for(int i = 0; i < leftList.size(); i++) {
            difference += Math.abs(leftList.get(i) - rightList.get(i));
            similarity += (long) leftList.get(i) * rightCounts.getOrDefault(leftList.get(i), 0);
        }

        System.out.println("Answer to problem 1: " + difference);
        System.out.println("Answer to problem 2: " + similarity);
    }

    public static void main(String[] args) throws IOException {
        solve();
    }
}
