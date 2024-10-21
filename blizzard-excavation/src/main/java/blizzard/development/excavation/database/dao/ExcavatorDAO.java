package blizzard.development.excavation.database.dao;

import blizzard.development.excavation.database.DatabaseConnection;
import blizzard.development.excavation.database.storage.ExcavatorData;

import java.sql.*;
import java.util.function.Consumer;

public class ExcavatorDAO {

    public void initializeDatabase() {
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement()) {

        String sql_excavator = "CREATE TABLE IF NOT EXISTS excavator_excavator (nickname varchar(36) primary key, efficiency int, agility int, extractor int)";

        statement.execute(sql_excavator);

    } catch (SQLException exception) {
        exception.printStackTrace();}
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

    public ExcavatorData findExcavatorData(String uuid) {
        String sqlpar = "SELECT * FROM extractor_extractor WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ExcavatorData(
                            resultSet.getString("nickname"),
                            resultSet.getInt("efficiency"),
                            resultSet.getInt("agility"),
                            resultSet.getInt("extractor"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public ExcavatorData findExcavatorDataByNickname(String player) {
        String sql = "SELECT * FROM excavator_excavator WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ExcavatorData(
                            resultSet.getString("nickname"),
                            resultSet.getInt("efficiency"),
                            resultSet.getInt("agility"),
                            resultSet.getInt("extractor"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }

    public void createExcavatorData(ExcavatorData excavatorData) throws SQLException {
        String sql_par = "INSERT INTO excavator_excavator (nickname, efficiency, agility, extractor) VALUES (?, ?, ?, ?)";

        executeUpdate(sql_par, (statement) -> {
            try {
                statement.setString(1, excavatorData.getNickname());
                statement.setInt(2, excavatorData.getEfficiency());
                statement.setInt(3, excavatorData.getAgility());
                statement.setInt(4, excavatorData.getExtractor());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteExcavatorData(ExcavatorData excavatorData) throws SQLException {
        String sqlpar = "DELETE FROM excavator_excavator WHERE nickname = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, excavatorData.getNickname());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateExcavatorData(ExcavatorData excavatorData) throws SQLException {
        String sqlpar = "UPDATE excavator_excavator SET efficiency = ?, agility = ?, extractor = ? WHERE nickname = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setInt(1, excavatorData.getEfficiency());
                statement.setInt(2, excavatorData.getAgility());
                statement.setInt(3, excavatorData.getExtractor());
                statement.setString(4, excavatorData.getNickname());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
