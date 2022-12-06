package Day06;

import java.util.Arrays;

public class Day06Solver {
    public int solveDay06a(String input, int length) {
        String first =  Arrays.stream(input.split(""))
                .reduce((a, b) -> {
                    if (a.length() == length) {
                        return a;
                    }
                    if (a.contains(b)) {
                        int indexB = a.indexOf(b);
                        return a.substring(indexB + 1) + b;
                    } else {
                        return a + b;
                    }
                }).orElse("");
        return input.indexOf(first);
    }
}
