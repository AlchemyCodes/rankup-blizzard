package blizzard.development.monsters.database.dao;

import blizzard.development.monsters.database.DatabaseConnection;
import blizzard.development.monsters.database.storage.PlayersData;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayersDAO {
    private final Gson gson = new Gson();

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS monsters_users (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "nickname VARCHAR(36), " +
                    "monsters_limit INTEGER, " +
                    "rewards TEXT" +
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
        String sql = "SELECT * FROM monsters_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String rewardsJson = resultSet.getString("rewards");

                    List<String> rewards = rewardsJson != null
                            ? gson.fromJson(rewardsJson, new TypeToken<List<String>>() {}.getType())
                            : new ArrayList<>();

                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getInt("monsters_limit"),
                            rewards
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sql = "INSERT INTO monsters_users (uuid, nickname, monsters_limit, rewards) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setInt(3, playerData.getMonstersLimit());
                statement.setString(4, gson.toJson(playerData.getRewards()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "DELETE FROM monsters_users WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sql = "UPDATE monsters_users SET nickname = ?, monsters_limit = ?, rewards = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setInt(2, playerData.getMonstersLimit());
                statement.setString(3, gson.toJson(playerData.getRewards()));
                statement.setString(4, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<PlayersData> getAllPlayersData() throws SQLException {
        String sql = "SELECT * FROM monsters_users";
        List<PlayersData> playersDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String rewardsJson = resultSet.getString("rewards");

                List<String> rewards = rewardsJson != null
                        ? gson.fromJson(rewardsJson, new TypeToken<List<String>>() {}.getType())
                        : new ArrayList<>();

                playersDataList.add(new PlayersData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("monsters_limit"),
                        rewards
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve players data: " + e.getMessage());
        }

        return playersDataList;
    }
}
