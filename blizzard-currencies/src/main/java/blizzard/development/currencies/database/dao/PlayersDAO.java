package blizzard.development.currencies.database.dao;

import blizzard.development.currencies.database.DatabaseConnection;
import blizzard.development.currencies.database.storage.PlayersData;

import java.sql.*;
import java.util.function.Consumer;

public class PlayersDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS currencies_users (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "nickname VARCHAR(36), " +
                    "souls DOUBLE" +
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
        String sql = "SELECT * FROM currencies_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getDouble("souls")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sql = "INSERT INTO currencies_users (uuid, nickname, souls) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setDouble(3, playerData.getSouls());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "DELETE FROM currencies_users WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sql = "UPDATE currencies_users SET nickname = ?, souls = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setDouble(2, playerData.getSouls());
                statement.setString(3, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
