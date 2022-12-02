package Day02;

import Utils.ReadPuzzleInput;

import java.io.IOException;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        List<String> lines = ReadPuzzleInput.readPuzzleLines("input02.txt");
        Day02Solver day02Solver = new Day02Solver(lines);
        System.out.println(day02Solver.solveDay01());
        System.out.println(day02Solver.solveDay02());

    }
}
