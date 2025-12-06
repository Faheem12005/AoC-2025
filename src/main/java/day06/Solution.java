package main.java.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Solution {

    private static long solve(long a, long b, char operation) throws Exception {
        if(operation == '+') {
            return a + b;
        }
        if(operation == '*') {
            return a * b;
        }
        else {
            throw new Exception("Unsupported Operator");
        }
    }

    private static long solveProblemSheet(List<String[]> problemSheet) throws Exception {
        long finalResult = 0;
        int noProblems = problemSheet.getLast().length;
        int problemLength = problemSheet.size();
        for(int i = 0; i < noProblems; i++) {
            long localResult = 0;
            char operation = problemSheet.getLast()[i].charAt(0);
            for(int j = 0; j < problemLength - 1; j++) {
                if(j == 0) {
                    localResult = Long.parseLong(problemSheet.get(j)[i]);
                    continue;
                }
                localResult = solve(localResult, Long.parseLong(problemSheet.get(j)[i]), operation);
            }
            finalResult += localResult;
        }
        return finalResult;
    }

    private static long processCephMath(List<String> numbers, char operation) throws Exception {
        long result = 0;
        for(String num : numbers) {
            if(result == 0) {
                result = Long.parseLong(num.trim());
            }
            else {
                result = solve(result, Long.parseLong(num.trim()), operation);
            }
        }
        return result;
    }


    public static List<String[]> partOneReader(FileReader fileReader) throws Exception {
        List<String[]> problemSheet = new ArrayList<>();
        int length = -1;
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        while(line != null) {
            line = line.trim();
            line = line.replaceAll("\\s+", " ");
            String[] words = line.split("\\s+");
            if(length == -1) {
                length = words.length;
            }
            else if(length != words.length) {
                throw new Exception("lines are not off the same length");
            }
            problemSheet.add(words);
            line = reader.readLine();
        }
        return problemSheet;
    }

    private static long solveProblemSheetTwo(List<String[]> problemSheet, int maxLen) throws Exception {
        long finalResult = 0;
        int noProblems = problemSheet.getLast().length;
        int problemLength = problemSheet.size();
        for(int i = 0; i < noProblems; i++) {
            List<String> noList = new ArrayList<>();
            char operation = problemSheet.getLast()[i].trim().charAt(0);
            for(int idx = maxLen - 1; idx >= 0; idx--) {
                StringBuilder horizontalNo = new StringBuilder();
                for(int j = 0; j < problemLength - 1; j++) {
                    String localNo = problemSheet.get(j)[i];
                    if(idx > localNo.length() - 1) continue;
                    horizontalNo.append(localNo.charAt(idx));
                }
                if(horizontalNo.toString().isEmpty()) continue;
                noList.add(horizontalNo.toString());
            }
            System.out.println();
            finalResult += processCephMath(noList, operation);
        }
        return finalResult;
    }


    public static int getProblemLength(String line) {
        String lineCopy = line.trim();
        lineCopy = lineCopy.replaceAll("\\s+", " ");
        String[] words = lineCopy.split("\\s+");
        return words.length;
    }

    public static int getNextStartIdx(List<String> input, int startIdx) {
        while(true) {
            int spaceCount = 0;
            for(String line : input) {
                if(startIdx < line.length() && line.charAt(startIdx) != ' ') continue;
                spaceCount++;
            }
            if(spaceCount == input.size()) {
                return startIdx + 1;
            }
            startIdx++;
        }
    }

    public static List<String[]> partTwoReader(FileReader fileReader) throws Exception {
        List<List<String>> problemSheet = new ArrayList<>();
        BufferedReader reader = new BufferedReader(fileReader);
        List<String> input = new ArrayList<>();
        String line = reader.readLine();
        int maxInputLength = line.length();
        while(line != null) {
            input.add(line);
            maxInputLength = Math.max(maxInputLength, line.length());
            problemSheet.add(new ArrayList<>());
            line = reader.readLine();
        }
        int startIdx = 0;
        int nextStartIdx = getNextStartIdx(input, startIdx);
        while (startIdx < maxInputLength) {
            for (int i = 0; i < input.size(); i++) {
                String inputLine = input.get(i);
                problemSheet.get(i).add(inputLine.substring(startIdx, Math.min(nextStartIdx - 1, inputLine.length())));
            }
            startIdx = nextStartIdx;
            nextStartIdx = getNextStartIdx(input, startIdx);
        }
        List<String[]> result = new ArrayList<>();
        for (List<String> row : problemSheet) {
            result.add(row.toArray(new String[0]));
        }
        return result;
    }

    public static void main(String[] args) {
        int maxLen = 4;
        try {
            List<String[]> problemSheet = partTwoReader(new FileReader("input/day6.txt"));
            for(String[] words : problemSheet) {
                System.out.println(Arrays.toString(words));
            }
            System.out.println(solveProblemSheetTwo(problemSheet, 4));

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
