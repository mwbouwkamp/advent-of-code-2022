package Day20;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day20Solver {
    List<Number> numbers;

    public Day20Solver(List<String> input) {
        numbers = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            numbers.add(new Number(i, Long.parseLong(input.get(i))));
        }
    }

    public long solveDay20(long key, int cycles) {
        List<Number> numbers = this.numbers.stream()
                .map(number -> new Number(number.originalIndex, number.value * key))
                .collect(Collectors.toList());
        for (int c = 0; c < cycles; c++) {
            for (int i = 0; i < numbers.size(); i++) {
                int finalI = i;
                Number current = numbers.stream()
                        .filter(number -> number.originalIndex == finalI)
                        .findFirst()
                        .orElse(null);
                int currentIndex = numbers.indexOf(current);
                numbers.remove(currentIndex);
                assert current != null;
                long newIndex = (currentIndex + current.value) % numbers.size();
                if (newIndex < 0) {
                    newIndex += numbers.size();
                }
                numbers.add((int) newIndex, current);
            }
        }
        Number zero = numbers.stream()
                .filter(number -> number.value == 0)
                .findFirst()
                .orElse(null);
        int zeroIndex = numbers.indexOf(zero);
        long oneK = numbers.get((1000 + zeroIndex) % numbers.size()).value;
        long twoK = numbers.get((2000 + zeroIndex) % numbers.size()).value;
        long threeK = numbers.get((3000 + zeroIndex) % numbers.size()).value;
        return oneK + twoK + threeK;
    }

    static class Number {
        int originalIndex;
        long value;

        Number(int originalIndex, long value) {
            this.originalIndex = originalIndex;
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }
}
