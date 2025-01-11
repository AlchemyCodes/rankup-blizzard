package blizzard.development.plantations.database.dao;

import blizzard.development.plantations.database.DatabaseConnection;
import blizzard.development.plantations.database.storage.ToolData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ToolDAO {

    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String sql_tool = "CREATE TABLE IF NOT EXISTS plantations_tools (id varchar(36) primary key, type varchar(36), skin varchar(36),nickname varchar(36), blocks int, botany int, agility int, explosion int, thunderstorm int, xray int, blizzard int)";
            statement.execute(sql_tool);

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

    public ToolData findToolData(String uuid) {
        String sql = "SELECT * FROM plantations_tools WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ToolData(
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("skin"),
                            resultSet.getString("nickname"),
                            resultSet.getInt("blocks"),
                            resultSet.getInt("botany"),
                            resultSet.getInt("agility"),
                            resultSet.getInt("explosion"),
                            resultSet.getInt("thunderstorm"),
                            resultSet.getInt("xray"),
                            resultSet.getInt("blizzard")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find tool data: " + e);
        }
        return null;
    }

    public void createToolData(ToolData toolData) throws SQLException {
        String sql = "INSERT INTO plantations_tools (id, type, skin, nickname, blocks, botany, agility, explosion, thunderstorm, xray, blizzard) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(sql, (statement) -> {
            try {
                statement.setString(1, toolData.getId());
                statement.setString(2, toolData.getType());
                statement.setString(3, toolData.getSkin());
                statement.setString(4, toolData.getNickname());
                statement.setInt(5, toolData.getBlocks() != null? toolData.getBlocks() : 0);
                statement.setInt(6, toolData.getBotany() != null ? toolData.getBotany() : 0);
                statement.setInt(7, toolData.getAgility() != null ? toolData.getAgility() : 0);
                statement.setInt(8, toolData.getExplosion() != null ? toolData.getExplosion() : 0);
                statement.setInt(9, toolData.getThunderstorm() != null ? toolData.getThunderstorm() : 0);
                statement.setInt(10, toolData.getXray() != null ? toolData.getXray() : 0);
                statement.setInt(11, toolData.getBlizzard() != null ? toolData.getBlizzard() : 0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateToolData(ToolData toolData) throws SQLException {
        String sql = "UPDATE plantations_tools SET type = ?, skin = ?, nickname = ?, blocks = ?, botany = ?, agility = ?, explosion = ?, thunderstorm = ?, xray = ?, blizzard = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, toolData.getType());
                statement.setString(2, toolData.getSkin());
                statement.setString(3, toolData.getNickname());
                statement.setInt(4, toolData.getBlocks() != null ? toolData.getBlocks() : 0);
                statement.setInt(5, toolData.getBotany() != null ? toolData.getBotany() : 0);
                statement.setInt(6, toolData.getAgility() != null ? toolData.getAgility() : 0);
                statement.setInt(7, toolData.getExplosion() != null ? toolData.getExplosion() : 0);
                statement.setInt(8, toolData.getThunderstorm() != null ? toolData.getThunderstorm() : 0);
                statement.setInt(9, toolData.getXray() != null ? toolData.getXray() : 0);
                statement.setInt(10, toolData.getBlizzard() != null ? toolData.getBlizzard() : 0);
                statement.setString(11, toolData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<ToolData> getAllToolData() throws SQLException {
        String sql = "SELECT * FROM plantations_tools";
        List<ToolData> toolsDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                toolsDataList.add(new ToolData(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("skin"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("blocks"),
                        resultSet.getInt("botany"),
                        resultSet.getInt("agility"),
                        resultSet.getInt("explosion"),
                        resultSet.getInt("thunderstorm"),
                        resultSet.getInt("xray"),
                        resultSet.getInt("blizzard")

                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve tool data: " + e.getMessage());
        }

        return toolsDataList;
    }
}
