package Day01;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        List<String> input = ReadPuzzleInput.readPuzzleLines("input01.txt");

        Day01Solver solver = new Day01Solver();
        System.out.println(solver.solveDay01a(input));
        System.out.println(solver.solveDay01b(input));
    }
}
