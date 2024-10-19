package blizzard.development.bosses.database.dao;

import blizzard.development.bosses.database.DatabaseConnection;
import blizzard.development.bosses.database.storage.ToolsData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolsDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_items = "CREATE TABLE IF NOT EXISTS bosses_tools (" +
                    "id VARCHAR(36) PRIMARY KEY, " +
                    "type VARCHAR(50), " +
                    "nickname VARCHAR(36))";
            stat.execute(sql_items);

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

    public ToolsData findToolsData(String id) {
        String sql = "SELECT * FROM bosses_tools WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ToolsData(
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("nickname"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find tools data: " + e.getMessage());
        }
        return null;
    }

    public void createToolsData(ToolsData toolsData) throws SQLException {
        String sql = "INSERT INTO bosses_tools (id, type, nickname) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolsData.getId());
                statement.setString(2, toolsData.getType());
                statement.setString(3, toolsData.getNickname());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteToolsData(ToolsData toolsData) throws SQLException {
        String sql = "DELETE FROM bosses_tools WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolsData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateToolsData(ToolsData toolsData) throws SQLException {
        String sql = "UPDATE bosses_tools SET type = ?, nickname = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolsData.getType());
                statement.setString(2, toolsData.getNickname());
                statement.setString(3, toolsData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<ToolsData> getAllToolsData() throws SQLException {
        String sql = "SELECT * FROM bosses_tools";
        List<ToolsData> toolsDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                toolsDataList.add(new ToolsData(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("nickname")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve tools data: " + e.getMessage());
        }

        return toolsDataList;
    }
}
