package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Day2 {
    private static void solve() throws FileNotFoundException {
        File input = new File("C:/AdventOfCode2024/Day2/input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(input));
        List<List<Integer>> reports = reader.lines()
                .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())
                .toList();
        int safeCount1 = 0;
        int safeCount2 = 0;

        for(List<Integer> report : reports) {
            Set<Integer> posDiff = getPositiveDifferenceSet();
            Set<Integer> negDiff = getNegativeDifferenceSet();

            int previous = report.get(0);
            for(int i = 1; i < report.size(); i++) {
                int current = report.get(i);
                int diff = current - previous;
                posDiff.add(diff);
                negDiff.add(diff);
                previous = current;
            }

            if(posDiff.size() == 3 || negDiff.size() == 3) {
                safeCount1++;
                safeCount2++;
                continue;
            }

            for(int removed = 0; removed < report.size(); removed++){
                posDiff = getPositiveDifferenceSet();
                negDiff = getNegativeDifferenceSet();
                int first = removed == 0 ? 1 : 0;
                previous = report.get(first);
                for(int i = first + 1; i < report.size(); i++) {
                    if(i == removed) {
                        continue;
                    }

                    int current = report.get(i);
                    int diff = current - previous;
                    posDiff.add(diff);
                    negDiff.add(diff);
                    previous = current;
                }

                if(posDiff.size() == 3 || negDiff.size() == 3) {
                    safeCount2++;
                    break;
                }
            }
        }
        System.out.println("Answer to problem 1: " + safeCount1);
        System.out.println("Answer to problem 2: " + safeCount2);
    }


    private static Set<Integer> getPositiveDifferenceSet(){
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        return set;
    }

    private static Set<Integer> getNegativeDifferenceSet(){
        Set<Integer> set = new HashSet<>();
        set.add(-1);
        set.add(-2);
        set.add(-3);
        return set;
    }

    public static void main(String[] args) throws FileNotFoundException {
        solve();
    }
}
