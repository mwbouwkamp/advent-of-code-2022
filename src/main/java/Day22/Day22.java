package Day22;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day22 {
    public static void main(String[] args) throws IOException {
        List<String> input = ReadPuzzleInput.readPuzzleLines("input22.txt");
        Day22Solver solver = new Day22Solver(input);
        System.out.println(solver.solveDay22a());
    } // High 142118
}
