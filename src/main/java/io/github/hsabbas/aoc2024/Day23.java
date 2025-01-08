package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day23 {
    public static void main(String[] args) throws IOException {
        Map<String, Set<String>> connections = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day23/input.txt"))){
            List<String> lines = br.lines().toList();
            for(String line : lines) {
                String[] connection = line.split("-");
                if(!connections.containsKey(connection[0])){
                    Set<String> set = new HashSet<>();
                    set.add(connection[1]);
                    connections.put(connection[0], set);
                } else {
                    connections.get(connection[0]).add(connection[1]);
                }

                if(!connections.containsKey(connection[1])){
                    Set<String> set = new HashSet<>();
                    set.add(connection[0]);
                    connections.put(connection[1], set);
                } else {
                    connections.get(connection[1]).add(connection[0]);
                }
            }
        }

        solve1(connections);
        solve2(connections);
    }

    private static void solve1(Map<String, Set<String>> connections) {
        Set<Set<String>> triosFound = new HashSet<>();
        for(Map.Entry<String, Set<String>> entry : connections.entrySet()) {
            Set<String> current = new HashSet<>();
            current.add(entry.getKey());
            String[] pairs = entry.getValue().toArray(new String[0]);
            for(int i = 0; i < pairs.length - 1; i++) {
                current.add(pairs[i]);
                boolean foundt = entry.getKey().charAt(0) == 't'
                        || pairs[i].charAt(0) == 't';
                for(int j = i + 1; j < pairs.length; j++) {
                    if(!foundt && pairs[j].charAt(0) != 't') {
                        continue;
                    }
                    if(connections.get(pairs[j]).contains(pairs[i])){
                        current.add(pairs[j]);
                        triosFound.add(Set.copyOf(current));
                        current.remove(pairs[j]);
                    }
                }
                current.remove(pairs[i]);
            }
        }
        System.out.println(triosFound.size());
    }

    private static void solve2(Map<String, Set<String>> connections) {
        Set<String> biggestParty = new HashSet<>();
        for(Map.Entry<String, Set<String>> entry : connections.entrySet()) {
            if(entry.getValue().size() + 1 < biggestParty.size()) {
                continue;
            }
            Set<String> current = new HashSet<>();
            current.add(entry.getKey());
            String[] pairs = entry.getValue().toArray(new String[0]);
            Set<String> lanParty = findLANParty(connections, current, pairs, 0);
            if(biggestParty.size() < lanParty.size()) {
                biggestParty = lanParty;
            }
        }
        System.out.println(String.join(",", biggestParty.stream().sorted().toList()));
    }

    private static Set<String> findLANParty(Map<String, Set<String>> connections, Set<String> current, String[] pairs, int i) {
        if(i >= pairs.length) {
            return Set.copyOf(current);
        }
        Set<String> withCurrentPc = null;
        if(connections.get(pairs[i]).containsAll(current)){
            current.add(pairs[i]);
            withCurrentPc = findLANParty(connections, current, pairs, i + 1);
            current.remove(pairs[i]);
        }
        Set<String> withoutCurrentPc = findLANParty(connections, current, pairs, i + 1);
        if(withCurrentPc != null && withCurrentPc.size() > withoutCurrentPc.size()){
            return withCurrentPc;
        }
        return withoutCurrentPc;
    }
}
