package blizzard.development.vips.database.dao;

import blizzard.development.vips.database.DatabaseConnection;
import blizzard.development.vips.database.storage.VipData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VipDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS vip_users ("
                    + "vipId VARCHAR(36) PRIMARY KEY, "
                    + "uuid VARCHAR(36), "
                    + "nickname VARCHAR(36), "
                    + "vipActivationDate VARCHAR(36), "
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

    public VipData findVipData(String uuid) {
        String sql = "SELECT * FROM vip_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new VipData(
                            resultSet.getString("vipId"),
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getString("vipActivationDate"),
                            resultSet.getString("vipName"),
                            resultSet.getLong("vipDuration"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createVipData(VipData vipData) throws SQLException {
        String sql = "INSERT INTO vip_users (vipId, uuid, nickname, vipActivationDate, vipName, vipDuration) VALUES (?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, vipData.getVipId());
                statement.setString(2, vipData.getUuid());
                statement.setString(3, vipData.getNickname());
                statement.setString(4, vipData.getVipActivationDate());
                statement.setString(5, vipData.getVipName());
                statement.setLong(6, vipData.getVipDuration());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteVipData(VipData vipData) throws SQLException {
        String sql = "DELETE FROM vip_users WHERE vipId = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, vipData.getVipId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteVipVip(VipData vipData) throws SQLException {
        String sql = "DELETE FROM vip_users WHERE uuid = ? AND vipName = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, vipData.getUuid());
                statement.setString(2, vipData.getVipName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateVipData(VipData vipData) throws SQLException {
        String sql = "UPDATE vip_users SET uuid = ?, nickname = ?, vipActivationDate = ?, vipName = ?, vipDuration = ? WHERE vipId = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, vipData.getUuid());
                statement.setString(2, vipData.getNickname());
                statement.setString(3, vipData.getVipActivationDate());
                statement.setString(4, vipData.getVipName());
                statement.setLong(5, vipData.getVipDuration());
                statement.setString(6, vipData.getVipId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<VipData> getAllVipData() throws SQLException {
        String sql = "SELECT * FROM vip_users";
        List<VipData> vipDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                vipDataList.add(new VipData(
                        resultSet.getString("vipId"),
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("vipActivationDate"),
                        resultSet.getString("vipName"),
                        resultSet.getLong("vipDuration")
                ));
            }
        }
        return vipDataList;
    }

}
