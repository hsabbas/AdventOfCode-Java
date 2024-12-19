package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day19 {
    public static void main(String[] args) throws IOException {
        List<String> towels = new ArrayList<>();
        List<String> patterns = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day19/input.txt"))){
            String line;
            while(!(line = br.readLine()).isBlank()){
                towels.addAll(Arrays.stream(line.split(", ")).toList());
            }
            while((line = br.readLine()) != null){
                patterns.add(line);
            }
        }

        solve1(patterns, towels);
        solve2(patterns, towels);
    }

    private static void solve1(List<String> patterns, List<String> towels) {
        Set<String> possiblePatterns = new HashSet<>(towels);
        Set<String> impossiblePatterns = new HashSet<>();
        long count = patterns
                .stream()
                .filter(pattern -> isPossible(pattern, possiblePatterns, impossiblePatterns))
                .count();
        System.out.println(count);
    }

    private static boolean isPossible(String pattern, Set<String> possiblePatterns, Set<String> impossiblePatterns) {
        if(possiblePatterns.contains(pattern)) {
            return true;
        }
        if(impossiblePatterns.contains(pattern) || pattern.length() == 1) {
            return false;
        }

        boolean possible = false;
        for(int len = 1; len <= pattern.length(); len++) {
            String leftPart = pattern.substring(0, len);
            if(!possiblePatterns.contains(leftPart)){
                continue;
            }

            possible = isPossible(pattern.substring(len), possiblePatterns, impossiblePatterns);
            if(possible) {
                possiblePatterns.add(pattern);
                break;
            }
        }
        if (!possible) {
            impossiblePatterns.add(pattern);
        }

        return possible;
    }

    private static void solve2(List<String> patterns, List<String> towels) {
        Set<String> towelSet = new HashSet<>(towels);
        Set<String> impossiblePatterns = new HashSet<>();
        long count = patterns
                .stream()
                .mapToLong(pattern -> countOptions(pattern, towelSet, impossiblePatterns, new HashMap<>()))
                .sum();
        System.out.println(count);
    }

    private static long countOptions(String pattern, Set<String> towels, Set<String> impossiblePatterns, Map<String, Long> memo) {
        if(pattern.isEmpty()) {
            return 1;
        }

        if(impossiblePatterns.contains(pattern)) {
            return 0;
        }

        if(memo.containsKey(pattern)) {
            return memo.get(pattern);
        }

        long count = 0;
        for(int len = 0; len <= pattern.length(); len++) {
            String substr = pattern.substring(0, len);
            if(!towels.contains(substr)){
                continue;
            }
            long currCount = countOptions(pattern.substring(len), towels, impossiblePatterns, memo);
            if(currCount == 0) {
                impossiblePatterns.add(pattern.substring(len));
            }
            count += currCount;
        }
        memo.put(pattern, count);
        return count;
    }
}