package blizzard.development.spawners.database.dao;

import blizzard.development.spawners.database.DatabaseConnection;
import blizzard.development.spawners.database.storage.SpawnersData;

import java.sql.*;
import java.util.function.Consumer;

public class SpawnersDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS spawners (" +
                    "id VARCHAR(36) PRIMARY KEY, " +
                    "type VARCHAR(50), " +
                    "location VARCHAR(255), " +
                    "nickname VARCHAR(36))";
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
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;
        }
    }

    public SpawnersData findSpawnerData(String id) {
        String sql = "SELECT * FROM spawners WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new SpawnersData(
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("location"),
                            resultSet.getString("nickname")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find spawner data: " + e);
        }
        return null;
    }

    public void createSpawnerData(SpawnersData spawnerData) throws SQLException {
        String sql = "INSERT INTO spawners (id, type, location, nickname) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, spawnerData.getId());
                statement.setString(2, spawnerData.getType());
                statement.setString(3, spawnerData.getLocation());
                statement.setString(4, spawnerData.getNickname());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteSpawnerData(String id) throws SQLException {
        String sql = "DELETE FROM spawners WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateSpawnerData(SpawnersData spawnerData) throws SQLException {
        String sql = "UPDATE spawners SET type = ?, location = ?, nickname = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, spawnerData.getType());
                statement.setString(2, spawnerData.getLocation());
                statement.setString(3, spawnerData.getNickname());
                statement.setString(4, spawnerData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
