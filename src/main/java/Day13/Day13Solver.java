package Day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Solver {
    List<Pair> pairs;

    public Day13Solver(List<String> input) {
        this.pairs = new ArrayList<>();
        while (input.size() > 0) {
            this.pairs.add(new Pair(input.remove(0), input.remove(0)));
            if (input.size() > 0) {
                input.remove(0);
            }
        }
    }

    public int solveDay13a() {
        int toReturn = 0;
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).isSmaller(pairs.get(i).first, pairs.get(i).second) == 1) {
                toReturn += (i + 1);
                System.out.println("++++++++" + (i + 1) + "++++++++");
            } else {
                System.out.println("--------" + (i + 1) + "--------");

            }
        }
        return toReturn;
    }

    public int solveDay13b() {
        List<String> input = pairs.stream()
                .map(pair -> Arrays.asList(pair.first, pair.second))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        input.add("[[2]]");
        input.add("[[6]]");
        List<String> sortedInput = input.stream()
                .sorted((a, b) -> {
                    Pair pair = new Pair(a, b);
                    return -pair.isSmaller(a, b);
                })
                .collect(Collectors.toList());
        int toReturn = 1;
        for (int i = 0; i < sortedInput.size(); i++) {
            if (sortedInput.get(i).equals("[[2]]") || sortedInput.get(i).equals("[[6]]")) {
                System.out.println("===>" + i);
                toReturn *= (i + 1);
            }
        }
        return toReturn;
    }

    static class Pair {
        String first;
        String second;

        Pair(String first, String second) {
            this.first = first;
            this.second = second;
        }

        int isSmaller(String first, String second) {
            System.out.println(first + "  -  " + second);
            if (second.equals("") && first.equals("")) {
                return 0;
            }
            if (second.equals("")) {
                return -1;
            }
            if (first.equals("")) {
                return 1;
            }
            String regEx = "-?\\d+";
            if (first.equals("[]") && second.equals("[]")) {
                return 0;
            }
//            if (first.matches(regEx) && second.equals("[]")) {
//                return 1;
//            }
//            if (second.matches(regEx) && first.equals("[]")) {
//                return -1;
//            }
            if (first.matches(regEx) && second.matches(regEx)) {
                return Integer.compare(Integer.parseInt(second), Integer.parseInt(first));
            }
            if (first.matches(regEx)) {
                first = "[" + first + "]";
            }
            if (second.matches(regEx)) {
                second = "[" + second + "]";
            }
            String firstToCompare = first.substring(1, first.length() - 1);
            String secondToCompare = second.substring(1, second.length() - 1);
            List<String> firstParts = splitToCompare(firstToCompare);
            List<String> secondParts = splitToCompare(secondToCompare);
            for (int i = 0; i < Math.min(firstParts.size(), secondParts.size()); i++) {
                int comparison = isSmaller(firstParts.get(i), secondParts.get(i));
                if (comparison == 1) {
                    return 1;
                } else if (comparison == -1) {
                    return -1;
                }
            }
            return firstParts.size() < secondParts.size() ? 1 : 0;
        }

        private List<String> splitToCompare(String input) {
            List<Integer> firstCommaPositions = getCommaPositions(input);
            return getParts(input, firstCommaPositions);
        }

        List<String> getParts(String input, List<Integer> commaPositions) {
            List<String> toReturn = new ArrayList<>();
            if (commaPositions.size() == 1) {
                toReturn.add(input);
                return toReturn;
            }
            for (int i = 0; i < commaPositions.size() - 1; i++) {
                toReturn.add(input.substring(commaPositions.get(i), commaPositions.get(i + 1)));
            }
            toReturn.add(input.substring(commaPositions.get(commaPositions.size() - 1)));
            return toReturn.stream()
                    .map(part -> part.startsWith(",") ? part.substring(1) : part)
                    .collect(Collectors.toList());
        }

        List<Integer> getCommaPositions(String input) {
            List<Integer> toReturn = new ArrayList<>();
            toReturn.add(0);
            int bracketsOpened = 0;
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == ',' && bracketsOpened == 0) {
                    toReturn.add(i);
                } else if (input.charAt(i) == '[') {
                    bracketsOpened++;
                } else if (input.charAt(i) == ']') {
                    bracketsOpened--;
                }
            }
            return toReturn;
        }
    }
}
