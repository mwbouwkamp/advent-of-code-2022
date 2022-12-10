package Day10;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day10 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input10.txt");
        Day10Solver solver = new Day10Solver(lines);
        System.out.println(solver.solveDay10a());
        solver.solveDay10b();
    }
}
