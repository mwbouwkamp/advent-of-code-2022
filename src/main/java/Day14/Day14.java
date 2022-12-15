package Day14;

import Day13.Day13Solver;
import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input14.txt");
        Day14Solver solver = new Day14Solver(lines);
        System.out.println(solver.SolveDay14a('a'));
        solver = new Day14Solver(lines);
        System.out.println(solver.SolveDay14a('b'));

//        List<String> test = new ArrayList<>();
//        test.add("498,4 -> 498,6 -> 496,6");
//        test.add("503,4 -> 502,4 -> 502,9 -> 494,9");
//        Day14Solver testSolver = new Day14Solver(test);
//        System.out.println(testSolver.SolveDay14a('a'));
//        testSolver = new Day14Solver(test);
//        System.out.println(testSolver.SolveDay14a('b'));
    }
}
