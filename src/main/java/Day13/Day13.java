package Day13;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day13 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input13.txt");
        Day13Solver solver = new Day13Solver(lines);
//        System.out.println(solver.solveDay13a());
        System.out.println(solver.solveDay13b());


        // Had to manually correct for this comparison (which gave + instead of -)
        // [[[5,9,[5,5,1,6,5]]]]  -  [[5],[6,5]]
        // [[5,9,[5,5,1,6,5]]]  -  [5]
        // [5,9,[5,5,1,6,5]]  -  5
        // 5  -  5



    }
}
