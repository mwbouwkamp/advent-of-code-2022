package Day03;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day03Solver {
    List<Rucksack> rucksackList;

    public Day03Solver(List<String> input) {
        this.rucksackList = input.stream()
                .map(s -> new Rucksack(s))
                .collect(Collectors.toList());
    }

    public int solveDay01() {
        return rucksackList.stream()
                .map(item -> getValue(item.getPriority()))
                .reduce(Integer::sum)
                .orElse(-1);
    }

    public int solveDay02() {
        int total = 0;
        for (int i = 0; i < rucksackList.size(); i += 3) {
            int finalI = i;
            String inAll = rucksackList.get(i).bothCompartments.stream()
                    .filter(items -> rucksackList.get(finalI + 1).bothCompartments.contains(items))
                    .filter(items -> rucksackList.get(finalI + 2).bothCompartments.contains(items))
                    .collect(Collectors.toList())
                    .get(0);
             total += getValue(inAll);
        }
        return total;
    }
    int getValue(String item) {
        char itemChar = item.toCharArray()[0];
        if (itemChar >= 97) {
            return itemChar - 96;
        }
        return itemChar - 64  + 26;

    }

    static class Rucksack {
        List<String> bothCompartments;
        List<String> firstCompartment;
        List<String> secondCompartment;

        Rucksack(String input) {
            this.bothCompartments = Arrays.asList(input.split(""));
            this.firstCompartment = Arrays.asList(input.substring(0, input.length() / 2).split(""));
            this.secondCompartment = Arrays.asList(input.substring(input.length() / 2).split(""));
        }

        String getPriority() {
            return firstCompartment.stream()
                    .filter(item -> secondCompartment.contains(item))
                    .collect(Collectors.toList())
                    .get(0);
        }
    }
}
