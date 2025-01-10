package blizzard.development.mail.database.dao;

import blizzard.development.mail.database.DatabaseConnection;
import blizzard.development.mail.database.storage.PlayerData;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerDao {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS mail_users (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "nickname VARCHAR(36), " +
                    "items TEXT)";
            stat.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdate(String sql, Consumer<PreparedStatement> setter) throws SQLException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            setter.accept(statement);
            statement.executeUpdate();
        }
    }

    public void createPlayerData(PlayerData playerData) throws SQLException {
        String sql = "INSERT INTO mail_users (uuid, nickname, items) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setString(3, new Gson().toJson(playerData.getItems()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(String uuid) throws SQLException {
        String sql = "DELETE FROM mail_users WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, uuid);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayerData playerData) throws SQLException {
        String sql = "UPDATE mail_users SET nickname = ?, items = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setString(2, new Gson().toJson(playerData.getItems()));
                statement.setString(3, playerData.getUuid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public PlayerData findPlayerData(String uuid) {
        String sql = "SELECT * FROM mail_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Gson gson = new Gson();
                    String itemsJson = resultSet.getString("items");
                    List<String> items = itemsJson != null
                            ? gson.fromJson(itemsJson, new TypeToken<List<String>>() {}.getType())
                            : new ArrayList<>();

                    return new PlayerData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            items
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e.getMessage());
        }
        return null;
    }

    public List<PlayerData> getAllPlayerData() throws SQLException {
        String sql = "SELECT * FROM mail_users";
        List<PlayerData> playerDataList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Gson gson = new Gson();
                String itemsJson = resultSet.getString("items");
                List<String> items = itemsJson != null
                        ? gson.fromJson(itemsJson, new TypeToken<List<String>>() {}.getType())
                        : new ArrayList<>();
                playerDataList.add(new PlayerData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        items
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerDataList;
    }
}
