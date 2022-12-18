package Day17;

import Utils.ReadPuzzleInput;

import java.io.IOException;

public class Day17 {
    public static void main(String[] args) throws IOException {
        String input = ReadPuzzleInput.readPuzzleSingleLine("input17.txt");
        Day17Solver solver = new Day17Solver(input.toCharArray());
//        System.out.println(solver.solveDay17(2022, 'a'));
        System.out.println(solver.solveDay17(1725 * 2, 'b'));

    }
}// Low 891594203941  891594203948
// No 1541449275370   1541449275372  1541449275369  1541449275374  1541449275368  1540571428557
