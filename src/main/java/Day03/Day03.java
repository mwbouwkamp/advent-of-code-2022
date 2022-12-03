package Day03;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day03 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input03.txt");
        Day03Solver solver = new Day03Solver(lines);
        System.out.println(solver.solveDay01());
        System.out.println(solver.solveDay02());

    }
}
