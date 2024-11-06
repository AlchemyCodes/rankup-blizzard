package blizzard.development.fishing.database.dao;

import blizzard.development.fishing.database.DatabaseConnection;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.enums.RodMaterials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RodsDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS fishing_rods ("
                    + "uuid VARCHAR(36) PRIMARY KEY, "
                    + "nickname VARCHAR(36), "
                    + "strength INT DEFAULT 0, "
                    + "xp DOUBLE DEFAULT 0, "
                    + "experienced INT DEFAULT 0, "
                    + "lucky INT DEFAULT 0, "
                    + "material VARCHAR(36))";
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

    public RodsData findRodsData(String uuid) {
        String sql = "SELECT * FROM fishing_rods WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String materialList = resultSet.getString("material");
                    List<RodMaterials> materials = new ArrayList<>();
                    for (String s : materialList.split(",")) {
                        materials.add(RodMaterials.valueOf(s));
                    }

                    return new RodsData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getInt("strength"),
                            resultSet.getDouble("xp"),
                            resultSet.getInt("experienced"),
                            resultSet.getInt("lucky"),
                            materials);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createRodsData(RodsData rodsData) throws SQLException {
        String sql = "INSERT INTO fishing_rods (uuid, nickname, strength, xp, experienced, lucky, material) VALUES (?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, (statement) -> {
            try {
                statement.setString(1, rodsData.getUuid());
                statement.setString(2, rodsData.getNickname());
                statement.setInt(3, rodsData.getStrength());
                statement.setDouble(4, rodsData.getXp());
                statement.setInt(5, rodsData.getExperienced());
                statement.setInt(6, rodsData.getLucky());
                statement.setString(7, rodsData.getRodMaterials().stream().map(Enum::name).collect(Collectors.joining(",")));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteRodsData(RodsData rodsData) throws SQLException {
        String sql = "DELETE FROM fishing_rods WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, rodsData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateRodsData(RodsData rodsData) throws SQLException {
        String sql = "UPDATE fishing_rods SET nickname = ?, strength = ?, xp = ?, experienced = ?, lucky = ?, material = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, rodsData.getNickname());
                statement.setInt(2, rodsData.getStrength());
                statement.setDouble(3, rodsData.getXp());
                statement.setInt(4, rodsData.getExperienced());
                statement.setInt(5, rodsData.getLucky());
                statement.setString(6, rodsData.getRodMaterials().stream().map(Enum::name).collect(Collectors.joining(",")));
                statement.setString(7, rodsData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
