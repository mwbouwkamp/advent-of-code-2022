package Day11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11Solver {
    List<Monkey> monkeys;

    public Day11Solver(List<String> input) {
        this.monkeys = new ArrayList<>();
        input.add("");
        int index = 0;
        while (input.size() > 0) {
            List<String> monkeyInput = new ArrayList<>();
            monkeyInput.add(input.remove(0));
            monkeyInput.add(input.remove(0));
            monkeyInput.add(input.remove(0));
            monkeyInput.add(input.remove(0));
            monkeyInput.add(input.remove(0));
            monkeyInput.add(input.remove(0));
            input.remove(0);
            this.monkeys.add(new Monkey(monkeyInput, index));
            index++;
        }
    }

    public long solveDay11a(int cycles, boolean reducer) {
        for (int i = 1; i <= cycles; i++) {
            monkeys.forEach(monkey -> monkey.doTurn(monkeys, reducer));
            if (!reducer) {
//                if (i == 1 || i == 20 || i == 1000 || i == 2000 || i == 3000 || i == 4000 || i == 5000 || i == 6000 || i == 7000 || i == 8000 || i == 9000 || i == 10000) {
                    System.out.print(i + " | ");
                    monkeys.forEach(monkey -> System.out.print(monkey.numInspections + " "));
                    System.out.println();
//                }
            }
        }
        List<Long> numInspections = monkeys.stream()
                .map(monkey -> monkey.numInspections)
                .sorted()
                .collect(Collectors.toList());
        return numInspections.get(numInspections.size() - 1) * numInspections.get(numInspections.size() - 2);

    }

    static class Monkey {
        int index;
        List<BigInteger> items;
        char operationType;
        BigInteger operationFactor;
        BigInteger test;
        int indexTrue;
        int indexFalse;
        long numInspections;

        public Monkey(List<String> input, int index) {
            this.index = index;
            this.items = Arrays.stream(input.get(1).replace("  Starting items: ", "").split(", "))
                    .map(BigInteger::new)
                    .collect(Collectors.toList());
            this.operationType = input.get(2).charAt(23);
            String operationFactorString = input.get(2).substring(25);
            if (operationFactorString.equals("old")) {
                this.operationType = '^';
            } else {
                this.operationFactor = new BigInteger(operationFactorString);
            }
            this.test = new BigInteger(input.get(3).substring(21));
            this.indexTrue = Integer.parseInt(input.get(4).substring(29));
            this.indexFalse = Integer.parseInt(input.get(5).substring(30));
            this.numInspections = 0;
        }

        void addItem(BigInteger item) {
            this.items.add(item);
        }

        void doTurn(List<Monkey> monkeys, boolean reducer) {
            items.stream().parallel().forEach(item -> {
                numInspections++;
                switch (operationType) {
                    case '+':
                        item = item.add(operationFactor);
                        break;
                    case '-':
                        item = item.subtract(operationFactor);
                        break;
                    case '*':
                        item = item.multiply(operationFactor);
                        break;
                    case '/':
                        item = item.divide(operationFactor);
                        break;
                    case '^':
                        item = item.multiply(item);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal operationType: " + operationType);
                }
                if (reducer) {
                    item = item.divide(new BigInteger("3"));
                }
                if (item.mod(test).intValue() == 0) {
                    monkeys.get(indexTrue).addItem(item);
                } else {
                    monkeys.get(indexFalse).addItem(item);
                }
            });
            items = new ArrayList<>();
        }
    }

}
