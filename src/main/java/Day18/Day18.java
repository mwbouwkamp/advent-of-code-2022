package Day18;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input18.txt");

//        lines = new ArrayList<>();
//        lines.add("1,1,1");
//        lines.add("2,1,1");

        Day18Solver solver = new Day18Solver(lines);
        System.out.println(solver.solveDay18a());

        solver = new Day18Solver(lines);
        System.out.println(solver.solveDay18b());
    }

}
