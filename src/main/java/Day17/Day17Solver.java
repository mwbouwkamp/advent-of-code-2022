package Day17;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day17Solver {
    List<Shape> basicShapes;
    char[] instructions;
    List<Shape> tunnel;
    List<Integer> heights;

    public Day17Solver(char[] input) {
        this.instructions = input;
        List<String> firstShape = new ArrayList<>();
        firstShape.add("####");
        List<String> secondShape = new ArrayList<>();
        secondShape.add(".#.");
        secondShape.add("###");
        secondShape.add(".#.");
        List<String> thirdShape = new ArrayList<>();
        thirdShape.add("###");
        thirdShape.add("..#");
        thirdShape.add("..#");
        List<String> fourthShape = new ArrayList<>();
        fourthShape.add("#");
        fourthShape.add("#");
        fourthShape.add("#");
        fourthShape.add("#");
        List<String> fifthShape = new ArrayList<>();
        fifthShape.add("##");
        fifthShape.add("##");
        List<String> floor = new ArrayList<>();
        floor.add("#######");
        this.basicShapes = new ArrayList<>();
        this.basicShapes.add(new Shape(firstShape, null, 4));
        this.basicShapes.add(new Shape(secondShape, null, 5));
        this.basicShapes.add(new Shape(thirdShape, null, 5));
        this.basicShapes.add(new Shape(fourthShape, null, 4));
        this.basicShapes.add(new Shape(fifthShape, null, 4));
        this.tunnel = new ArrayList<>();
        tunnel.add(new Shape(floor, new Point(0, -1), 7));
        this.heights = new ArrayList<>();
    }

    public long solveDay17(int amount, char day) {
        int rockPointer = 0;
        int pushPointer = 0;
        Shape currentShape = new Shape(basicShapes.get(0), new Point(2, getTopOfStack() + 3));
        tunnel.add(currentShape);
        while (true) {
            char push = instructions[pushPointer % instructions.length];
            if (push == '<') {
                currentShape.move(-1, 0);
                if (currentShape.overlaps(tunnel) || currentShape.position.x < 0) {
                    currentShape.move(1, 0);
                }
            } else {
                currentShape.move(1, 0);
                if (currentShape.overlaps(tunnel) || currentShape.position.x + currentShape.width > 7) {
                    currentShape.move(-1, 0);
                }
            }
            pushPointer++;
            currentShape.move(0, -1);
            if (currentShape.overlaps(tunnel)) {
                currentShape.move(0, 1);
                rockPointer++;
                tunnel.add(currentShape);
                currentShape = new Shape(basicShapes.get(rockPointer % 5), new Point(2, getTopOfStack() + 3));
                System.out.println(tunnel.size() + " " + getTunnelTop());
                heights.add(getTunnelTop());
                if (tunnel.size() == amount - 1) {
                    return day == 'a' ? getTunnelTop() : solveB(amount);
                }
            }
        }
    }

    private long solveB(int amount) {
//        int repetitionSize = 1750;  // Found in Excel that there is a periodicity in the deltas from one height to the next
        int repetitionSize = 1725;  // Found in Excel that there is a periodicity in the deltas from one height to the next
        System.out.println("------------------------");
        long toReturn = -1L;
        for (long i = amount - 10; i < amount; i++) {
            long numRocks = i - 3; // weird mismatch of 3!?!
            if (i == amount - 1) {
                numRocks = 1000000000000L;
            }
            int timesRepetition = (int) (numRocks / repetitionSize);
            int remainder = (int) (numRocks % repetitionSize);
            long repititionGrowth = heights.get(1000 + repetitionSize) - heights.get(1000);
            toReturn = timesRepetition * repititionGrowth + heights.get(remainder) - 1;
            System.out.println(i + " " + toReturn);
        }
        return toReturn;




    }
    private Integer getTunnelTop() {
        return tunnel.stream()
                .map(shape -> shape.position.y + shape.height)
                .reduce(0, (a, b) -> a > b ? a : b);
    }

    @Override
    public String toString() {
        int maxY = getTunnelTop() + 3;
        Set<Point> allPoints = tunnel.stream()
                .map(Shape::getPoints)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        StringBuilder toReturn = new StringBuilder();
        for (int y = maxY; y >= 0; y--) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x <= 7; x++) {
                if (allPoints.contains(new Point(x, y))) {
                    line.append("#");
                } else {
                    line.append(".");
                }
            }
            toReturn.append(line).append("\n");
        }
        return toReturn.toString();
    }
    private int getTopOfStack() {
        return getTunnelTop();
    }

    static class Shape {
        List<String> lines;
        int width;
        int height;
        Point position;
        int numPoints;

        Shape(List<String> lines, Point position, int numPoints) {
            this.lines = lines;
            this.width = lines.stream()
                    .map(String::length)
                    .reduce(0, (a, b) -> a > b ? a : b);
            this.height = lines.size();
            this.position = position;
            this.numPoints = numPoints;
        }

        Shape(Shape basicShape, Point position) {
            this.lines = basicShape.lines;
            this.width = basicShape.width;
            this.height = basicShape.height;
            this.position = position;
            this.numPoints = basicShape.numPoints;
        }

        void move(int x, int y) {
            this.position = new Point(this.position.x + x, this.position.y + y);
        }

        boolean overlaps(List<Shape> otherShapes) {
            //                    .filter(shape -> shape.position.y + shape.height < position.y)
            //                    .filter(shape -> shape.position.y > position.y + height)
            //                    .filter(shape -> shape.position.x + shape.width < position.x)
            //                    .filter(shape -> shape.position.x > position.x + width)
            return otherShapes.stream()
                    .filter(shape -> shape != this)
                    .anyMatch(this::closeShapesOverlap);
        }

        boolean closeShapesOverlap(Shape otherShape) {
            Set<Point> points = new HashSet<>();
            points.addAll(getPoints());
            points.addAll(otherShape.getPoints());
            return points.size() != numPoints + otherShape.numPoints;
        }

        List<Point> getPoints() {
            List<Point> toReturn = new ArrayList<>();
            for(int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (lines.get(y).charAt(x) == '#') {
                        toReturn.add(new Point(position.x + x, position.y + y));
                    }
                }
            }
            return toReturn;
        }
    }
}
