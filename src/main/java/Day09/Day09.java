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
    }
}
