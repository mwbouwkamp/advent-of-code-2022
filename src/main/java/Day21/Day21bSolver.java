package Day21;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21bSolver {
    Map<String, Monkey> monkeys;

    public Day21bSolver(List<String> input) {
        this.monkeys = input.stream()
                .map(line -> new Monkey(line))
                .collect(Collectors.toMap(monkey -> monkey.identifier, monkey -> monkey));
        this.monkeys.get("humn").value = "X";
        this.monkeys.get("root").operator = '=';
    }

    public long solveDay21b() {
        String formula = monkeys.get("root").getValue(monkeys);
        return solveFormula(formula);
    }

    private long solveFormula(String formula) {
        String leftSide = formula.split("=")[0];
        leftSide = leftSide.substring(1);
        String rightSide = formula.split("=")[1];
        rightSide = rightSide.substring(0, rightSide.length() - 1);
        String solvedSide = !rightSide.contains("(") ? rightSide : leftSide;
        String unsolvedSide = rightSide.contains("(") ? rightSide : leftSide;
        long solved = Long.parseLong(solvedSide);
        while (unsolvedSide.contains("(")) {
            if (unsolvedSide.equals("(X-5)")) {
                System.out.println();
            }
            unsolvedSide = unsolvedSide.substring(1, unsolvedSide.length() - 1);
            int positionFirstPlus = unsolvedSide.contains("+") ? unsolvedSide.indexOf("+") : Integer.MAX_VALUE;
            int positionFirstMin = unsolvedSide.contains("-") ? unsolvedSide.indexOf("-") : Integer.MAX_VALUE;
            int positionFirstMult = unsolvedSide.contains("/") ? unsolvedSide.indexOf("/") : Integer.MAX_VALUE;
            int positionFirstDiv = unsolvedSide.contains("*") ? unsolvedSide.indexOf("*") : Integer.MAX_VALUE;
            int positionFirstOperator = Stream.of(positionFirstPlus, positionFirstMin, positionFirstMult, positionFirstDiv)
                    .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
            int positionLastPlus = unsolvedSide.contains("+") ? unsolvedSide.lastIndexOf("+") : Integer.MIN_VALUE;
            int positionLastMin = unsolvedSide.contains("-") ? unsolvedSide.lastIndexOf("-") : Integer.MIN_VALUE;
            int positionLastMult = unsolvedSide.contains("/") ? unsolvedSide.lastIndexOf("/") : Integer.MIN_VALUE;
            int positionLastDiv = unsolvedSide.contains("*") ? unsolvedSide.lastIndexOf("*") : Integer.MIN_VALUE;
            int positionLastOperator = Stream.of(positionLastPlus, positionLastMin, positionLastMult, positionLastDiv)
                    .reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);

            String firstPartPositionFirst = unsolvedSide.substring(0, positionFirstOperator);
            String secondPartPositionFirst = unsolvedSide.substring(positionFirstOperator + 1);
            String operatorPositionFirst = unsolvedSide.substring(positionFirstOperator, positionFirstOperator + 1);
            String firstPartPositionLast = unsolvedSide.substring(0, positionLastOperator);
            String secondPartPositionLast = unsolvedSide.substring(positionLastOperator + 1);
            String operatorPositionLast = unsolvedSide.substring(positionLastOperator, positionLastOperator + 1);
            boolean firstPartNumber = false;
            boolean usePositionFirst = false;
            if (isNumber(firstPartPositionFirst)) {
                firstPartNumber = true;
                usePositionFirst = true;
            } else if (isNumber(secondPartPositionFirst)) {
                usePositionFirst = true;
            } else if (isNumber(firstPartPositionLast)) {
                firstPartNumber = true;
            }
            long numeric;
            String nonNumeric;
            String operator;
            if (firstPartNumber && usePositionFirst) {
                numeric = Long.parseLong(firstPartPositionFirst);
                nonNumeric = secondPartPositionFirst;
                operator = operatorPositionFirst;
            } else if (firstPartNumber && !usePositionFirst) { // this is the key!!
                numeric = Long.parseLong(firstPartPositionLast);
                nonNumeric = secondPartPositionLast;
                operator = operatorPositionLast;
            } else if (!firstPartNumber && usePositionFirst) {
                numeric = Long.parseLong(secondPartPositionFirst);
                nonNumeric = firstPartPositionFirst;
                operator = operatorPositionFirst;
            } else {
                numeric = Long.parseLong(secondPartPositionLast);
                nonNumeric = firstPartPositionLast;
                operator = operatorPositionLast;
            }
            switch (operator) {
                case "+":
                    solved -= numeric;
                    break;
                case "-":
                    if (firstPartNumber) { // #-b=solved
                        solved = -(solved - numeric);
                    } else { // a-#=solved
                        solved += numeric;
                    }
                    break;
                case "*":
                    solved /= numeric;
                    break;
                case "/":
                    if (firstPartNumber) { // #/b=solved  ->  #=solved*b  ->  b=#/solved
                        solved = numeric / solved;
                    } else {
                        solved *=numeric;
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Operation not supported: " + operator);
            }
            unsolvedSide = nonNumeric;
            System.out.println(unsolvedSide + " -> " + solved);
        }
        System.out.println(solvedSide);
        return Long.parseLong(solvedSide);
    }

    private boolean isNumber(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    static class Monkey {
        String identifier;
        String value;
        char operator;
        String child1;
        String child2;

        Monkey(String input) {
            String[] inputParts = input.split(": ");
            this.identifier = inputParts[0];
            if (!inputParts[1].contains("+") && !inputParts[1].contains("-") && !inputParts[1].contains("*") && !inputParts[1].contains("/") && !inputParts[1].contains("=")) {
                value = inputParts[1];
            } else {
                if (inputParts[1].contains("+")) {
                    operator = '+';
                } else if (inputParts[1].contains("-")) {
                    operator = '-';
                } else if (inputParts[1].contains("*")) {
                    operator = '*';
                } else if (inputParts[1].contains("/")) {
                    operator = '/';
                } else if (inputParts[1].contains("=")) {
                    operator = '=';
                }
                String[] operationParts = inputParts[1].split("[-+*/]");
                child1 = operationParts[0].replace(" ", "");
                child2 = operationParts[1].replace(" ", "");
            }
        }

        String getValue(Map<String, Monkey> monkeys) {
            if (value != null) {
                return value;
            } else {
                String monkey1 = monkeys.get(child1).getValue(monkeys);
                String monkey2 = monkeys.get(child2).getValue(monkeys);
                switch (operator) {
                    case '+':
                        try {
                            return (Long.parseLong(monkey1) + Long.parseLong(monkey2)) + "";
                        } catch (Exception e) {
                            return "(" + monkey1 + "+" + monkey2 + ")";
                        }
                    case '-':
                        try {
                            return (Long.parseLong(monkey1) - Long.parseLong(monkey2)) + "";
                        } catch (Exception e) {
                            return "(" + monkey1 + "-" + monkey2 + ")";
                        }
                    case '*':
                        try {
                            return (Long.parseLong(monkey1) * Long.parseLong(monkey2)) + "";
                        } catch (Exception e) {
                            return "(" + monkey1 + "*" + monkey2 + ")";
                        }
                    case '/':
                        try {
                            return (Long.parseLong(monkey1) / Long.parseLong(monkey2)) + "";
                        } catch (Exception e) {
                            return "(" + monkey1 + "/" + monkey2 + ")";
                        }
                    case '=':
                        return "(" + monkey1 + "=" + monkey2 + ")";
                    default:
                        throw new UnsupportedOperationException("no such operation exists :" + operator);
                }
            }
        }
    }
}
