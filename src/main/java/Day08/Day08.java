package Day08;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day08 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input08.txt");
        Day08Solver solver = new Day08Solver(lines);
        System.out.println(solver.solveDay08a());
        System.out.println(solver.solveDay08b());
    }
}
