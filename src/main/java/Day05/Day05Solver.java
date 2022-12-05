package Day05;

import java.util.*;
import java.util.stream.Collectors;

public class Day05Solver {
    Map<Integer, ArrayList<String>> stacks;
    List<String> instructions;

    public Day05Solver(List<String> input) {
        List<String> stacks = new ArrayList<>();
        String line = input.remove(0);
        while (!line.startsWith(" "))
        {
            stacks.add(line);
            line = input.remove(0);
        }
        input.remove(0);
        this.instructions = input;
        stacks = rotateStringList(stacks).stream()
                .filter(s -> !s.startsWith("["))
                .filter(s -> !s.startsWith(" "))
                .filter(s -> !s.startsWith("]"))
                .map(s -> s.replaceAll(" ", ""))
                .collect(Collectors.toList());
        this.stacks = new HashMap<>();
        for (int i = 0; i < stacks.size(); i++) {
            this.stacks.put(i + 1, new ArrayList<>(Arrays.asList(stacks.get(i).split(""))));
        }
    }

    public String solveDay05a() {
        instructions.forEach(instruction -> {
            String[] instructionsArray = instruction
                    .replace("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split("\\s");
            doMoveDay05a(Integer.parseInt(instructionsArray[0]), Integer.parseInt(instructionsArray[1]), Integer.parseInt(instructionsArray[2]));
        });
        return getTopStacks();
    }

    public String solveDay05b() {
        instructions.forEach(instruction -> {
            String[] instructionsArray = instruction
                    .replace("move ", "")
                    .replace("from ", "")
                    .replace("to ", "")
                    .split("\\s");
            doMoveDay05b(Integer.parseInt(instructionsArray[0]), Integer.parseInt(instructionsArray[1]), Integer.parseInt(instructionsArray[2]));
        });
        return getTopStacks();
    }

    private void doMoveDay05a(int amount, int from, int to) {
        ArrayList<String> fromStack = stacks.get(from);
        ArrayList<String> toStack = stacks.get(to);
        for (int i = 0; i < amount; i++) {
            String crate = fromStack.remove(fromStack.size() - 1);
            toStack.add(crate);
        }
        stacks.put(from, fromStack);
        stacks.put(to, toStack);
    }

    private void doMoveDay05b(int amount, int from, int to) {
        ArrayList<String> fromStack = stacks.get(from);
        ArrayList<String> toStack = stacks.get(to);
        int initialHeight = toStack.size();
        for (int i = 0; i < amount; i++) {
            String crate = fromStack.remove(fromStack.size() - 1);
            toStack.add(initialHeight, crate);
        }
        stacks.put(from, fromStack);
        stacks.put(to, toStack);
    }

    private String getTopStacks() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stacks.size(); i++) {
            List<String> pile = stacks.get(i + 1);
            builder.append(pile.get(pile.size() - 1));
        }
        return builder.toString();
    }
    private List<String> rotateStringList(List<String> stringList) {
        List<String> rotatedStringList = new ArrayList<>();
        for (int i = 0; i < stringList.get(stringList.size() - 1).length(); i++) {
            StringBuilder newLineBuilder = new StringBuilder();
            for (int j = stringList.size() - 1; j >=0; j--) {
                newLineBuilder.append(stringList.get(j).charAt(i));
            }
            rotatedStringList.add(newLineBuilder.toString());
        }
        return rotatedStringList;
    }
}
