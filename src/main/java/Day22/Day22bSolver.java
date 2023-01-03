package Day22;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day22bSolver {
    Map<String, CubeSide> cubeSides;
    String instructions;
    String activeCubeSide;
    Point position;
    int direction; // R = 0, B = 1, L = 2, T = 3
    int dimensions;

    public Day22bSolver(List<String> input, int dimensions) {
        this.cubeSides = new HashMap<>();
        List aLines = new ArrayList<String>();
        List bLines = new ArrayList<String>();
        List cLines = new ArrayList<String>();
        List dLines = new ArrayList<String>();
        List eLines = new ArrayList<String>();
        List fLines = new ArrayList<String>();
        for (int y = 0; y < dimensions; y++) {
            aLines.add(input.get(y).substring(dimensions, 2 * dimensions));
            bLines.add(input.get(y).substring(2 * dimensions, 3 * dimensions));
        }
        for (int y = dimensions; y < 2 * dimensions; y++) {
            cLines.add(input.get(y).substring(dimensions, 2 * dimensions));
        }
        for (int y = 2 * dimensions; y < 3 * dimensions; y++) {
            dLines.add(input.get(y).substring(0, dimensions));
            eLines.add(input.get(y).substring(dimensions, 2 * dimensions));
        }
        for (int y = 3 * dimensions; y < 4 * dimensions; y++) {
            fLines.add(input.get(y));
        }
        cubeSides.put("A", new CubeSide(aLines, "A"));
        cubeSides.put("B", new CubeSide(bLines, "B"));
        cubeSides.put("C", new CubeSide(cLines, "C"));
        cubeSides.put("D", new CubeSide(dLines, "D"));
        cubeSides.put("E", new CubeSide(eLines, "E"));
        cubeSides.put("F", new CubeSide(fLines, "F"));
        this.instructions = input.get(4 * dimensions + 1);
        this.activeCubeSide = "A";
        this.position = new Point(0, 0);
        this.direction = 0;
        this.dimensions = dimensions;
    }

    public int solveDay22b() {
        while (instructions.length() > 0) {
            if (instructions.startsWith("L")) {
                direction = direction > 0 ? direction - 1 : 3;
                instructions = instructions.substring(1);
            } else if (instructions.startsWith("R")) {
                direction = direction < 3 ? direction + 1 : 0;
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
                    String newCubeSide;
                    Point newPosition;
                    int newDirection;
                    String[] cornerInstructions;
                    switch (direction) {
                        case 0:
                            if (position.x < dimensions - 1) {
                                newCubeSide = activeCubeSide;
                                newPosition = new Point(position.x + 1, position.y);
                                newDirection = direction;
                            } else {
                                cornerInstructions = cubeSides.get(activeCubeSide).right.split("");
                                newCubeSide = cornerInstructions[0];
                                switch (cornerInstructions[1]) {
                                    case "L":
                                        newPosition = new Point(0, position.y);
                                        newDirection = 0;
                                        break;
                                    case "R":
                                        newPosition = new Point(dimensions - 1, dimensions - 1 - position.y);
                                        newDirection = 2;
                                        break;
                                    case "B":
                                        newPosition = new Point(position.y, dimensions - 1);
                                        newDirection = 3;
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Illegal corner instructions: " + Arrays.toString(cornerInstructions));
                                }
                            }
                            break;
                        case 1:
                            if (position.y < dimensions - 1) {
                                newCubeSide = activeCubeSide;
                                newPosition = new Point(position.x, position.y + 1);
                                newDirection = direction;
                            } else {
                                cornerInstructions = cubeSides.get(activeCubeSide).bottom.split("");
                                newCubeSide = cornerInstructions[0];
                                switch (cornerInstructions[1]) {
                                    case "R":
                                        newPosition = new Point(dimensions - 1, position.x);
                                        newDirection = 2;
                                        break;
                                    case "T":
                                        newPosition = new Point(position.x, 0);
                                        newDirection = 1;
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Illegal corner instructions: " + Arrays.toString(cornerInstructions));
                                }
                            }
                            break;
                        case 2:
                            if (position.x > 0) {
                                newCubeSide = activeCubeSide;
                                newPosition = new Point(position.x - 1, position.y);
                                newDirection = direction;
                            } else {
                                cornerInstructions = cubeSides.get(activeCubeSide).left.split("");
                                newCubeSide = cornerInstructions[0];
                                switch (cornerInstructions[1]) {
                                    case "L":
                                        newPosition = new Point(0, dimensions - 1 - position.y);
                                        newDirection = 0;
                                        break;
                                    case "R":
                                        newPosition = new Point(dimensions - 1, position.y);
                                        newDirection = 2;
                                        break;
                                    case "T":
                                        newPosition = new Point(position.y, 0);
                                        newDirection = 1;
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Illegal corner instructions: " + Arrays.toString(cornerInstructions));
                                }
                            }
                            break;
                        case 3:
                            if (position.y > 0) {
                                newCubeSide = activeCubeSide;
                                newPosition = new Point(position.x, position.y - 1);
                                newDirection = direction;
                            } else {
                                cornerInstructions = cubeSides.get(activeCubeSide).top.split("");
                                newCubeSide = cornerInstructions[0];
                                switch (cornerInstructions[1]) {
                                    case "L":
                                        newPosition = new Point(0, position.x);
                                        newDirection = 0;
                                        break;
                                    case "B":
                                        newPosition = new Point(position.x, dimensions - 1);
                                        newDirection = 3;
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Illegal corner instructions: " + Arrays.toString(cornerInstructions));
                                    }
                                }
                            break;
                        default:
                            throw new IllegalArgumentException("Direction not allowed: " + direction);
                    }
                    if (cubeSides.get(newCubeSide).rows.get(newPosition.y).charAt(newPosition.x) == '#') {
                        System.out.println("WALL");
                        i = Integer.MAX_VALUE - 1;
                    } else {
                        activeCubeSide = newCubeSide;
                        position = newPosition;
                        direction = newDirection;
                        System.out.println(activeCubeSide + " " + position + " " + direction);
                    }
                }
                instructions = instructions.substring(stepsString.length());
            }
        }
        int xFactor = 0;
        int yFactor = 0;
        switch (activeCubeSide) {
            case "A":
                xFactor = 1;
                break;
            case "B":
                xFactor = 2;
                break;
            case "C":
                xFactor = 1;
                yFactor = 1;
                break;
            case "D":
                yFactor = 2;
                break;
            case "E":
                xFactor = 1;
                yFactor = 2;
                break;
            case "F":
                yFactor = 3;
                break;
            default:
                throw new IllegalArgumentException("Illegal cubeside: " + activeCubeSide);
        }
        return 1000 * (yFactor * dimensions + position.y + 1) + 4 * (xFactor * dimensions + position.x + 1) + direction;
    }

    static class  CubeSide {
        List<String> rows;
        String identifier;
        String top, bottom, left, right;

        CubeSide(List<String> rows, String identifier) {
            this.rows = rows;
            this.identifier = identifier;
            switch (identifier) {
                case "A":
                    this.top = "FL";
                    this.bottom = "CT";
                    this.left = "DL";
                    this.right = "BL";
                    break;
                case "B":
                    this.top = "FB";
                    this.bottom = "CR";
                    this.left = "AR";
                    this.right = "ER";
                    break;
                case "C":
                    this.top = "AB";
                    this.bottom = "ET";
                    this.left = "DT";
                    this.right = "BB";
                    break;
                case "D":
                    this.top = "CL";
                    this.bottom = "FT";
                    this.left = "AL";
                    this.right = "EL";
                    break;
                case "E":
                    this.top = "CB";
                    this.bottom = "FR";
                    this.left = "DR";
                    this.right = "BR";
                    break;
                case "F":
                    this.top = "DB";
                    this.bottom = "BT";
                    this.left = "AT";
                    this.right = "EB";
                    break;
                default:
                    throw new IllegalArgumentException("Illegal side: " + identifier);
            }
        }
    }
}
