package Day14;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14Solver {
    List<Point> positions;

    public Day14Solver(List<String> input) {
        this.positions = new ArrayList<>();
        input.forEach(this::parseInput);
    }

    public int SolveDay14a(char part) {
        int maxDepth = part == 'a' ? Integer.MAX_VALUE : positions.stream()
                .map(position -> position.y)
                .reduce(0, (a, b) -> a > b ? a : b) + 2;
        int counter = 0;
        Point current = new Point(500,0);
        while (true) {
            Point finalCurrent = current;
            int positionsUnderY = positions.stream()
                    .filter(position -> position.x == finalCurrent.x)
                    .map(position -> position.y)
                    .filter(y -> y > finalCurrent.y)
                    .reduce(maxDepth, (a, b) -> a < b ? a : b);
            if (positionsUnderY == Integer.MAX_VALUE || positionsUnderY < current.y) {
                return counter;
            }
            current = new Point(current.x, positionsUnderY - 1);
            Point leftUnder = new Point(current.x - 1, positionsUnderY);
            Point rightUnder = new Point(current.x + 1, positionsUnderY);
            if (current.x == 500 && current.y == 0 && positions.contains(new Point(500, 1)) && positions.contains(new Point(501, 1)) && positions.contains(new Point(499, 1))) {
                return counter + 1;
            }
            if (!positions.contains(leftUnder) && leftUnder.y < maxDepth) {
                current = new Point(leftUnder.x, leftUnder.y -1);
            } else if (!positions.contains(rightUnder) && rightUnder.y < maxDepth) {
                current = new Point(rightUnder.x, rightUnder.y - 1);
                if (current.x == 501 && current.y == 0 && positions.contains(new Point(501, 1))) {
                    return counter + 1;
                }
            } else {
                positions.add(current);
                counter++;
                System.out.println(counter);
                current = new Point(500, 0);
            }
        }
    }

    private void parseInput(String input) {
        String[] inputParts = input.split(" -> ");
        for (int i = 0; i < inputParts.length - 1; i++) {
            Point first = createPoint(inputParts[i]);
            Point second = createPoint(inputParts[i + 1]);
            if (first.x == second.x) {
                int minY = Math.min(first.y, second.y);
                int maxY = Math.max(first.y, second.y);
                for (int y = minY; y <= maxY; y++) {
                    positions.add(new Point(first.x, y));
                }
            } else {
                int minX = Math.min(first.x, second.x);
                int maxX = Math.max(first.x, second.x);
                for (int x = minX; x <= maxX; x++) {
                    positions.add(new Point(x, first.y));
                }
            }
        }
    }

    private Point createPoint(String inputParts) {
        String[] firstString = inputParts.split(",");
        return new Point(Integer.parseInt(firstString[0]), Integer.parseInt(firstString[1]));
    }
}
