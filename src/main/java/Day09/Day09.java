package Day09;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input09.txt");
        Day09Solver solver = new Day09Solver(lines, 2);
        System.out.println(solver.solveDay09());
        Day09Solver solverDay09b = new Day09Solver(lines, 10);
        System.out.println(solverDay09b.solveDay09());

        List<String> test = new ArrayList<>();
        test.add("R 5");
        test.add("U 8");
        test.add("L 8");
        test.add("D 3");
        test.add("R 17");
        test.add("D 10");
        test.add("L 25");
        test.add("U 20");
        Day09Solver solverTest = new Day09Solver(test, 10);
        System.out.println(solverTest.solveDay09());
    }
}
