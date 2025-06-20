package aisw;

import java.util.*;

public class PlayerManager {
    private DatabaseManager db = DatabaseManager.getInstance();
    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public void setupPlayers(int numPlayers) {
        players.clear();
        for (int i = 1; i <= numPlayers; i++) {
            while (true) {
                String action = Utils.input(
                        i + "번 플레이어, [1] 로그인  [2] 회원가입 : ");
                if (action.trim().equals("1")) {
                    String nickname = Utils.input("닉네임 입력: ");
                    // 중복 로그인 체크
                    boolean alreadyLoggedIn = players.stream()
                            .anyMatch(p -> p.getName().equals(nickname));
                    if (alreadyLoggedIn) {
                        System.out.println("이미 로그인한 닉네임입니다. 다른 계정을 입력하세요.");
                        continue;
                    }
                    Player p = db.loadPlayer(nickname);
                    if (p != null) {
                        System.out.println("로그인 성공!");
                        players.add(p);
                        break;
                    } else {
                        System.out.println("존재하지 않는 닉네임입니다.");
                    }
                } else if (action.trim().equals("2")) {
                    while (true) {
                        String nickname = Utils.input("가입할 닉네임 입력: ");
                        // 중복 로그인 체크 (가입 후 바로 로그인하므로)
                        boolean alreadyLoggedIn = players.stream()
                                .anyMatch(p -> p.getName().equals(nickname));
                        if (alreadyLoggedIn) {
                            System.out.println("이미 로그인한 닉네임입니다. 다른 계정을 입력하세요.");
                            continue;
                        }
                        if (db.registerPlayer(nickname)) {
                            System.out.println("회원가입 성공! 로그인으로 전환합니다.");
                            Player p = db.loadPlayer(nickname);
                            players.add(p);
                            break;
                        } else {
                            System.out.println("이미 존재하는 닉네임입니다. 다시 입력하세요.");
                        }
                    }
                    break;
                } else {
                    System.out.println("1 또는 2번을 입력하세요.");
                }
            }
        }
    }
}