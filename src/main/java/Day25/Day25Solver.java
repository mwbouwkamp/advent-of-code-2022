package Day25;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day25Solver {
    List<List<Integer>> factorsList;

    public Day25Solver(List<String> input) {
        this.factorsList = input.stream()
                .map(line -> Arrays.asList(line
                        .replace("2", "2,")
                        .replace("1", "1,")
                        .replace("0", "0,")
                        .replace("-", "-1,")
                        .replace("=", "-2,")
                        .split(",")))
                .map(strings -> strings.stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public String solveDay25a() {
//        for (long i = 0L; i < 30; i++) {
//            System.out.println(calcFiveNumber(i));
//        }
        long number = factorsList.stream()
                .map(this::calcFactor)
                .reduce(0L, Long::sum);
        return calcFiveNumber(number);
    }

    private long calcFactor(List<Integer> factors) {
        Collections.reverse(factors);
        long result = 0;
        long five = 1;
        for (Integer factor: factors) {
            result += five * factor;
            five *= 5;
        }
        return result;
    }

    private String calcFiveNumber(long number) {
        String parsedLong = Long.toString(number, 5);
        List<Integer> parts = Arrays.stream(parsedLong.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        while (parts.contains(3) || parts.contains(4) || parts.contains(5)) {
            fixNumber(parts, 3);
            fixNumber(parts, 4);
            fixNumber(parts, 5);
        }
        return parts.stream()
                .map(i -> Integer.toString(i))
                .map(s -> s.equals("-2") ? "=" : s)
                .map(s -> s.equals("-1") ? "-" : s)
                .reduce("", (a, b) -> a + b);
    }

    private void fixNumber(List<Integer> parts, int number) {
        int posNumber = parts.indexOf(number);
        if (posNumber >= 0) {
            parts.set(posNumber, number - 5);
            if (posNumber > 0) {
                parts.set(posNumber - 1, parts.get(posNumber - 1) + 1);
            } else {
                parts.add(0, 1);
            }
        }
    }
}
