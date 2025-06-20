package aisw;

public class Player {
    private String name;
    private int totalScore;
    private int playCount;
    private int firstPlaceCount;
    private ScoreCard scoreCard = new ScoreCard();

    public Player(String name) {
        this.name = name;
        this.totalScore = 0;
        this.playCount = 0;
        this.firstPlaceCount = 0;
    }

    public Player(String name, int totalScore, int playCount, int firstPlaceCount) {
        this.name = name;
        this.totalScore = totalScore;
        this.playCount = playCount;
        this.firstPlaceCount = firstPlaceCount;
    }

    public String getName() { return name; }
    public ScoreCard getScoreCard() { return scoreCard; }
    public int getTotalScore() { return totalScore; }
    public int getPlayCount() { return playCount; }
    public int getFirstPlaceCount() { return firstPlaceCount; }
    public void addTotalScore(int score) { this.totalScore += score; }
    public void incPlayCount() { this.playCount++; }
    public void incFirstPlaceCount() { this.firstPlaceCount++; }
}
