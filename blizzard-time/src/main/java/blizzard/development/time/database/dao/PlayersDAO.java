package blizzard.development.time.database.dao;

import blizzard.development.time.database.DatabaseConnection;
import blizzard.development.time.database.storage.PlayersData;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayersDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS time_users (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "nickname VARCHAR(36), " +
                    "play_time BIGINT, " +
                    "completed_missions TEXT, " +
                    "notified_missions TEXT" +
                    ")";

            stat.execute(sql_player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdate(String sql, Consumer<PreparedStatement> setter) throws SQLException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            setter.accept(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;
        }
    }

    public PlayersData findPlayerData(String uuid) {
        String sql = "SELECT * FROM time_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Gson gson = new Gson();

                    String completedMissionsJson = resultSet.getString("completed_missions");
                    List<String> completedMissions = completedMissionsJson != null
                            ? gson.fromJson(completedMissionsJson, new TypeToken<List<String>>(){}.getType())
                            : new ArrayList<>();

                    String notifiedMissionsJson = resultSet.getString("notified_missions");
                    List<String> notifiedMissions = notifiedMissionsJson != null
                            ? gson.fromJson(notifiedMissionsJson, new TypeToken<List<String>>(){}.getType())
                            : new ArrayList<>();

                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getLong("play_time"),
                            completedMissions,
                            notifiedMissions
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "INSERT INTO time_users (uuid, nickname, play_time, completed_missions, notified_missions) VALUES (?, ?, ?, ?, ?)";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setLong(3, playerData.getPlayTime());
                statement.setString(4, new Gson().toJson(playerData.getCompletedMissions()));
                statement.setString(5, new Gson().toJson(playerData.getNotifiedMissions()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "DELETE FROM time_users WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sql = "UPDATE time_users SET nickname = ?, play_time = ?, completed_missions = ?, notified_missions = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setLong(2, playerData.getPlayTime());
                statement.setString(3, new Gson().toJson(playerData.getCompletedMissions()));
                statement.setString(4, new Gson().toJson(playerData.getNotifiedMissions()));
                statement.setString(5, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<PlayersData> getAllPlayers() {
        List<PlayersData> players = new ArrayList<>();
        String sql = "SELECT * FROM time_users";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            Gson gson = new Gson();

            while (resultSet.next()) {
                String completedMissionsJson = resultSet.getString("completed_missions");
                List<String> completedMissions = completedMissionsJson != null
                        ? gson.fromJson(completedMissionsJson, new TypeToken<List<String>>(){}.getType())
                        : new ArrayList<>();

                String notifiedMissionsJson = resultSet.getString("notified_missions");
                List<String> notifiedMissions = notifiedMissionsJson != null
                        ? gson.fromJson(notifiedMissionsJson, new TypeToken<List<String>>(){}.getType())
                        : new ArrayList<>();

                PlayersData player = new PlayersData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getLong("play_time"),
                        completedMissions,
                        notifiedMissions
                );
                players.add(player);
            }
        } catch (SQLException e) {
            System.out.println("Failed to load all players: " + e);
        }
        return players;
    }
}
