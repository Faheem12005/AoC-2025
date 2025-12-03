package main.java.day03;

import java.io.BufferedReader;
import java.io.FileReader;

public class Solution {
    private static int maxJoltage(String input) {
        int maxI = 0;
        int maxDigitOne = input.charAt(0) - '0';
        for(int i = 1; i < input.length() - 1; i++) {
            int digit = input.charAt(i) - '0';
            if(digit > maxDigitOne) {
                maxDigitOne = digit;
                maxI = i;
            }
        }
        int maxDigitTwo = input.charAt(maxI + 1) - '0';
        for(int i = maxI + 1; i < input.length(); i++) {
            maxDigitTwo = Math.max(maxDigitTwo, input.charAt(i) - '0');
        }
        return maxDigitOne * 10 + maxDigitTwo;
    }

    private static long maxJoltageTwo(String input) {
        int maxI = -1;
        long joltage = 0;
        for(int digitsLeft = 12; digitsLeft > 0; digitsLeft--) {
            int maxDigit = -1;
            for(int i = maxI + 1; i <= input.length() - digitsLeft; i++) {
                if(maxDigit < input.charAt(i) - '0') {
                    maxDigit = input.charAt(i) - '0';
                    maxI = i;
                }
            }
            joltage = joltage * 10 + maxDigit;
        }
        return joltage;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day3.txt"));
            String line = reader.readLine();
            long sum = 0;
            while(line != null) {
                sum += maxJoltageTwo(line);
                line = reader.readLine();
            }
            System.out.println("sum: " + sum);
        }
        catch(Exception e) {
            System.err.println("Error occurred: " + e);
        }

    }
}
