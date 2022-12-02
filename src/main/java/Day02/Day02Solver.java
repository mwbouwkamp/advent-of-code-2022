package Day02;

import java.util.List;
import java.util.stream.Collectors;

public class Day02Solver {
    List<Game> games;

    public Day02Solver(List<String> input) {
        this.games = input.stream()
                .map(line -> new Game(line))
                .collect(Collectors.toList());
    }

    public int solveDay01() {
        return games.stream()
                .map(game -> game.you + game.score())
                .reduce(Integer::sum)
                .orElse(-1);
    }

    public int solveDay02() {
        return games.stream()
                .map(game -> {
                    game.adoptYourValues();
                    return game.you + game.score();
                })
                .reduce(Integer::sum)
                .orElse(-1);
    }
    static class Game {
        int them;
        int you;

        Game(String input) {
            char[] inputChars = input.toCharArray();
            this.them = (int) inputChars[0] - 64;
            this.you = (int) inputChars[2] - 87;
        }

        int score() {
            int difference = you - them;
            switch (difference) {
                case 1:
                case -2:
                    return 6;
                case 0:
                    return 3;
                default:
                    return 0;
            }
        }

        void adoptYourValues() {
            switch (you) {
                case 1:
                    if (them == 2 || them == 3) {
                        you = them - 1;
                    } else {
                        you = them + 2;
                    }
                    break;
                case 2:
                    you = them;
                    break;
                default:
                    if (them == 1 || them == 2) {
                        you = them + 1;
                    } else {
                        you = them - 2;
                    }
                    break;
            }
        }
    }
}
