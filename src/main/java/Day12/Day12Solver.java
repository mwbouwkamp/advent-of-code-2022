package Day12;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12Solver {
    List<String> map;
    Point startingPoint;
    Point endPoint;

    public Day12Solver(List<String> input) {
        this.map = input;
        this.startingPoint = getPointForCharacter(input, "S");
        this.endPoint = getPointForCharacter(input, "E");
        this.map.set(this.startingPoint.y, this.map.get(this.startingPoint.y).replace("S", "a"));
        this.map.set(this.endPoint.y, this.map.get(this.endPoint.y).replace("E", "z"));
    }

    private Point getPointForCharacter(List<String> input, String character) {
        int startingPointY = IntStream.range(0, input.size())
                .filter(i -> input.get(i).contains(character))
                .findFirst()
                .orElse(-1);
        int startingPointX = input.get(startingPointY).indexOf(character);
        return new Point(startingPointX, startingPointY);
    }

    public int solveDay12a() {
        Map<Point, State> visited = new HashMap<>();
        Map<Integer, ArrayList<State>> fringe = new HashMap<>();
        State startingState = new State(new Point(startingPoint.x, startingPoint.y), map.get(startingPoint.y).charAt(startingPoint.x), map.get(startingPoint.y).charAt(startingPoint.x) + "");
        ArrayList initialStateList = new ArrayList();
        initialStateList.add(startingState);
        fringe.put(startingState.path.length() + startingState.getHeuristic(endPoint), initialStateList);
        visited.put(startingState.position, startingState);
        while (fringe.size() > 0) {
            int lowest = fringe.keySet().stream().sorted().collect(Collectors.toList()).get(0);
            ArrayList<State> lowestStates = fringe.get(lowest);
            State toCheck;
            if (lowestStates.size() > 0) {
                toCheck = lowestStates.remove(0);
                fringe.put(lowest, lowestStates);
            } else {
                fringe.remove(lowest);
                continue;
            }

            System.out.println(toCheck.position);
            System.out.println(fringe.size());

//            if (visited.keySet().stream()
//                    .filter(key -> key.equals(toCheck.position))
//                    .map(key -> visited.get(key).path.length())
//                    .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b) < toCheck.path.length()) {
//                continue;
//            }
            visited.put(toCheck.position, toCheck);
            if (toCheck.position == endPoint) {
                return toCheck.path.length();
            }
            else {
                List<State> childrenToAdd = toCheck.getChildren(map).stream()
                        .filter(child -> !visited.containsKey(child.position) || child.path.length() < visited.get(child.position).path.length())
                        .filter(child -> fringe.keySet().stream()
                                .filter(key -> key < child.path.length())
                                .map(fringe::get)
                                .flatMap(List::stream)
                                .noneMatch(state -> state.position.equals(child.position)))
                        .filter(child -> fringe.keySet().stream()
                                .filter(key -> key <= child.path.length())
                                .map(fringe::get)
                                .noneMatch(stateList -> stateList.contains(child)))
                        .collect(Collectors.toList());
                childrenToAdd.forEach(child -> {
//                    fringe.keySet().stream()
//                            .filter(key -> key >= child.path.length())
//                            .forEach(key -> {
//                                ArrayList<State> statesForKey = (ArrayList<State>) fringe.get(key).stream()
//                                        .filter(state -> !state.position.equals(child.position))
//                                        .collect(Collectors.toList());
//                                fringe.put(key, statesForKey);
//
//                            });
                    ArrayList<State> currentStates = fringe.get(child.path.length() + child.getHeuristic(endPoint));
                    if (currentStates == null) {
                        currentStates = new ArrayList<>();
                    }
                    currentStates.add(child);
                    fringe.put(child.path.length() + child.getHeuristic(endPoint), currentStates);
                });
            }
        }
        return -1;
    }
    static class State {
        char value;
        Point position;
        String path;

        public State(Point position, char value, String path) {
            this.position = position;
            this.value = value;
            this.path = path;
        }

        public List<State> getChildren(List<String> map) {
            List<State> children = new ArrayList<>();
            for (int x = position.x - 1; x <= position.x + 1; x++) {
                if (x != position.x && x > 0 && x < map.get(0).length() && map.get(position.y).charAt(x) <= value + 1) {
                    children.add(new State(new Point(x, position.y), map.get(position.y).charAt(x), path + map.get(position.y).charAt(x)));
                }
            }
            for (int y = position.y - 1; y <= position.y + 1; y++) {
                if (y != position.y && y > 0 && y < map.size() && map.get(y).charAt(position.x) <= value + 1) {
                    children.add(new State(new Point(position.x, y), map.get(y).charAt(position.x), path + map.get(y).charAt(position.x)));
                }
            }
            return children;
        }

        public int getHeuristic(Point endPoint) {
            return (position.x - endPoint.x) * (position.x - endPoint.x) + (position.y - endPoint.y) * (position.y - endPoint.y);

        }
    }

}
