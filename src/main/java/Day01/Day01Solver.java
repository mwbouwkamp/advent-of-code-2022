package Day01;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day01Solver {
    public int solveDay01a(List<String> input) {
        List<Integer> caloriesElves = calculateCaloriesElves(input);
        return caloriesElves.stream()
                .max(Integer::compareTo)
                .orElse(-1);
    }

    public int solveDay01b(List<String> input) {
        List<Integer> caloriesElves = calculateCaloriesElves(input);
        List<Integer> sortedCaloriesElves = caloriesElves.stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
        int size = sortedCaloriesElves.size();
        return sortedCaloriesElves.get(size - 1) + sortedCaloriesElves.get(size - 2) + sortedCaloriesElves.get(size - 3);
    }

    private List<Integer> calculateCaloriesElves(List<String> input) {
        List<Integer> caloriesElves = new ArrayList<>();
        int currentTotal = 0;
        for (String line : input) {
            if (line.equals("")) {
                caloriesElves.add(currentTotal);
                currentTotal = 0;
            } else {
                currentTotal += Integer.parseInt(line);
            }
        }
        return caloriesElves;
    }
}
