package Day12;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12Solver {
    List<String> map;
    Point initialStartingPoint;
    Point endPoint;
    List<Point> startingPoints;

    public Day12Solver(List<String> input) {
        this.map = input;
        this.initialStartingPoint = getPointForCharacter(input, "S");
        this.endPoint = getPointForCharacter(input, "E");
        this.map.set(this.initialStartingPoint.y, this.map.get(this.initialStartingPoint.y).replace("S", "a"));
        this.map.set(this.endPoint.y, this.map.get(this.endPoint.y).replace("E", "z"));
        this.startingPoints = getPointsForCharacter(this.map);
    }

    private Point getPointForCharacter(List<String> input, String character) {
        int startingPointY = IntStream.range(0, input.size())
                .filter(i -> input.get(i).contains(character))
                .findFirst()
                .orElse(-1);
        int startingPointX = input.get(startingPointY).indexOf(character);
        return new Point(startingPointX, startingPointY);
    }

    private List<Point> getPointsForCharacter(List<String> input) {
        List<Point> toReturn = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(0).length(); x++) {
                if (input.get(y).charAt(x) == 'a') {
                    toReturn.add(new Point(x, y));
                }
            }
        }
        return toReturn;
    }

    public int solveDay12b() {
        System.out.println("size " + startingPoints.size());
        return startingPoints.stream()
                .map(this::findShortesPath)
                .peek(System.out::println)
                .filter(value -> value != -2)
                .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
    }

    public int solveDay12a() {
        return findShortesPath(initialStartingPoint);
    }

    private int findShortesPath(Point startingPoint) {
        Map<Point, State> visited = new HashMap<>();
        Map<State, Integer> fringe = new HashMap<>();
        State startingState = new State(new Point(startingPoint.x, startingPoint.y), map.get(startingPoint.y).charAt(startingPoint.x), map.get(startingPoint.y).charAt(startingPoint.x) + "");
        fringe.put(startingState, startingState.path.length() + startingState.getHeuristic(endPoint));
        visited.put(startingState.position, startingState);
        List<State> possibles = new ArrayList<>();
        while (fringe.size() > 0) {
            State toCheck = fringe.keySet().stream()
                    .reduce((a, b) -> fringe.get(a) < fringe.get(b) ? a : b)
                    .orElse(null);
            fringe.remove(toCheck);
            visited.put(toCheck.position, toCheck);
            if (toCheck.position.equals(endPoint)) {
                possibles.add(toCheck);
            }
            else {
                List<State> childrenToAdd = toCheck.getChildren(map).stream()
                        .filter(child -> !visited.containsKey(child.position) || child.path.length() < visited.get(child.position).path.length())
                        .filter(child -> fringe.keySet().stream()
                                .noneMatch(key -> key.position.equals(child.position) && key.path.length() + key.getHeuristic(endPoint) < child.path.length() + child.getHeuristic(endPoint)))
                        .collect(Collectors.toList());
                childrenToAdd.forEach(child -> {
                    List<State> alreadyOnFringe = fringe.keySet().stream()
                            .filter(key -> key.position.equals(child.position))
                            .collect(Collectors.toList());
                    if (alreadyOnFringe.size() > 0) {
                        fringe.remove(alreadyOnFringe.get(0));
                    }
                    fringe.put(child, child.path.length() + child.getHeuristic(endPoint));
                });
            }
        }
        return possibles.stream().map(state -> state.path.length()).reduce((a, b) -> a < b ? a : b).orElse(-1) - 1;
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
                if (x != position.x && x >= 0 && x < map.get(0).length() && map.get(position.y).charAt(x) <= value + 1) {
                    children.add(new State(new Point(x, position.y), map.get(position.y).charAt(x), path + map.get(position.y).charAt(x)));
                }
            }
            for (int y = position.y - 1; y <= position.y + 1; y++) {
                if (y != position.y && y >= 0 && y < map.size() && map.get(y).charAt(position.x) <= value + 1) {
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
