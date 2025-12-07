package main.java.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    private static final Map<Integer, Long> splitterTimelines = new HashMap<>();

    private static long countTimelines(List<String> grid, int beamPos, int row) {
        if(beamPos < 0 || beamPos >= grid.getFirst().length()) return 0;
        int nextRow = row + 1;
        while(true) {
            if(nextRow >= grid.size()) return 1;
            else if(grid.get(nextRow).charAt(beamPos) == '^') break;
            nextRow++;
        }
        //splitter is found, we need to add up the timelines
        int key = nextRow * 1_000_000 + beamPos;
        if(splitterTimelines.containsKey(key)) return splitterTimelines.get(key);
        long timelines = countTimelines(grid, beamPos + 1, nextRow) + countTimelines(grid, beamPos - 1, nextRow);
        splitterTimelines.put(key, timelines);
        return timelines;
    }

    private static int countSplits(List<String> grid, boolean[] beams) {
        int count = 0;
        for(int i = 1; i < grid.size(); i++) {
            for(int j = 0; j < grid.getFirst().length(); j++) {
                if(beams[j] && grid.get(i).charAt(j) == '^') {
                    count++;
                    beams[j] = false;
                    if(j + 1 < grid.getFirst().length()) beams[j + 1] = true;
                    if(j - 1 >= 0) beams[j - 1] = true;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            List<String> grid = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("input/day7.txt"));
            String line = reader.readLine();
            while(line != null) {
                grid.add(line);
                line = reader.readLine();
            }
            int startingCol = -1;
            boolean[] beams = new boolean[grid.getFirst().length()];
            for(int i = 0; i < grid.getFirst().length(); i++) {
                if(grid.getFirst().charAt(i) == 'S') {
                    startingCol = i;
                    beams[i] = true;
                    break;
                }
            }
            System.out.println(countSplits(grid, beams));
            System.out.println(countTimelines(grid, startingCol, 0));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
