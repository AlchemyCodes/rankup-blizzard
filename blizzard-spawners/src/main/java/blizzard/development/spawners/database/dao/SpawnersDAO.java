package blizzard.development.spawners.database.dao;

import blizzard.development.spawners.database.DatabaseConnection;
import blizzard.development.spawners.database.storage.SpawnersData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpawnersDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS spawners (" +
                    "id VARCHAR(36) PRIMARY KEY, " +
                    "type VARCHAR(50), " +
                    "amount DOUBLE, " +
                    "mob_amount DOUBLE, " +
                    "drops DOUBLE, " +
                    "location VARCHAR(255), " +
                    "mob_location VARCHAR(255), " +
                    "nickname VARCHAR(36), " +
                    "state VARCHAR(50), " +
                    "plotId VARCHAR(36), " +
                    "speed_level INTEGER, " +
                    "lucky_level INTEGER, " +
                    "experience_level INTEGER)";
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
                            resultSet.getString("mob_location"),
                            resultSet.getString("nickname"),
                            resultSet.getString("state"),
                            resultSet.getString("plotId"),
                            resultSet.getDouble("amount"),
                            resultSet.getDouble("mob_amount"),
                            resultSet.getDouble("drops"),
                            resultSet.getInt("speed_level"),
                            resultSet.getInt("lucky_level"),
                            resultSet.getInt("experience_level")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SpawnersData> findSpawnerDataByPlotId(String plotId) {
        String sql = "SELECT * FROM spawners WHERE plotId = ?";
        List<SpawnersData> spawnersData = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, plotId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    spawnersData.add(new SpawnersData(
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("location"),
                            resultSet.getString("mob_location"),
                            resultSet.getString("nickname"),
                            resultSet.getString("state"),
                            resultSet.getString("plotId"),
                            resultSet.getDouble("amount"),
                            resultSet.getDouble("mob_amount"),
                            resultSet.getDouble("drops"),
                            resultSet.getInt("speed_level"),
                            resultSet.getInt("lucky_level"),
                            resultSet.getInt("experience_level")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spawnersData;
    }

    public void createSpawnerData(SpawnersData spawnerData) throws SQLException {
        String sql = "INSERT INTO spawners (id, type, location, mob_location, nickname, state, plotId, amount, mob_amount, drops, speed_level, lucky_level, experience_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, spawnerData.getId());
                statement.setString(2, spawnerData.getType());
                statement.setString(3, spawnerData.getLocation());
                statement.setString(4, spawnerData.getMobLocation());
                statement.setString(5, spawnerData.getNickname());
                statement.setString(6, spawnerData.getState());
                statement.setString(7, spawnerData.getPlotId());
                statement.setDouble(8, spawnerData.getAmount());
                statement.setDouble(9, spawnerData.getMobAmount());
                statement.setDouble(10, spawnerData.getDrops());
                statement.setInt(11, spawnerData.getSpeedLevel());
                statement.setInt(12, spawnerData.getLuckyLevel());
                statement.setInt(13, spawnerData.getExperienceLevel());
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
        String sql = "UPDATE spawners SET type = ?, location = ?, mob_location = ?, nickname = ?, state = ?, plotId = ?, amount = ?, mob_amount = ?, drops = ?, speed_level = ?, lucky_level = ?, experience_level = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, spawnerData.getType());
                statement.setString(2, spawnerData.getLocation());
                statement.setString(3, spawnerData.getMobLocation());
                statement.setString(4, spawnerData.getNickname());
                statement.setString(5, spawnerData.getState());
                statement.setString(6, spawnerData.getPlotId());
                statement.setDouble(7, spawnerData.getAmount());
                statement.setDouble(8, spawnerData.getMobAmount());
                statement.setDouble(9, spawnerData.getDrops());
                statement.setInt(10, spawnerData.getSpeedLevel());
                statement.setInt(11, spawnerData.getLuckyLevel());
                statement.setInt(12, spawnerData.getExperienceLevel());
                statement.setString(13, spawnerData.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<SpawnersData> getAllSpawnersData() throws SQLException {
        String sql = "SELECT * FROM spawners";
        List<SpawnersData> spawnersDataList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                spawnersDataList.add(new SpawnersData(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("location"),
                        resultSet.getString("mob_location"),
                        resultSet.getString("nickname"),
                        resultSet.getString("state"),
                        resultSet.getString("plotId"),
                        resultSet.getDouble("amount"),
                        resultSet.getDouble("mob_amount"),
                        resultSet.getDouble("drops"),
                        resultSet.getInt("speed_level"),
                        resultSet.getInt("lucky_level"),
                        resultSet.getInt("experience_level")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spawnersDataList;
    }
}
