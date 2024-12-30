package blizzard.development.monsters.database.dao;

import blizzard.development.monsters.database.DatabaseConnection;
import blizzard.development.monsters.database.storage.ToolsData;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolsDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_tools = "CREATE TABLE IF NOT EXISTS monsters_tools (" +
                    "id VARCHAR(36) PRIMARY KEY, " +
                    "type VARCHAR(36), " +
                    "damage INTEGER, " +
                    "experienced INTEGER, " +
                    "owner VARCHAR(36)" +
                    ")";
            stat.execute(sql_tools);
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

    public ToolsData findToolData(String id) {
        String sql = "SELECT * FROM monsters_tools WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ToolsData(
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getInt("damage"),
                            resultSet.getInt("experienced"),
                            resultSet.getString("owner")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find tool data: " + e);
        }
        return null;
    }

    public void createToolData(ToolsData toolData) throws SQLException {
        String sql = "INSERT INTO monsters_tools (id, type, damage, experienced, owner) VALUES (?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getId());
                statement.setString(2, toolData.getType());
                statement.setInt(3, toolData.getDamage());
                statement.setInt(4, toolData.getExperienced());
                statement.setString(5, toolData.getOwner());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteToolData(ToolsData toolData) throws SQLException {
        String sql = "DELETE FROM monsters_tools WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateToolData(ToolsData toolData) throws SQLException {
        String sql = "UPDATE monsters_tools SET type = ?, damage = ?, experienced = ?, owner = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getType());
                statement.setInt(2, toolData.getDamage());
                statement.setInt(3, toolData.getExperienced());
                statement.setString(4, toolData.getOwner());
                statement.setString(5, toolData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<ToolsData> getAllToolsData() throws SQLException {
        String sql = "SELECT * FROM monsters_tools";
        List<ToolsData> toolsDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                toolsDataList.add(new ToolsData(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getInt("damage"),
                        resultSet.getInt("experienced"),
                        resultSet.getString("owner")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve tools data: " + e.getMessage());
        }

        return toolsDataList;
    }
}
