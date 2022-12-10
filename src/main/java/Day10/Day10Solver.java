package Day10;

import java.util.*;
import java.util.stream.Collectors;

public class Day10Solver {
    List<String> signals;
    Map<Integer, Integer> positionMap = new HashMap<>();

    public Day10Solver(List<String> input) {
        this.signals = input.stream()
                .map(signal -> {
                    if (signal.startsWith("addx")) {
                        return Arrays.asList("noop", signal);
                    } else {
                        return List.of(signal);
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
     }

    public int solveDay10a() {
        int x = 1;
        positionMap.put(1, 1);
        for (int i = 0; i < 240; i++) {
            String signal = signals.get(i);
            if (signal.startsWith("addx")) {
                x += Integer.parseInt(signal.split(" ")[1]);
            }
            positionMap.put(i + 2, x);
        }
        return 20 * positionMap.get(20)
                + 60 * positionMap.get(60)
                + 100 * positionMap.get(100)
                + 140 * positionMap.get(140)
                + 180 * positionMap.get(180)
                + 220 * positionMap.get(220);
    }

    public void solveDay10b() {
        StringBuilder builder = new StringBuilder();
        positionMap.keySet()
                .forEach(key -> positionMap.put(key, positionMap.get(key) + 40 * (key / 40)));
        for (int i = 1; i < positionMap.size(); i++) {
            int x = positionMap.get(i);
            if (i - 2 == x || i - 1 == x || i == x) {
                builder.append("#");
            } else {
                builder.append(".");
            }
        }
        System.out.println(builder.substring(0, 39));
        System.out.println(builder.substring(40, 79));
        System.out.println(builder.substring(80, 119));
        System.out.println(builder.substring(120, 159));
        System.out.println(builder.substring(160, 199));
        System.out.println(builder.substring(200));
    }
}
