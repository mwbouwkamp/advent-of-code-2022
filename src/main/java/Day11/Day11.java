package Day11;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input11.txt");
        Day11Solver solver = new Day11Solver(lines);
        System.out.println(solver.solveDay11a(20, true));

        lines = ReadPuzzleInput.readPuzzleLines("input11_test.txt");
        Day11Solver solverDay11b = new Day11Solver(lines);
        System.out.println(solverDay11b.solveDay11a(10000, false));
    }

}
