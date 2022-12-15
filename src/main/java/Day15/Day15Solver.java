package Day15;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15Solver {
    List<SensorBeacon> sensorBeaconList;

    public Day15Solver(List<String> input) {
        this.sensorBeaconList = input.stream()
                .map(line -> new SensorBeacon(line))
                .collect(Collectors.toList());
    }

    public int solveDay15a(int y) {
        Set<Integer> points = getXCoordinatesForY(y);
        return points.size();
    }

    private Set<Integer> getXCoordinatesForY(int y) {
        return sensorBeaconList.stream().parallel()
                .filter(sensorBeacon -> sensorBeacon.sensor.y + sensorBeacon.distance > y && sensorBeacon.sensor.y - sensorBeacon.distance < y)
                .map(sensorBeacon -> sensorBeacon.overlappingWithY(y))
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

//    public int solveDay15b(int min, int max) {
//        int minY = sensorBeaconList.stream()
//                .map(sensorBeacon -> sensorBeacon.sensor.y - sensorBeacon.distance)
//                .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
//        int maxY = sensorBeaconList.stream()
//                .map(sensorBeacon -> sensorBeacon.sensor.y + sensorBeacon.distance)
//                .reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);
//        min = Math.max(min, minY);
//        max = Math.min(max, maxY);
//        for (int y = min; y <= max; y++) {
//            System.out.println(y);
//            Set<Integer> xCoordinatesForY = getXCoordinatesForY(y, min, max);
//            if (xCoordinatesForY.size() < max) {
//                for (int x = min; x <= max; x++) {
//                    if (!xCoordinatesForY.contains(x)) {
//                        return x * 4000000 + y;
//                    }
//                }
//            }
//        }
//        return -1;
//    }

    public long solveDay15b(int min, int max) {
        for (int x = min; x <= max; x++) {
            for (int y = min; y <= max; y++) {
                boolean cont = false;
                for (SensorBeacon sensorBeacon: sensorBeaconList) {
                    int jumpY = sensorBeacon.isInSensorRange(new Point(x, y));
                    if (jumpY != -1) {
                        y = Math.max(y, jumpY);
                        cont = true;
                    }
                }
                if (!cont) {
                    return x * 4000000L + y;
                }
            }
        }
        return -1;
    }
    static class SensorBeacon {
        Point sensor;
        Point beacon;
        int distance;

        SensorBeacon(String input) {
            String[] inputParts = input
                    .replace("Sensor at x=", "")
                    .replace(": closest beacon is at x=", " ")
                    .replace(", y=", " ")
                    .split(" ");
            this.sensor = new Point(Integer.parseInt(inputParts[0]), Integer.parseInt(inputParts[1]));
            this.beacon = new Point(Integer.parseInt(inputParts[2]), Integer.parseInt(inputParts[3]));
            this.distance = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);
        }

        int isInSensorRange(Point point) {
            if (Math.abs(point.x - sensor.x) + Math.abs(point.y - sensor.y) > distance) {
                return -1;
            } else {
                return sensor.y + distance - Math.abs(point.x - sensor.x);
            }
        }

        List<Integer> overlappingWithY(int y) {
            int length;
            if (y > sensor.y) {
                length = (sensor.y + distance - y) * 2 + 1;
            } else if (y < sensor.y) {
                length = (-sensor.y + distance + y) * 2 + 1;
            } else {
                length = distance * 2 + 1;
            }
            return IntStream.range(Math.max(sensor.x - length / 2, Integer.MIN_VALUE), Math.min(sensor.x + length / 2, Integer.MAX_VALUE))
                    .boxed()
                    .collect(Collectors.toList());
        }
    }
}
