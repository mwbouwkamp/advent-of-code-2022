package Day04;

import java.util.List;

public class Day04Solver {
    public long solveDay04a(List<String> input) {
        return input.stream()
                .filter(line -> {
                    String[] assignments = line.split(",");
                    String[] assignmentOne = assignments[0].split("-");
                    String[] assignmentTwo = assignments[1].split("-");
                    return (Integer.parseInt(assignmentOne[0]) <= Integer.parseInt(assignmentTwo[0]) &&
                            Integer.parseInt(assignmentOne[1]) >= Integer.parseInt(assignmentTwo[1])) ||
                            (Integer.parseInt(assignmentTwo[0]) <= Integer.parseInt(assignmentOne[0]) &&
                                    Integer.parseInt(assignmentTwo[1]) >= Integer.parseInt(assignmentOne[1]));
                })
                .count();
    }

    public long solveDay04b(List<String> input) {
        return input.stream()
                .filter(line -> {
                    String[] assignments = line.split(",");
                    String[] assignmentOne = assignments[0].split("-");
                    String[] assignmentTwo = assignments[1].split("-");
                    return Integer.parseInt(assignmentOne[0]) <= Integer.parseInt(assignmentTwo[1]) &&
                            Integer.parseInt(assignmentOne[1]) >= Integer.parseInt(assignmentTwo[0]);
                })
                .count();
    }

}
