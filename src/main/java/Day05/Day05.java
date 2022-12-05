package Day05;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day05 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input05.txt");
        Day05Solver solverDay05a = new Day05Solver(lines);
        System.out.println(solverDay05a.solveDay05a());

        List<String> linesb = ReadPuzzleInput.readPuzzleLines("input05.txt");
        Day05Solver solverDay05b = new Day05Solver(linesb);
        System.out.println(solverDay05b.solveDay05b());
    }
}
