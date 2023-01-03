package Day22;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day22 {
    public static void main(String[] args) throws IOException {
        List<String> input = ReadPuzzleInput.readPuzzleLines("input22.txt");
        Day22aSolver solverA = new Day22aSolver(input);
//        System.out.println(solverA.solveDay22a());

        Day22bSolver solverB = new Day22bSolver(input, 50);
        System.out.println(solverB.solveDay22b());  // low: 145250     not: 162151, 161151
    }
}
