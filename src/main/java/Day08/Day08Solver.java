package Day08;

import java.util.ArrayList;
import java.util.List;

public class Day08Solver {
    List<String> lines;
    int width;
    int height;

    public Day08Solver(List<String> input) {
        this.lines = input;
        this.width = input.get(0).length();
        this.height = input.size();
    }

    public int solveDay08a() {
        int amount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isVisible(x, y)) {
                    amount++;
                }
            }
        }
        return amount;
    }

    public int solveDay08b() {
        int maxVisible = 1;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (numVisible(x, y) > maxVisible) {
                    maxVisible = numVisible(x, y);
                }
            }
        }
        return maxVisible;
    }

    private int numVisible(int x, int y) {
        int value = lines.get(y).charAt(x);
        int pointer = x - 1;
        int left = 0;
        while (pointer >= 0) {
            left++;
            if (lines.get(y).charAt(pointer) < value) {
                pointer--;
            } else {
                break;
            }
        }
        pointer = x + 1;
        int right = 0;
        while (pointer < width) {
            right++;
            if (lines.get(y).charAt(pointer) < value) {
                pointer++;
            } else {
                break;
            }
        }
        pointer = y - 1;
        int up = 0;
        while (pointer >= 0) {
            up++;
            if (lines.get(pointer).charAt(x) < value) {
                pointer--;
            } else {
                break;
            }
        }
        pointer = y + 1;
        int down = 0;
        while (pointer < height) {
            down++;
            if (lines.get(pointer).charAt(x) < value) {
                pointer++;
            } else {
                break;
            }
        }
        System.out.println(left + " " + right + " " + up + " " + down);
        return left * right * up * down;
    }
    private boolean isVisible(int x, int y) {
        boolean isVisible = true;
        int value = lines.get(y).charAt(x);
        int stringValue = value - 48;
        for (int i = 0; i < x; i++) {
            if (lines.get(y).charAt(i) >= value) {
                isVisible = false;
                break;
            }
        }
        if (isVisible) {
            return true;
        }
        isVisible = true;
        for (int i = x + 1; i < width; i++) {
            if (lines.get(y).charAt(i) >= value) {
                isVisible = false;
                break;
            }
        }
        if (isVisible) {
            return true;
        }
        isVisible = true;
        for (int i = 0; i < y; i++) {
            if (lines.get(i).charAt(x) >= value) {
                isVisible = false;
                break;
            }
        }
        if (isVisible) {
            return true;
        }
        isVisible = true;
        for (int i = y + 1; i < height; i++) {
            if (lines.get(i).charAt(x) >= value) {
                isVisible = false;
                break;
            }
        }
        return isVisible;
    }
}
