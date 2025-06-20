package aisw;

public class ScoreCard {
    private Integer[] scores = new Integer[ScoreCategory.values().length];

    public boolean isUsed(ScoreCategory cat) {
        return scores[cat.ordinal()] != null;
    }

    public void setScore(ScoreCategory cat, int score) {
        scores[cat.ordinal()] = score;
    }

    public int getScore(ScoreCategory cat) {
        Integer val = scores[cat.ordinal()];
        return val == null ? 0 : val;
    }

    public int getHomeworkSum() {
        int sum = 0;
        for (int i = 0; i <= ScoreCategory.SIXES.ordinal(); i++) {
            if (scores[i] != null) sum += scores[i];
        }
        return sum;
    }

    public int getTotalScore() {
        int sum = 0;
        for (Integer s : scores) {
            if (s != null) sum += s;
        }
        // 숙제 합이 63점 이상이면 35점 추가
        if (getHomeworkSum() >= 63) sum += 35;
        return sum;
    }

    public boolean isAllFilled() {
        for (ScoreCategory cat : ScoreCategory.values()) {
            if (scores[cat.ordinal()] == null) return false;
        }
        return true;
    }

    public void printScoreCard() {
        System.out.println("===== 점수판 =====");
        for (ScoreCategory cat : ScoreCategory.values()) {
            String scoreStr = isUsed(cat) ? String.valueOf(getScore(cat)) : "-";
            System.out.printf("%-18s : %s\n", cat.name(), scoreStr);
        }
        System.out.println("숙제합: " + getHomeworkSum());
        if (getHomeworkSum() >= 63) {
            System.out.println("보너스 35점 획득!");
        }
        System.out.println("총점: " + getTotalScore());
        System.out.println("=================");
    }
}