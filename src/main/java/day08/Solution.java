package main.java.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class Connection {
    public double distance;
    int idx1;
    int idx2;
    public int[] box1;
    public int[] box2;

    public Connection(double distance, int[] box1, int[] box2, int idx1, int idx2) {
        this.distance = distance;
        this.box1 = box1;
        this.box2 = box2;
        this.idx1 = idx1;
        this.idx2 = idx2;
    }
}

public class Solution {
    private static final Queue<Connection> connections = new PriorityQueue<>(Comparator.comparingDouble(conn -> conn.distance));

    private static double calcDistance(int[] p1, int[] p2) {
        double dx = p2[0] - p1[0];
        double dy = p2[1] - p1[1];
        double dz = p2[2] - p1[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private static void findConnections(List<int[]> input) {
        for(int i = 0; i < input.size(); i++) {
            for(int j = i + 1; j < input.size(); j++) {
                Connection connection = new Connection(calcDistance(input.get(i), input.get(j)), input.get(i), input.get(j), i, j);
                connections.add(connection);
            }
        }
    }

    private static long partOne(int inputSize, int noConnections) {
        Queue<Long> circuits = new PriorityQueue<>((a, b) -> Long.compare(b, a));
        Map<Integer, Long> circuitSize = new HashMap<>();
        int circuitCount = 0;
        int[] circuitMap = new int[inputSize];
        while(noConnections > 0) {
            noConnections--;
            Connection connection = connections.poll();
            assert connection != null;
            if(circuitMap[connection.idx1] != 0 && circuitMap[connection.idx2] != 0 &&
                    circuitMap[connection.idx1] == circuitMap[connection.idx2]) continue;

            if(circuitMap[connection.idx1] != 0 && circuitMap[connection.idx2] != 0) {
                circuitCount++;
                long boxesCircuitOne = circuitSize.get(circuitMap[connection.idx1]);
                long boxesCircuitTwo = circuitSize.get(circuitMap[connection.idx2]);
                circuitSize.remove(circuitMap[connection.idx1]);
                circuitSize.remove(circuitMap[connection.idx2]);
                circuitSize.put(circuitCount, boxesCircuitOne + boxesCircuitTwo);
                int circuitMapOne = circuitMap[connection.idx1];
                int circuitMapTwo = circuitMap[connection.idx2];
                for(int i = 0; i < circuitMap.length; i++) {
                    if(circuitMap[i] == circuitMapOne || circuitMap[i] == circuitMapTwo) {
                        circuitMap[i] = circuitCount;
                    }
                }
            }

            else if(circuitMap[connection.idx1] == 0 && circuitMap[connection.idx2] == 0) {
                //two isolated boxes being connected
                circuitCount++;
                circuitMap[connection.idx1] = circuitCount;
                circuitMap[connection.idx2] = circuitCount;
                circuitSize.put(circuitCount, 2L);
            }
            else if(circuitMap[connection.idx1] == 0) {
                circuitMap[connection.idx1] = circuitMap[connection.idx2];
                circuitSize.put(circuitMap[connection.idx2], circuitSize.get(circuitMap[connection.idx2]) + 1);
            }
            else {
                circuitMap[connection.idx2] = circuitMap[connection.idx1];
                circuitSize.put(circuitMap[connection.idx1], circuitSize.get(circuitMap[connection.idx1]) + 1);
            }
        }
        for(Map.Entry<Integer, Long> entry : circuitSize.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
            circuits.add(entry.getValue());
        }
        long total = 0;
        for(int i = 0; i < 3; i++) {
            Long noBoxes = circuits.poll();
            if(noBoxes == null) continue;
            if(total == 0) {
                total = noBoxes;
            }
            else {
                total *= noBoxes;
            }
        }
        return total;
    }

    private static long partTwo(int inputSize) {
        Map<Integer, Long> circuitSize = new HashMap<>();
        int circuitCount = 0;
        int[] circuitMap = new int[inputSize];
        while(true) {
            Connection connection = connections.poll();
            assert connection != null;
            if(circuitMap[connection.idx1] != 0 && circuitMap[connection.idx2] != 0 &&
                    circuitMap[connection.idx1] == circuitMap[connection.idx2]) continue;

            if(circuitMap[connection.idx1] != 0 && circuitMap[connection.idx2] != 0) {
                circuitCount++;
                long boxesCircuitOne = circuitSize.get(circuitMap[connection.idx1]);
                long boxesCircuitTwo = circuitSize.get(circuitMap[connection.idx2]);
                circuitSize.remove(circuitMap[connection.idx1]);
                circuitSize.remove(circuitMap[connection.idx2]);
                circuitSize.put(circuitCount, boxesCircuitOne + boxesCircuitTwo);
                int circuitMapOne = circuitMap[connection.idx1];
                int circuitMapTwo = circuitMap[connection.idx2];
                for(int i = 0; i < circuitMap.length; i++) {
                    if(circuitMap[i] == circuitMapOne || circuitMap[i] == circuitMapTwo) {
                        circuitMap[i] = circuitCount;
                    }
                }
            }

            else if(circuitMap[connection.idx1] == 0 && circuitMap[connection.idx2] == 0) {
                //two isolated boxes being connected
                circuitCount++;
                circuitMap[connection.idx1] = circuitCount;
                circuitMap[connection.idx2] = circuitCount;
                circuitSize.put(circuitCount, 2L);
            }
            else if(circuitMap[connection.idx1] == 0) {
                circuitMap[connection.idx1] = circuitMap[connection.idx2];
                circuitSize.put(circuitMap[connection.idx2], circuitSize.get(circuitMap[connection.idx2]) + 1);
            }
            else {
                circuitMap[connection.idx2] = circuitMap[connection.idx1];
                circuitSize.put(circuitMap[connection.idx1], circuitSize.get(circuitMap[connection.idx1]) + 1);
            }
            if(circuitSize.get(circuitCount) == inputSize) {
                //we found the mf
                return (long) connection.box1[0] * connection.box2[0];
            }
        }
    }

    public static void main(String[] args) {
        List<int[]> input = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day8.txt"));
            String line = reader.readLine();
            while(line != null) {
                String[] coordStr = line.split(",");
                int[] coords = new int[coordStr.length];
                for(int i = 0; i < coordStr.length; i++) {
                    coords[i] = Integer.parseInt(coordStr[i]);
                }
                if(coords.length != 3) throw new Exception("Coordinates are not in 3D");
                input.add(coords);
                line = reader.readLine();
            }
            findConnections(input);
//            System.out.println(partOne(input.size(), 1000));
                System.out.println(partTwo(input.size()));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
