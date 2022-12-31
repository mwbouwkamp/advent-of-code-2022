package Day21;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day21 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input21.txt");
        Day21aSolver solverA = new Day21aSolver(lines);
        System.out.println(solverA.solveDay21a());

        Day21bSolver solverB = new Day21bSolver(lines);
        System.out.println(solverB.solveDay21b());
        // high: 21718827469549
    }
}
