package aisw;

import java.util.*;

public class ScoreCalculator {
    public static int calculateScore(ScoreCategory cat, int[] dice) {
        int[] counts = new int[7];
        for (int val : dice) counts[val]++;
        Arrays.sort(dice);

        switch (cat) {
            case ONES: case TWOS: case THREES: case FOURS: case FIVES: case SIXES:
                int num = cat.ordinal() + 1;
                return counts[num] * num;
            case FOUR_OF_A_KIND:
                for (int i = 1; i <= 6; i++) if (counts[i] >= 4) return Arrays.stream(dice).sum();
                return 0;
            case FULL_HOUSE:
                boolean has3 = false, has2 = false;
                for (int i = 1; i <= 6; i++) {
                    if (counts[i] == 3) has3 = true;
                    if (counts[i] == 2) has2 = true;
                }
                return (has3 && has2) ? 25 : 0;
            case SMALL_STRAIGHT:
                if ((counts[1] >= 1 && counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1) ||
                        (counts[2] >= 1 && counts[3] >= 1 && counts[4] >= 1 && counts[5] >= 1) ||
                        (counts[3] >= 1 && counts[4] >= 1 && counts[5] >= 1 && counts[6] >= 1)) {
                    return 30;
                }
                return 0;
            case LARGE_STRAIGHT:
                if ((counts[1] == 1 && counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1) ||
                        (counts[2] == 1 && counts[3] == 1 && counts[4] == 1 && counts[5] == 1 && counts[6] == 1)) {
                    return 40;
                }
                return 0;
            case YAHTZEE:
                for (int i = 1; i <= 6; i++) if (counts[i] == 5) return 50;
                return 0;
            case CHANCE:
                return Arrays.stream(dice).sum();
        }
        return 0;
    }
}
