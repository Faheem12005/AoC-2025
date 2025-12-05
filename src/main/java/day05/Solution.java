package main.java.day05;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class Solution {
    private static final NavigableMap<Long, Long> ranges = new TreeMap<>();

    private static boolean checkValidId(long id) {
        Long lowerKey = ranges.lowerKey(id);
        if(lowerKey == null) return false;
        return id <= ranges.get(lowerKey) || ranges.containsKey(id);
    }

    private static void insertIntoMap(long start, long end) {
        Map.Entry<Long, Long> lower = ranges.floorEntry(start);

        if (lower != null && lower.getValue() >= start - 1) {
            start = lower.getKey();
            end = Math.max(end, lower.getValue());
            ranges.remove(lower.getKey());
        }

        // Merge with all overlapping higher ranges
        Map.Entry<Long, Long> higher = ranges.ceilingEntry(start);

        while (higher != null && higher.getKey() <= end + 1) {
            end = Math.max(end, higher.getValue());
            ranges.remove(higher.getKey());
            higher = ranges.ceilingEntry(start);
        }

        ranges.put(start, end);
    }

    private static long countValidIDs() {
        long count = 0;
        for(Map.Entry<Long, Long> entry : ranges.entrySet()) {
            count += (entry.getValue() - entry.getKey() + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day5.txt"));
            String line = reader.readLine();
            while(!Objects.equals(line, "")) {
                String[] range = line.split("-");
                long start = Long.parseLong(range[0]);
                long end = Long.parseLong(range[1]);
                insertIntoMap(start, end);
                line = reader.readLine();
            }
            int count = 0;
            line = reader.readLine();
            while(line != null) {
                long id = Long.parseLong(line);
                if(checkValidId(id)) count++;
                line = reader.readLine();
            }
            System.out.println(count);
            System.out.println(countValidIDs());
        }
        catch(Exception e) {
            System.err.println("Error occurred: " + e);
        }

    }
}
