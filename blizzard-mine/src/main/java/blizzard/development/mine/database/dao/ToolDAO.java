package blizzard.development.mine.database.dao;

import blizzard.development.mine.database.DatabaseConnection;
import blizzard.development.mine.database.storage.ToolData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolDAO {

    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS mine_tool (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "nickname VARCHAR(36), " +
                    "blocks INT)";

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

    public ToolData findToolData(String uuid) {
        String sql = "SELECT * FROM mine_tool WHERE uuid = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ToolData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getInt("blocks")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find tool data: " + e);
        }
        return null;
    }

    public void createToolData(ToolData toolData) throws SQLException {
        String sql = "INSERT INTO mine_tool (uuid, nickname, blocks) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getUuid());
                statement.setString(2, toolData.getNickname());
                statement.setInt(3, toolData.getBlocks());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteToolData(String uuid) throws SQLException {
        String sql = "DELETE FROM mine_tool WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, uuid);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateToolData(ToolData toolData) throws SQLException {
        String sql = "UPDATE mine_tool SET nickname = ?, blocks = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getNickname());
                statement.setInt(2, toolData.getBlocks());
                statement.setString(3, toolData.getUuid());
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
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("blocks")
                ));
            }
        }
        return toolDataList;
    }
}
