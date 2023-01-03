package Day22;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day22aSolver {
    MonkeyMap map;
    String instructions;

    public Day22aSolver(List<String> input) {
        this.map = new MonkeyMap(input.stream()
                .filter(line -> !line.equals(""))
                .filter(line -> !line.contains("R"))
                .filter(line -> !line.contains("L"))
                .map(line -> " " + line + " ")
                .collect(Collectors.toList()));
        this.instructions = input.get(input.size() - 1);
    }

    public int solveDay22a() {
        while (instructions.length() > 0) {
            if (instructions.startsWith("L")) {
                map.rotate("L");
                instructions = instructions.substring(1);
            } else if (instructions.startsWith("R")) {
                map.rotate("R");
                instructions = instructions.substring(1);
            } else {
                int positionR = instructions.contains("R") ? instructions.indexOf("R") : Integer.MAX_VALUE;
                int positionL = instructions.contains("L") ? instructions.indexOf("L"): Integer.MAX_VALUE;
                int positionMin = Math.min(positionR, positionL);
                if (positionMin == Integer.MAX_VALUE) {
                    System.out.println();
                }
                String stepsString = positionMin != Integer.MAX_VALUE ? instructions.substring(0, Math.min(positionR, positionL)) : instructions;
                int steps = Integer.parseInt(stepsString);
                for (int i = 0; i < steps; i++) {
                    Point newPosition;
                    switch (map.orientation) {
                        case 0:
                            if (map.position.x  + 1 < map.lines.get(map.position.y).length() && map.lines.get(map.position.y).charAt(map.position.x + 1) != ' ') {
                                newPosition = new Point(map.position.x + 1, map.position.y);
                            } else {
                                newPosition = new Point(map.getFirstInRow(map.position.y), map.position.y);
                            }
                            break;
                        case 1:
                            if (map.position.y + 1 < map.lines.size() && map.position.x < map.lines.get(map.position.y + 1).length() && map.lines.get(map.position.y + 1).charAt(map.position.x) != ' ') {
                                newPosition = new Point(map.position.x, map.position.y + 1);
                            } else {
                                newPosition = new Point(map.position.x, map.getFirstInColumn(map.position.x));
                            }
                            break;
                        case 2:
                            if (map.position.x - 1 >= 0 && map.lines.get(map.position.y).charAt(map.position.x - 1) != ' ') {
                                newPosition = new Point(map.position.x - 1, map.position.y);
                            } else {
                                newPosition = new Point(map.getLastInRow(map.position.y), map.position.y);
                            }
                            break;
                        case 3:
                            if (map.position.y - 1 >= 0 && map.lines.get(map.position.y - 1).charAt(map.position.x) != ' ') {
                                newPosition = new Point(map.position.x, map.position.y - 1);
                            } else {
                                newPosition = new Point(map.position.x, map.getLastInColumn(map.position.x));
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("orientation not allowed: " + map.orientation);
                    }
                    if (map.lines.get(newPosition.y).charAt(newPosition.x) == '#') {
                        i = Integer.MAX_VALUE - 1;
                        System.out.println("WALL");
                    } else {
                        map.position = newPosition;
                        System.out.println(map.position);
                    }
                }
                instructions = instructions.substring(stepsString.length());
            }
        }
        System.out.println("----");
        System.out.println(map.position);
        System.out.println(map.orientation);
        return 1000 * (map.position.y + 1) + 4 * (map.position.x) + map.orientation;
    }

    static class MonkeyMap {
        List<String> lines;
        int orientation;
        Point position;

        MonkeyMap(List<String> lines) {
            int maxLength = lines.stream()
                    .map(String::length)
                    .reduce(0, Integer::max);
            this.lines = lines.stream()
                    .map(line -> line + StringUtils.repeat(" ",  maxLength - line.length()))
                    .collect(Collectors.toList());
            this.orientation = 0;
            this.position = new Point(getFirstInRow(0), 0);
        }

        void rotate(String direction) {
            if (direction.equals("L")) {
                orientation -= 1;
                orientation = orientation < 0 ? 3 : orientation;
            } else {
                orientation += 1;
                orientation = orientation > 3 ? 0 : orientation;
            }
        }
        int getFirstInRow(int row) {
            String line = lines.get(row);
            int posFirstOpen = line.contains(".") ? line.indexOf(".") : Integer.MAX_VALUE;
            int posFirstWall = line.contains("#") ? line.indexOf("#") : Integer.MAX_VALUE;
            return Math.min(posFirstOpen, posFirstWall);
        }

        int getLastInRow(int row) {
            String line = lines.get(row);
            int posFirstOpen = line.contains(".") ? line.lastIndexOf(".") : Integer.MIN_VALUE;
            int posFirstWall = line.contains("#") ? line.lastIndexOf("#") : Integer.MIN_VALUE;
            return Math.max(posFirstOpen, posFirstWall);
        }

        int getFirstInColumn(int column) {
            for (int y = 0; y < lines.size(); y++) {
                if (lines.get(y).charAt(column) == '.' || lines.get(y).charAt(column) == '#') {
                    return y;
                }
            }
            return -1;
        }

        int getLastInColumn(int column) {
            for (int y = lines.size() - 1; y >= 0; y--) {
                if (lines.get(y).length() > column && (lines.get(y).charAt(column) == '.' || lines.get(y).charAt(column) == '#')) {
                    return y;
                }
            }
            return -1;
        }
    }
}
