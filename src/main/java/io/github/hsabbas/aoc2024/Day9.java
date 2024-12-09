package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9 {
    public static void main(String[] args) throws IOException {
        String input = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day9/input.txt")).readLine();
        int[] filesystem = new int[input.length()];
        for(int i = 0; i < filesystem.length; i++) {
            filesystem[i] = input.charAt(i) - '0';
        }

        solvePartOne(filesystem);
    }

    private static void solvePartOne(int[] filesystem) {
        List<Integer> compactFileSystem = new ArrayList<>();

        int left = 0;
        int right = filesystem.length - 1;
        int spaceNeeded = filesystem[right];
        while(left < right) {
            for(int i = 0; i < filesystem[left]; i++){
                compactFileSystem.add(left / 2);
            }
            left++;
            for(int i = 0; i < filesystem[left]; i++) {
                if(spaceNeeded == 0) {
                    right -= 2;
                    if(right <= left) {
                        break;
                    }
                    spaceNeeded = filesystem[right];
                }
                compactFileSystem.add(right / 2);
                spaceNeeded--;
            }
            left++;
        }

        long checksum = 0;
        for(int i = 0; i < compactFileSystem.size(); i++) {
            checksum += (long) i * compactFileSystem.get(i);
        }
        System.out.println(checksum);
    }
}