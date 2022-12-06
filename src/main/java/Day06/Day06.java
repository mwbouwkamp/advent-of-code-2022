package Day06;

import Utils.ReadPuzzleInput;

import java.io.IOException;

public class Day06 {
    public static void main(String[] args) throws IOException {
        String input = ReadPuzzleInput.readPuzzleSingleLine("input06.txt");
        Day06Solver solver = new Day06Solver();
        System.out.println(solver.solveDay06a(input, 4) + 4);
        System.out.println(solver.solveDay06a(input, 14) + 14);
    }
}
