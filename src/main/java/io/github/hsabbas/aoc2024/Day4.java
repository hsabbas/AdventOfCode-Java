package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        solve();
    }

    private static void solve() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day4/input.txt"));
        List<char[]> lines = reader.lines().map(String::toCharArray).toList();
        char[][] input = new char[lines.size()][];
        for(int i = 0; i < lines.size(); i++) {
            input[i] = lines.get(i);
        }

        int partOneCount = 0;
        int n = input.length;
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < input[row].length; col++) {
                if(input[row][col] != 'X') {
                    continue;
                }

                partOneCount += checkMAS(input, row, col, -1, -1);
                partOneCount += checkMAS(input, row, col, -1, 0);
                partOneCount += checkMAS(input, row, col, -1, 1);
                partOneCount += checkMAS(input, row, col, 0, -1);
                partOneCount += checkMAS(input, row, col, 0, 1);
                partOneCount += checkMAS(input, row, col, 1, -1);
                partOneCount += checkMAS(input, row, col, 1, 0);
                partOneCount += checkMAS(input, row, col, 1, 1);
            }
        }

        int partTwoCount = 0;
        for(int row = 0; row < n - 2; row++) {
            for(int col = 0; col < input[row].length - 2; col++) {
                boolean firstMasFound = false;
                if(input[row][col] == 'M'){
                    firstMasFound = checkAS(input, row, col, 1, 1);
                } else if (input[row + 2][col + 2] == 'M'){
                    firstMasFound = checkAS(input, row + 2, col + 2, -1, -1);
                }
                if(!firstMasFound) {
                    continue;
                }

                boolean secMasFound = false;
                if(input[row + 2][col] == 'M'){
                    secMasFound = checkAS(input, row + 2, col, -1, 1);
                } else if (input[row][col + 2] == 'M'){
                    secMasFound = checkAS(input, row, col + 2, 1, -1);
                }
                if(secMasFound){
                    partTwoCount++;
                }
            }
        }

        System.out.println(partOneCount);
        System.out.println(partTwoCount);
    }

    private static int checkMAS(char[][] input, int xRow, int xCol, int rowChange, int colChange){
        int n = input.length;
        int m = input[xRow].length;

        int mRow = xRow + rowChange;
        int mCol = xCol + colChange;
        if(mCol >= m || mCol < 0 || mRow >= n || mRow < 0
                || input[mRow][mCol] != 'M'){
            return 0;
        }

        int aRow = mRow + rowChange;
        int aCol = mCol + colChange;
        if(aCol >= m || aCol < 0 || aRow >= n || aRow < 0
                || input[aRow][aCol] != 'A'){
            return 0;
        }

        int sRow = aRow + rowChange;
        int sCol = aCol + colChange;
        if(sCol >= m || sCol < 0 || sRow >= n || sRow < 0
                || input[sRow][sCol] != 'S'){
            return 0;
        }
        return 1;
    }

    private static boolean checkAS(char[][] input, int mRow, int mCol, int rowChange, int colChange){
        int aRow = mRow + rowChange;
        int aCol = mCol + colChange;
        if(input[aRow][aCol] != 'A'){
            return false;
        }

        int sRow = aRow + rowChange;
        int sCol = aCol + colChange;
        return input[sRow][sCol] == 'S';
    }
}
