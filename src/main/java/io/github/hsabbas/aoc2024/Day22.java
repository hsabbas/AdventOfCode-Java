package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day22 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        int[] startSecrets;
        try (BufferedReader br = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day22/input.txt"))) {
            startSecrets = br.lines().mapToInt(Integer::parseInt).toArray();
        }

        long[][] secrets = findAllSecrets(startSecrets, 2000);
        solve1(secrets);
        solve2(secrets);

        System.out.println(System.currentTimeMillis() - start);
    }

    private static long[][] findAllSecrets(int[] startSecrets, int secretsPerBuyer) {
        long[][] secrets = new long[startSecrets.length][secretsPerBuyer];
        for(int i = 0; i < startSecrets.length; i++) {
            long secret = startSecrets[i];
            for(int j = 0; j < 2000; j++) {
                long val = secret << 6;
                secret = val ^ secret;
                secret %= 16777216;

                val = secret >> 5;
                secret = val ^ secret;
                secret %= 16777216;

                val = secret << 11;
                secret = val ^ secret;
                secret = secret % 16777216;
                secrets[i][j] = secret;
            }
        }
        return secrets;
    }

    private static void solve1(long[][] secrets) {
        long result = 0;
        for(long[] buyer : secrets) {
            result += buyer[buyer.length - 1];
        }
        System.out.println(result);
    }

    private static void solve2(long[][] secrets) {
        Map<List<Integer>, Integer> bananas = new HashMap<>();
        for(long[] buyer : secrets) {
            Set<List<Integer>> seen = new HashSet<>();
            List<Integer> current = new LinkedList<>();
            int prevPrice = (int) (buyer[0] % 10);
            for(int i = 1; i < buyer.length; i++) {
                int currPrice = (int) (buyer[i] % 10);
                int change = currPrice - prevPrice;
                current.add(change);
                if(current.size() > 4) {
                    current.remove(0);
                }

                if(current.size() == 4 && !seen.contains(current)) {
                    seen.add(current);
                    bananas.put(List.copyOf(current), bananas.getOrDefault(current, 0) + currPrice);
                }

                prevPrice = currPrice;
            }
        }
        System.out.println(bananas.values().stream().max(Comparator.naturalOrder()).orElse(0));
    }
}
