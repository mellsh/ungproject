package aisw;

import java.util.*;

public class YahtzeeGame {
    private PlayerManager playerManager = new PlayerManager();
    private DatabaseManager db = DatabaseManager.getInstance();
    private DiceSet diceSet = new DiceSet();

    public void run() {
        System.out.println("야추다이스 게임에 오신 것을 환영합니다!");
        int numPlayers;
        while (true) {
            numPlayers = Utils.inputInt("플레이 인원(2~4) 입력: ");
            if (numPlayers >= 2 && numPlayers <= 4) break;
            System.out.println("인원은 2~4명이어야 합니다.");
        }
        playerManager.setupPlayers(numPlayers);

        List<Player> players = playerManager.getPlayers();
        int rounds = ScoreCategory.values().length;
        while (!players.stream().allMatch(p -> p.getScoreCard().isAllFilled())) {
            for (Player player : players) {
                if (player.getScoreCard().isAllFilled()) continue;
                System.out.println("\n" + player.getName() + "님의 차례입니다.");
                playerTurn(player);
            }
        }
        finishGame(players);
    }

    private void playerTurn(Player player) {
        boolean[] toRoll = { true, true, true, true, true };
        int tries = 0;
        diceSet.rollAll();
        while (tries < 2) {
            diceSet.printDices();
            String reroll = Utils.input("다시 굴릴 주사위 번호 입력 (예: 1 3, 건너뛰기: 엔터): ");
            if (reroll.trim().isEmpty()) break;
            String[] parts = reroll.trim().split("\\s+");
            toRoll = new boolean[5];
            for (String part : parts) {
                try {
                    int idx = Integer.parseInt(part) - 1;
                    if (idx >= 0 && idx < 5) toRoll[idx] = true;
                } catch (NumberFormatException ignore) {}
            }
            diceSet.rollSelected(toRoll);
            tries++;
        }
        diceSet.printDices();
        player.getScoreCard().printScoreCard();
        int catIdx;
        ScoreCategory cat;
        while (true) {
            catIdx = Utils.inputInt(
                    "점수를 기록할 카테고리 번호를 입력하세요:\n" + ScoreCategory.listCategories() + "> "
            );
            if (catIdx >= 0 && catIdx < ScoreCategory.values().length) {
                cat = ScoreCategory.values()[catIdx];
                if (!player.getScoreCard().isUsed(cat)) break;
                System.out.println("이미 기록된 항목입니다. 다시 선택하세요.");
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        }
        int score = ScoreCalculator.calculateScore(cat, diceSet.getValues());
        player.getScoreCard().setScore(cat, score);
        System.out.println(cat.name() + "에 " + score + "점이 기록되었습니다.");
    }

    private void finishGame(List<Player> players) {
        System.out.println("\n===== 게임 종료 =====");
        int maxScore = Integer.MIN_VALUE;
        List<Player> winners = new ArrayList<>();
        for (Player p : players) {
            int total = p.getScoreCard().getTotalScore();
            System.out.printf("%s: %d점\n", p.getName(), total);
            if (total > maxScore) {
                winners.clear();
                winners.add(p);
                maxScore = total;
            } else if (total == maxScore) {
                winners.add(p);
            }
            // DB 누적 기록
            p.addTotalScore(total);
            p.incPlayCount();
            db.updatePlayer(p);
        }
        for (Player winner : winners) {
            winner.incFirstPlaceCount();
            System.out.println("우승자: " + winner.getName());
            db.updatePlayer(winner);
        }
        db.printRanking();
    }
}