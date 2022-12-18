package Day18;

import java.util.*;
import java.util.stream.Collectors;

public class Day18Solver {
    List<Cube> cubes;
    Set<Cube> availableCubes;

    public Day18Solver (List<String> input) {
        this.cubes = input.stream()
                .map(line -> new Cube(line))
                .collect(Collectors.toList());
        this.availableCubes = new HashSet<>();
    }

    public long solveDay18a() {
        return cubes.stream()
                .map(cube -> {
                    cube.setFreeSidesDaya(cubes);
                    return cube.countFreeSides();
                })
                .reduce(0L, Long::sum);
    }

    public long solveDay18b() {
        int maxX = cubes.stream()
                .map(cube -> cube.x)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE) + 1;
        int minX = cubes.stream()
                .map(cube -> cube.x)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE) - 1;
        int maxY = cubes.stream()
                .map(cube -> cube.y)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE) + 1;
        int minY = cubes.stream()
                .map(cube -> cube.y)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE) - 1;
        int maxZ = cubes.stream()
                .map(cube -> cube.z)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE) + 1;
        int minZ = cubes.stream()
                .map(cube -> cube.z)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE) - 1;
        Cube startingCube = new Cube(minX, minY, minZ);
        Set<Cube> fringe = new HashSet<>();
        fringe.add(startingCube);
        while (fringe.size() > 0) {
            Cube toCheck = fringe.iterator().next();
            fringe.remove(toCheck);
            availableCubes.add(toCheck);
            if (toCheck.x - 1 >= minX && cubes.stream().noneMatch(cube -> cube.x == toCheck.x - 1 && cube.y == toCheck.y && cube.z == toCheck.z)) {
                fringe.add(new Cube(toCheck.x - 1, toCheck.y, toCheck.z));
            }
            if (toCheck.x + 1 <= maxX && cubes.stream().noneMatch(cube -> cube.x == toCheck.x + 1 && cube.y == toCheck.y && cube.z == toCheck.z)) {
                fringe.add(new Cube(toCheck.x + 1, toCheck.y, toCheck.z));
            }
            if (toCheck.y - 1 >= minY && cubes.stream().noneMatch(cube -> cube.x == toCheck.x && cube.y == toCheck.y - 1 && cube.z == toCheck.z)) {
                fringe.add(new Cube(toCheck.x, toCheck.y - 1, toCheck.z));
            }
            if (toCheck.y + 1 <= maxY && cubes.stream().noneMatch(cube -> cube.x == toCheck.x && cube.y == toCheck.y + 1 && cube.z == toCheck.z)) {
                fringe.add(new Cube(toCheck.x, toCheck.y + 1, toCheck.z));
            }
            if (toCheck.z - 1 >= minZ && cubes.stream().noneMatch(cube -> cube.x == toCheck.x && cube.y == toCheck.y && cube.z == toCheck.z - 1)) {
                fringe.add(new Cube(toCheck.x, toCheck.y, toCheck.z - 1));
            }
            if (toCheck.z + 1 <= maxZ && cubes.stream().noneMatch(cube -> cube.x == toCheck.x && cube.y == toCheck.y && cube.z == toCheck.z + 1)) {
                fringe.add(new Cube(toCheck.x, toCheck.y, toCheck.z + 1));
            }
            fringe.removeAll(availableCubes);
        }
        return cubes.stream()
                .map(cube -> {
                    cube.setFreeSidesDayb(availableCubes);
                    return cube.countFreeSides();
                })
                .reduce(0L, Long::sum);
    }

    static class Cube {
        int x;
        int y;
        int z;
        List<Boolean> freeSides;

        Cube(String input) {
            String[] inputParts = input.split(",");
            this.x = Integer.parseInt(inputParts[0]);
            this.y = Integer.parseInt(inputParts[1]);
            this.z = Integer.parseInt(inputParts[2]);
            this.freeSides = new ArrayList<>();
        }

        Cube(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        void setFreeSidesDaya(List<Cube> otherCubes) {
            freeSides.add(otherCubes.stream().noneMatch(cube -> cube.x - 1 == x && cube.y == y && cube.z == z));
            freeSides.add(otherCubes.stream().noneMatch(cube -> cube.x + 1 == x && cube.y == y && cube.z == z));
            freeSides.add(otherCubes.stream().noneMatch(cube -> cube.x == x && cube.y - 1 == y && cube.z == z));
            freeSides.add(otherCubes.stream().noneMatch(cube -> cube.x == x && cube.y + 1 == y && cube.z == z));
            freeSides.add(otherCubes.stream().noneMatch(cube -> cube.x == x && cube.y == y && cube.z - 1 == z));
            freeSides.add(otherCubes.stream().noneMatch(cube -> cube.x == x && cube.y == y && cube.z + 1 == z));
        }

        void setFreeSidesDayb(Set<Cube> otherCubes) {
            freeSides.add(otherCubes.stream().anyMatch(cube -> cube.x - 1 == x && cube.y == y && cube.z == z));
            freeSides.add(otherCubes.stream().anyMatch(cube -> cube.x + 1 == x && cube.y == y && cube.z == z));
            freeSides.add(otherCubes.stream().anyMatch(cube -> cube.x == x && cube.y - 1 == y && cube.z == z));
            freeSides.add(otherCubes.stream().anyMatch(cube -> cube.x == x && cube.y + 1 == y && cube.z == z));
            freeSides.add(otherCubes.stream().anyMatch(cube -> cube.x == x && cube.y == y && cube.z - 1 == z));
            freeSides.add(otherCubes.stream().anyMatch(cube -> cube.x == x && cube.y == y && cube.z + 1 == z));



        }

        long countFreeSides() {
            return freeSides.stream().filter(side -> side).count();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Cube)) {
                return false;
            }
            Cube other = (Cube) o;
            return x == other.x && y == other.y && z == other.z;
        }

        @Override
        public int hashCode() {
            return x * 1000000 + y * 1000 + z;
        }
    }
}
