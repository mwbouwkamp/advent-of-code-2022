package Day12;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day12 {
    public static void main(String[] args) throws IOException {
        List<String> input = ReadPuzzleInput.readPuzzleLines("input12.txt");
        Day12Solver solver = new Day12Solver(input);
        System.out.println(solver.solveDay12a());
        System.out.println(solver.solveDay12b());

    }
}
