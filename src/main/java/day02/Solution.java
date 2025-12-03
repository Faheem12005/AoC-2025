package main.java.day02;

import java.io.BufferedReader;
import java.io.FileReader;

public class Solution {
    private static long countInvalid(long start, long end) {
        long sum = 0;
        for(long noIdx = start; noIdx <= end; noIdx++) {
            int length = 0;
            long noCopy = noIdx;
            while(noCopy > 0) {
                length++;
                noCopy /= 10;
            }
            if(length % 2 != 0) continue;
            length = length / 2;
            int divisor = (int) Math.pow(10, length);
            if(noIdx / divisor == noIdx % divisor) sum += noIdx;
        }
        return sum;
    }
    /// PART TWO //
    private static long countInvalidTwo(long start, long end) {
        long sum = 0;
        for(long noIdx = start; noIdx <= end; noIdx++) {
            int length = 0;
            long noCopy = noIdx;
            while(noCopy > 0) {
                length++;
                noCopy /= 10;
            }
            int maxLen = length / 2;
            for(int i = 1; i <= maxLen; i++) {
                if(length % i != 0) continue;
                int divisor = (int) Math.pow(10, i);
                noCopy = noIdx;
                long rep = noCopy % divisor;
                noCopy /= divisor;
                while(noCopy > 0) {
                    if(noCopy % divisor != rep) break;
                    noCopy /= divisor;
                }
                if(noCopy == 0) {
                    sum += noIdx;
                    break;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day2.txt"));
            String[] inputList = reader.readLine().split(",");
            long sum = 0;
            for(String input : inputList) {
                String[] range = input.split("-");
                long first = Long.parseLong(range[0]);
                long last = Long.parseLong(range[1]);
                sum += countInvalidTwo(first, last);
            }
            System.out.println("sum: " + sum);
        }
        catch(Exception e) {
            System.err.println("Error occurred: " + e);
        }

    }
}
