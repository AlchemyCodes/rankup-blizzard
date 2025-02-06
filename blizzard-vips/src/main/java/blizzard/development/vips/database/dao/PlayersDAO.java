package blizzard.development.vips.database.dao;

import blizzard.development.vips.database.DatabaseConnection;
import blizzard.development.vips.database.storage.PlayersData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayersDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS vip_users ("
                    + "uuid VARCHAR(36), "
                    + "id INT, "
                    + "nickname VARCHAR(36), "
                    + "vipActivationDate VARCHAR(36), "
                    + "vipId VARCHAR(36), "
                    + "vipName VARCHAR(50), "
                    + "vipDuration BIGINT)";
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
        String sql = "SELECT * FROM vip_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getInt("id"),
                            resultSet.getString("nickname"),
                            resultSet.getString("vipActivationDate"),
                            resultSet.getString("vipId"),
                            resultSet.getString("vipName"),
                            resultSet.getLong("vipDuration"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sql = "INSERT INTO vip_users (uuid, id, nickname, vipActivationDate, vipId, vipName, vipDuration) VALUES (?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setInt(2, getNextVipIndex(playerData.getNickname()));
                statement.setString(3, playerData.getNickname());
                statement.setString(4, playerData.getVipActivationDate());
                statement.setString(5, playerData.getVipId());
                statement.setString(6, playerData.getVipName());
                statement.setLong(7, playerData.getVipDuration());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sql = "DELETE FROM vip_users WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getVipId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerVip(PlayersData playerData) throws SQLException {
        String sql = "DELETE FROM vip_users WHERE uuid = ? AND vipName = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getVipName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sql = "UPDATE vip_users SET uuid = ?, id = ?, nickname = ?, vipActivationDate = ?, vipName = ?, vipDuration = ? WHERE vipId = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setInt(2, playerData.getId());
                statement.setString(3, playerData.getNickname());
                statement.setString(4, playerData.getVipActivationDate());
                statement.setString(5, playerData.getVipName());
                statement.setLong(6, playerData.getVipDuration());
                statement.setString(7, playerData.getVipId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public int getNextVipIndex(String nickname) throws SQLException {
        String sql = "SELECT MAX(id) FROM vip_users WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nickname);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) + 1;
                }
            }
        }
        return 1;
    }

}
