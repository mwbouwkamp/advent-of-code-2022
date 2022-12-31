package Day21;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day21aSolver {
    Map<String, Monkey> monkeys;

    public Day21aSolver(List<String> input) {
        this.monkeys = input.stream()
                .map(line -> new Monkey(line))
                .collect(Collectors.toMap(monkey -> monkey.identifier, monkey -> monkey));
    }

    public long solveDay21a() {
        return monkeys.get("root").getValue(monkeys);
    }
    static class Monkey {
        String identifier;
        long value;
        char operator;
        String child1;
        String child2;

        Monkey(String input) {
            String[] inputParts = input.split(": ");
            this.identifier = inputParts[0];
            try {
                this.value = Long.parseLong(inputParts[1]);
            } catch (IllegalArgumentException e) {
                if (inputParts[1].contains("+")) {
                    operator = '+';
                } else if (inputParts[1].contains("-")) {
                    operator = '-';
                } else if (inputParts[1].contains("*")) {
                    operator = '*';
                } else if (inputParts[1].contains("/")) {
                    operator = '/';
                }
                String[] operationParts = inputParts[1].split("[-+*/]");
                child1 = operationParts[0].replace(" ", "");
                child2 = operationParts[1].replace(" ", "");
                value = Long.MIN_VALUE;
            }
        }

        long getValue(Map<String, Monkey> monkeys) {
            if (value != Long.MIN_VALUE) {
                return value;
            } else {
                Monkey monkey1 = monkeys.get(child1);
                Monkey monkey2 = monkeys.get(child2);
                switch (operator) {
                    case '+':
                        return monkey1.getValue(monkeys) + monkey2.getValue(monkeys);
                    case '-':
                        return monkey1.getValue(monkeys) - monkey2.getValue(monkeys);
                    case '*':
                        return monkey1.getValue(monkeys) * monkey2.getValue(monkeys);
                    case '/':
                        return monkey1.getValue(monkeys) / monkey2.getValue(monkeys);
                    default:
                        throw new UnsupportedOperationException("no such operation exists :" + operator);
                }
            }
        }
    }
}
