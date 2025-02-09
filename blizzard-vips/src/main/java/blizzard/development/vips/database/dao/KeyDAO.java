package blizzard.development.vips.database.dao;

import blizzard.development.vips.database.DatabaseConnection;
import blizzard.development.vips.database.storage.KeyData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class KeyDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS vip_keys ("
                    + "vipKey VARCHAR(36) PRIMARY KEY, "
                    + "vipName VARCHAR(36), "
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

    public void createKeyData(KeyData keyData) throws SQLException {
        String sql = "INSERT INTO vip_keys (vipKey, vipName, vipDuration) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, keyData.getKey());
                statement.setString(2, keyData.getVipName());
                statement.setLong(3, keyData.getDuration());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<KeyData> getAllKeysData() {
        List<KeyData> keysList = new ArrayList<>();
        String sql = "SELECT vipKey, vipName, vipDuration FROM vip_keys";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String vipKey = resultSet.getString("vipKey");
                String vipName = resultSet.getString("vipName");
                long vipDuration = resultSet.getLong("vipDuration");

                KeyData keyData = new KeyData(vipKey, vipName, vipDuration);
                keysList.add(keyData);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return keysList;
    }

    public KeyData findKeyData(String vipKey) {
        String sql = "SELECT vipKey, vipName, vipDuration FROM vip_keys WHERE vipKey = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, vipKey);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new KeyData(
                            resultSet.getString("vipKey"),
                            resultSet.getString("vipName"),
                            resultSet.getLong("vipDuration")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find key data: " + e.getMessage());
        }

        return null;
    }

    public void deleteKeyData(String vipKey) throws SQLException {
        String sql = "DELETE FROM vip_keys WHERE vipKey = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, vipKey);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateKeyData(KeyData keyData) throws SQLException {
        String sql = "UPDATE vip_keys SET vipKey = ?, vipName = ?, vipDuration = ? WHERE vipKey = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, keyData.getKey());
                statement.setString(2, keyData.getVipName());
                statement.setLong(3, keyData.getDuration());
                statement.setString(4, keyData.getKey());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}