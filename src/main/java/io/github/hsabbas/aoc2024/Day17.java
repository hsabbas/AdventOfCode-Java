package io.github.hsabbas.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day17 {
    public static void main(String[] args) throws IOException {
        int a = 0;
        int b = 0;
        int c = 0;
        int[] instructions = new int[0];
        try(BufferedReader br = new BufferedReader(new FileReader("C:/AdventOfCode2024/Day17/input.txt"))){
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("Register A")) {
                    a = Integer.parseInt(line.substring(12));
                } else if(line.contains("Register B")) {
                    b = Integer.parseInt(line.substring(12));
                } else if(line.contains("Register C")) {
                    c = Integer.parseInt(line.substring(12));
                } else if(!line.isBlank()) {
                    instructions = Arrays.stream(line.substring(9).split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                }
            }
        }

        solve1(instructions, new int[]{a, b, c});
    }

    private static void solve1(int[] instructions, int[] registers){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < instructions.length; i+=2) {
            int opcode = instructions[i];
            int operand = instructions[i + 1];
            switch (opcode) {
                case 0:
                    operand = getCombo(operand, registers);
                    registers[0] /= (int) Math.pow(2, operand);
                    break;
                case 1:
                    registers[1] = registers[1] ^ operand;
                    break;
                case 2:
                    operand = getCombo(operand, registers);
                    registers[1] = operand % 8;
                    break;
                case 3:
                    if(registers[0] == 0){
                        continue;
                    }
                    i = operand - 2;
                    break;
                case 4:
                    registers[1] = registers[1] ^ registers[2];
                    break;
                case 5:
                    operand = getCombo(operand, registers);
                    if(!stringBuilder.isEmpty()){
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(operand % 8);
                    break;
                case 6:
                    operand = getCombo(operand, registers);
                    registers[1] = registers[0] / (int) Math.pow(2, operand);
                    break;
                case 7:
                    operand = getCombo(operand, registers);
                    registers[2] = registers[0] / (int) Math.pow(2, operand);
                    break;
            }
        }
        System.out.println(stringBuilder);
    }

    private static int getCombo(int operand, int[] registers) {
        if(operand < 4) {
            return operand;
        }
        if(operand < 7) {
            return registers[operand - 4];
        }
        return -1;
    }
}
