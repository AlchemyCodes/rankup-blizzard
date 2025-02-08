package blizzard.development.mine.database.dao;

import blizzard.development.mine.database.DatabaseConnection;
import blizzard.development.mine.database.storage.ToolData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolDAO {

    public void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS mine_tool ("
                + "id VARCHAR(36) PRIMARY KEY, "
                + "type VARCHAR(50), "
                + "skin VARCHAR(50), "
                + "owner VARCHAR(50), "
                + "blocks INT, "
                + "meteor INT)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void executeUpdate(String sql, Consumer<PreparedStatement> setter) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setter.accept(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;
        }
    }

    public ToolData findToolData(String id) {
        String sql = "SELECT * FROM mine_tool WHERE id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ToolData(
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("skin"),
                            resultSet.getString("owner"),
                            resultSet.getInt("blocks"),
                            resultSet.getInt("meteor")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find tool data: " + e);
        }
        return null;
    }

    public void createToolData(ToolData toolData) throws SQLException {
        String sql = "INSERT INTO mine_tool (id, type, skin, owner, blocks, meteor) VALUES (?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getId());
                statement.setString(2, toolData.getType());
                statement.setString(3, toolData.getSkin());
                statement.setString(4, toolData.getOwner());
                statement.setInt(5, toolData.getBlocks());
                statement.setInt(6, toolData.getMeteor());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteToolData(String id) throws SQLException {
        String sql = "DELETE FROM mine_tool WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateToolData(ToolData toolData) throws SQLException {
        String sql = "UPDATE mine_tool SET type = ?, skin = ?, owner = ?, blocks = ?, meteor = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getType());
                statement.setString(2, toolData.getSkin());
                statement.setString(3, toolData.getOwner());
                statement.setInt(4, toolData.getBlocks());
                statement.setInt(5, toolData.getMeteor());
                statement.setString(6, toolData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<ToolData> getAllToolsData() throws SQLException {
        String sql = "SELECT * FROM mine_tool";
        List<ToolData> toolDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                toolDataList.add(new ToolData(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("skin"),
                        resultSet.getString("owner"),
                        resultSet.getInt("blocks"),
                        resultSet.getInt("meteor")
                ));
            }
        }
        return toolDataList;
    }
}

