package aisw;

public enum ScoreCategory {
    ONES, TWOS, THREES, FOURS, FIVES, SIXES,
    FOUR_OF_A_KIND, FULL_HOUSE,
    SMALL_STRAIGHT, LARGE_STRAIGHT, YAHTZEE, CHANCE;

    public static String listCategories() {
        StringBuilder sb = new StringBuilder();
        for (ScoreCategory cat : ScoreCategory.values()) {
            sb.append(cat.ordinal()).append(": ").append(cat.name()).append("\n");
        }
        return sb.toString();
    }
}
