package Day15;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day15 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input15.txt");
        Day15Solver solver = new Day15Solver(lines);
        System.out.println(solver.solveDay15a(2000000));
        System.out.println(solver.solveDay15b(0, 4000000));

//        List<String> testLines = ReadPuzzleInput.readPuzzleLines("input15_test.txt");
//        Day15Solver testSolver = new Day15Solver(testLines);
//        System.out.println(testSolver.solveDay15a(10));
//        System.out.println(testSolver.solveDay15b(0, 20));
    }  // Low: 1075151795
}
