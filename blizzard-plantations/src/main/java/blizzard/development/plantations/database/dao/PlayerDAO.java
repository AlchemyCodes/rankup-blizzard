package blizzard.development.plantations.database.dao;

import blizzard.development.plantations.database.DatabaseConnection;
import blizzard.development.plantations.database.storage.PlayerData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerDAO {

    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String sql_plantations = "CREATE TABLE IF NOT EXISTS plantations_player (uuid varchar(36) primary key, nickname varchar(36), area int, blocks int, plantations int, plantation TINYINT(1))";

            statement.execute(sql_plantations);

        } catch (SQLException exception) {
            exception.printStackTrace();
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

    public PlayerData findPlayerData(String uuid) {
        String sqlpar = "SELECT * FROM plantations_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayerData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("area"),
                        resultSet.getInt("blocks"),
                        resultSet.getInt("plantations"),
                        resultSet.getBoolean("plantation")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public PlayerData findPlayerDataByNickname(String player) {
        String sql = "SELECT * FROM plantations_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayerData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("area"),
                        resultSet.getInt("blocks"),
                        resultSet.getInt("plantations"),
                        resultSet.getBoolean("plantation")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayerData playerData) throws SQLException {
        String sql_par = "INSERT INTO plantations_player (uuid, nickname, area, blocks, plantations, plantation) VALUES (?, ?, ?, ?, ?, ?)";

        executeUpdate(sql_par, (statement) -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setInt(3, playerData.getArea());
                statement.setInt(4, playerData.getBlocks());
                statement.setInt(5, playerData.getPlantations());
                statement.setBoolean(6, playerData.getIsInPlantation());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayerData playerData) throws SQLException {
        String sqlpar = "DELETE FROM plantations_player WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayerData playerData) throws SQLException {
        String sqlpar = "UPDATE plantations_player SET nickname = ?, area = ?, blocks = ?,plantations = ?, plantation = ? WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setInt(2, playerData.getArea());
                statement.setInt(3, playerData.getBlocks());
                statement.setInt(4, playerData.getPlantations());
                statement.setBoolean(5, playerData.getIsInPlantation());
                statement.setString(6, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<PlayerData> getAllPlayersData() throws SQLException {
        String sql = "SELECT * FROM plantations_player";
        List<PlayerData> playerDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                playerDataList.add(new PlayerData(
                    resultSet.getString("uuid"),
                    resultSet.getString("nickname"),
                    resultSet.getInt("area"),
                    resultSet.getInt("blocks"),
                    resultSet.getInt("plantations"),
                    resultSet.getBoolean("plantation")
                ));
            }
        }
        return playerDataList;
    }
}
