package aisw;

import java.sql.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:yahtzee.db");
            String sql = "CREATE TABLE IF NOT EXISTS player (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nickname TEXT UNIQUE, " +
                    "total_score INTEGER DEFAULT 0, " +
                    "play_count INTEGER DEFAULT 0, " +
                    "first_place_count INTEGER DEFAULT 0)";
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    public boolean registerPlayer(String nickname) {
        try {
            String sql = "INSERT INTO player (nickname) VALUES (?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nickname);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Player loadPlayer(String nickname) {
        try {
            String sql = "SELECT * FROM player WHERE nickname = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nickname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Player p = new Player(
                        rs.getString("nickname"),
                        rs.getInt("total_score"),
                        rs.getInt("play_count"),
                        rs.getInt("first_place_count")
                );
                rs.close();
                pstmt.close();
                return p;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePlayer(Player player) {
        try {
            String sql = "UPDATE player SET total_score = ?, play_count = ?, first_place_count = ? WHERE nickname = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, player.getTotalScore());
            pstmt.setInt(2, player.getPlayCount());
            pstmt.setInt(3, player.getFirstPlaceCount());
            pstmt.setString(4, player.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printRanking() {
        try {
            String sql = "SELECT nickname, total_score, play_count, first_place_count " +
                    "FROM player ORDER BY total_score DESC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("==== 유저 랭킹 (누적 점수 순) ====");
            int rank = 1;
            System.out.printf("%-4s %-12s %-8s %-6s %-8s\n", "순위", "닉네임", "누적점수", "플레이", "1등횟수");
            while (rs.next()) {
                System.out.printf("%-4d %-12s %-8d %-6d %-8d\n",
                        rank++,
                        rs.getString("nickname"),
                        rs.getInt("total_score"),
                        rs.getInt("play_count"),
                        rs.getInt("first_place_count"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
