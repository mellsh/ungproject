package aisw;

public class Main {
    public static void main(String[] args) {
        YahtzeeGame game = new YahtzeeGame();
        while (true) {
            System.out.println("===================================");
            System.out.println("1. 게임 시작");
            System.out.println("2. 유저 랭킹 보기");
            System.out.println("0. 종료");
            System.out.println("===================================");
            int sel = Utils.inputInt("메뉴를 선택하세요: ");
            if (sel == 1) {
                game.run(); // 게임 끝나도 메뉴로 돌아옴
            } else if (sel == 2) {
                DatabaseManager.getInstance().printRanking();
            } else if (sel == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}