package main.java.day01;

import java.io.BufferedReader;
import java.io.FileReader;

public class Solution {
    private static int[] moveDial(String change, int dial) {
        char direction = change.charAt(0);
        int mag = Integer.parseInt(change.substring(1));
        int hits = 0;

        if (direction == 'L') {
            int residue = dial % 100;
            int firstHit = (residue == 0) ? 100 : residue;

            if (firstHit <= mag) {
                hits = 1 + (mag - firstHit) / 100;
            }

            dial = ((dial - mag) % 100 + 100) % 100;
        } else { // 'R'
            int residue = (100 - dial) % 100;
            int firstHit = (residue == 0) ? 100 : residue;

            if (firstHit <= mag) {
                hits = 1 + (mag - firstHit) / 100;
            }

            dial = (dial + mag) % 100;
        }
        return new int[]{hits, dial};
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day1.txt"));
            String line;
            line = reader.readLine();
            int count = 0;
            int dial = 50;
            while(line != null) {
                int[] res = moveDial(line, dial);
                count += res[0];
                dial = res[1];
                line = reader.readLine();
            }
            System.out.println("Count: " + count);
        }
        catch(Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
