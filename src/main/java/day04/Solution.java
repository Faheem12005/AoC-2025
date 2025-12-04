package main.java.day04;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Solution {
    private static final int[][] directions = new int[][]{
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0},
            {-1, 1},
            {1, -1},
            {1, 1},
            {-1, -1}
    };

    private static boolean isValid(int i, int j, int m, int n) {
        return i >= 0 && i < m && j >= 0 && j < n;
    }

    private static int countRolls(List<char[]> grid) {
        int rollsAccessible = 0;
        for(int i = 0; i < grid.size(); i++) {
            for(int j = 0; j < grid.get(i).length; j++) {
                if(grid.get(i)[j] != '@') continue;
                int rolls = 0;
                for(int[] dir : directions) {
                    int new_i = i + dir[0];
                    int new_j = j + dir[1];
                    if(isValid(new_i, new_j, grid.size(), grid.get(i).length) && grid.get(new_i)[new_j] == '@') {
                        rolls++;
                    }
                }
                if(rolls < 4) rollsAccessible++;
            }
        }
        return rollsAccessible;
    }

    private static int recurseRemoveRolls(int i, int j, List<char[]> grid) {
        int rolls = 0;
        for(int[] dir : directions) {
            int new_i = i + dir[0];
            int new_j = j + dir[1];
            if(isValid(new_i, new_j, grid.size(), grid.get(i).length) && grid.get(new_i)[new_j] == '@') {
                rolls++;
            }
        }
        if(rolls >= 4) return 0;
        grid.get(i)[j] = '.';
        int rollsRemoved = 1;
        for(int[] dir : directions) {
            int new_i = i + dir[0];
            int new_j = j + dir[1];
            if(isValid(new_i, new_j, grid.size(), grid.get(i).length) && grid.get(new_i)[new_j] == '@') {
                rollsRemoved += recurseRemoveRolls(new_i, new_j, grid);
            }
        }
        return rollsRemoved;
    }

    private static int countRollsTwo(List<char[]> grid) {
        int rollsRemoved = 0;
        for(int i = 0; i < grid.size(); i++) {
            for(int j = 0; j < grid.get(i).length; j++) {
                if(grid.get(i)[j] != '@') continue;
                rollsRemoved += recurseRemoveRolls(i, j, grid);
            }
        }
        return rollsRemoved;
    }

    public static void main(String[] args) {
        List<char[]> grid = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day4.txt"));
            String line = reader.readLine();
            while(line != null) {
                grid.add(line.toCharArray());
                line = reader.readLine();
            }
            System.out.println("sum: " + countRollsTwo(grid));
        }
        catch(Exception e) {
            System.err.println("Error occurred: " + e);
        }

    }
}
