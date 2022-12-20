package Day20;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day20 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input20.txt");
        Day20Solver solver = new Day20Solver(lines);
        System.out.println(solver.solveDay20(1, 1));
        System.out.println(solver.solveDay20(811589153, 10));

    }  // Low
}
