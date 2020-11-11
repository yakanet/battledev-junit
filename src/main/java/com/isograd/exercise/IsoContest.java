/*******
 * Read input from System.in
 * Use: System.out.println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise;
import java.util.*;

public class IsoContest {
    public static void main( String[] argv ) throws Exception {
        String line;
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> result = new HashMap<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split(" ");
            if (!result.containsKey(parts[1])) {
                result.put(parts[1], new ArrayList<>());
            }
            result.get(parts[1]).add(parts[0]);
        }
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            if (entry.getValue().size() == 1) {
                System.out.println(entry.getValue().get(0));
            }
        }
    }
}
