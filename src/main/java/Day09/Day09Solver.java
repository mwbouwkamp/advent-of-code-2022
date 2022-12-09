package Day09;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day09Solver {
    Set<Point> visited;
    List<String> commands;
    List<Point> rope;


    public Day09Solver(List<String> input, int ropeLength) {
        this.commands = input;
        this.visited = new HashSet<>();
        this.rope = new ArrayList<>();
        for (int i = 0; i < ropeLength; i++) {
            this.rope.add(new Point(0, 0));
        }
        visited.add(this.rope.get(0));
    }

    public int solveDay09(){
        commands.forEach(this::doMove);
//        print();
        return visited.size();
    }

    private void print() {
        int minX = visited.stream()
                .map(point -> point.x)
                .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
        int maxX = visited.stream()
                .map(point -> point.x)
                .reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);
        int minY = visited.stream()
                .map(point -> point.y)
                .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
        int maxY = visited.stream()
                .map(point -> point.y)
                .reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);
        List<Point> toPrint = visited.stream()
                .map(point -> new Point(point.x - minX, point.y - minY))
                .collect(Collectors.toList());
        maxX -= minX;
        maxY -= minY;
        for (int y = 0; y <= maxY; y++) {
            StringBuilder output = new StringBuilder();
            for (int x = 0; x <= maxX; x++) {
                output.append(toPrint.contains(new Point(x, y)) ? "#" : ".");
            }
            System.out.println(output);
        }
    }
    private void doMove(String command) {
        String[] commandParts = command.split(" ");
        for (int i = 0; i < Integer.parseInt(commandParts[1]); i++) {
            for (int r = 0; r < rope.size() - 1; r++) {
                Point head = rope.get(r);
                Point tail = rope.get(r + 1);
                if (r == 0) {
                    switch (commandParts[0]) {
                        case "U":
                            head = new Point(head.x, head.y - 1);
                            break;
                        case "D":
                            head = new Point(head.x, head.y + 1);
                            break;
                        case "L":
                            head = new Point(head.x - 1, head.y);
                            break;
                        case "R":
                            head = new Point(head.x + 1, head.y);
                            break;
                        default:
                            throw new IllegalArgumentException("Command not allowed: " + commandParts[0]);
                    }
                }
                if (!isTouching(head, tail)) {
                    tail = whereTo(head, tail, commandParts[0]);
                }
                rope.set(r, head);
                rope.set(r + 1, tail);
            }
            visited.add(rope.get(rope.size() - 1));
        }
    }

    private Point whereTo(Point head, Point tail, String direction) {
        if (Math.abs(head.x - tail.x) > 1 && Math.abs(head.y - tail.y) > 1) {
            return new Point(tail.x + (head.x - tail.x) / 2, tail.y + (head.y - tail.y) / 2);
        }
        if (Math.abs(head.x - tail.x) > Math.abs(head.y - tail.y)) {
            if (head.x - tail.x > 1) {
                return new Point(head.x - 1, head.y);
            }
            if (head.x - tail.x < -1) {
                return new Point(head.x + 1, head.y);
            }
        } else {
            if (head.y - tail.y > 1) {
                return new Point(head.x, head.y - 1);
            }
            if (head.y - tail.y < -1) {
                return new Point(head.x, head.y + 1);
            }
        }
        throw new UnsupportedOperationException("Move not allowed");
    }

    private boolean isTouching(Point first, Point second) {
        return Math.abs(first.x - second.x) <= 1 && Math.abs(first.y - second.y) <= 1;
    }
}
