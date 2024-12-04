package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day3/input.txt"));
        String input = bufferedReader.lines().collect(Collectors.joining());

        StringBuilder stringBuilder = new StringBuilder();
        int searchIndex = 0;
        while(searchIndex < input.length()) {
            int dont = input.indexOf("don't()", searchIndex);
            if(dont == -1) {
                break;
            }
            stringBuilder.append(input, searchIndex, dont);
            int doIndex = input.indexOf("do()", dont);
            if(doIndex == -1) {
                searchIndex = input.length();
            } else {
                searchIndex = doIndex + 4;
            }
        }
        if(searchIndex < input.length()){
            stringBuilder.append(input, searchIndex, input.length());
        }
        String input2 = stringBuilder.toString();

        long total = 0;
        Pattern mulPattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Pattern numPattern = Pattern.compile("\\d+");
        Matcher mulMatcher = mulPattern.matcher(input);
        while(mulMatcher.find()){
            String mul = mulMatcher.group();
            Matcher numMatcher = numPattern.matcher(mul);
            numMatcher.find();
            int num1 = Integer.parseInt(numMatcher.group());
            numMatcher.find();
            total += (long) Integer.parseInt(numMatcher.group()) * num1;
        }
        System.out.println(total);

        long total2 = 0;
        Matcher mulMatcher2 = mulPattern.matcher(input2);
        while(mulMatcher2.find()){
            String mul = mulMatcher2.group();
            Matcher numMatcher = numPattern.matcher(mul);
            numMatcher.find();
            int num1 = Integer.parseInt(numMatcher.group());
            numMatcher.find();
            total2 += (long) Integer.parseInt(numMatcher.group()) * num1;
        }
        System.out.println(total2);
    }
}
