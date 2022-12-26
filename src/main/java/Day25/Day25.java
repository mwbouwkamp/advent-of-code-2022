package Day25;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day25 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input25.txt");
        Day25Solver solver = new Day25Solver(lines);
        System.out.println(solver.solveDay25a());

        System.out.println(Integer.toString(4890, 5));

    }
}
