package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day5 {
    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day5/input.txt"));
        Map<String, List<String>> orderRules = new HashMap<>();

        for(String line = reader.readLine(); !line.isBlank(); line = reader.readLine()) {
            String[] pair = line.split("\\|");
            if(!orderRules.containsKey(pair[1])){
                orderRules.put(pair[1], new ArrayList<>());
            }
            orderRules.get(pair[1]).add(pair[0]);
        }

        int partOneSum = 0;
        int partTwoSum = 0;

        for(String line = reader.readLine(); !(line == null); line = reader.readLine())  {
            String[] update = line.split(",");
            List<String> result = new ArrayList<>();
            List<String> pagesToPrint = new ArrayList<>(List.of(update));

            boolean ordered = true;
            while(!pagesToPrint.isEmpty()) {
                for(String page : pagesToPrint.stream().toList()) {
                    boolean add = true;
                    if(orderRules.containsKey(page)){
                        for(String reqPage: orderRules.get(page)){
                            if (pagesToPrint.contains(reqPage)) {
                                add = false;
                                break;
                            }
                        }
                    }
                    if(add){
                        pagesToPrint.remove(page);
                        result.add(page);
                    } else {
                        ordered = false;
                    }
                }
            }

            if(ordered){
                partOneSum += Integer.parseInt(result.get(result.size() / 2));
            } else {
                partTwoSum += Integer.parseInt(result.get(result.size() / 2));
            }
        }
        System.out.println(partOneSum);
        System.out.println(partTwoSum);
    }
}