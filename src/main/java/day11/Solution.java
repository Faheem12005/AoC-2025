package main.java.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Solution {

    private static long findUniquePaths(Map<String, String[]> edges, Set<String> visited, String src) {
        if(src.equals("out")) return 1;
        long count = 0;
        visited.add(src);
        for(String edge : edges.get(src)) {
            if(visited.contains(edge)) continue;
            count += findUniquePaths(edges, visited, edge);
        }
        visited.remove(src);
        return count;
    }

    private static long findUniquePathsPartTwo(Map<String, String[]> edges, Set<String> visited, String src, Map<String, Long> dpUniquePaths) {
        if(src.equals("out")) {
            if(visited.contains("dac") && visited.contains("fft")) return 1;
            return 0;
        }
        boolean visitedDac = visited.contains("dac");
        boolean visitedFft = visited.contains("fft");
        String key = src + "|" + visitedDac + "|" + visitedFft;
        if(dpUniquePaths.containsKey(key)) return dpUniquePaths.get(key);
        long count = 0;
        visited.add(src);
        for(String edge : edges.get(src)) {
            if(visited.contains(edge)) continue;
            count += findUniquePathsPartTwo(edges, visited, edge, dpUniquePaths);
        }
        visited.remove(src);
        dpUniquePaths.put(src, count);
        dpUniquePaths.put(key, count);
        return count;
    }


    public static void main(String[] args) {
        Map<String, String[]> edges = new HashMap<>();
        Map<String, Long> dpUniquePaths = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day11.txt"));
            String line = reader.readLine();
            while(line != null) {
                String[] input = line.split(":");
                String src = input[0].trim();
                String[] dst = input[1].trim().split(" ");
                edges.put(src, dst);
                line = reader.readLine();
            }

//            System.out.println(findUniquePaths(edges, new HashSet<>(), "you"));
            System.out.println(findUniquePathsPartTwo(edges, new HashSet<>(), "svr", dpUniquePaths));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
