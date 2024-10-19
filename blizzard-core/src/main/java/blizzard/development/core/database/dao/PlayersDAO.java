package blizzard.development.core.database.dao;

import blizzard.development.core.database.DatabaseConnection;
import blizzard.development.core.database.storage.PlayersData;
import blizzard.development.core.clothing.ClothingType;

import java.sql.*;
import java.util.function.Consumer;

public class PlayersDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS player_core (" +
                    "uuid varchar(36) primary key, " +
                    "nickname varchar(36), " +
                    "temperature double, " +
                    "clothing varchar(36))";
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
        String sqlpar = "SELECT * FROM player_core WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getDouble("temperature"),
                            ClothingType.valueOf(resultSet.getString("clothing")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public PlayersData findPlayerDataByName(String player) {
        String sql = "SELECT * FROM player_core WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getDouble("temperature"),
                            ClothingType.valueOf(resultSet.getString("clothing")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "INSERT INTO player_core (uuid, nickname, temperature, clothing) VALUES (?, ?, ?, ?)";
        executeUpdate(sqlpar, (statement) -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setDouble(3, playerData.getTemperature());
                statement.setString(4, playerData.getClothingType().name());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "DELETE FROM player_core WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "UPDATE player_core SET nickname = ?, temperature = ?, clothing = ? WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setDouble(2, playerData.getTemperature());
                statement.setString(3, playerData.getClothingType().name());
                statement.setString(4, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
