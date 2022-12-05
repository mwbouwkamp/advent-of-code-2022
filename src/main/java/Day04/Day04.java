package Day04;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day04 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input04.txt");
        Day04Solver solver = new Day04Solver();
        System.out.println(solver.solveDay04a(lines));
        System.out.println(solver.solveDay04b(lines));
    }
}
