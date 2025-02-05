package blizzard.development.mine.database.dao;

import blizzard.development.mine.database.DatabaseConnection;
import blizzard.development.mine.database.storage.BoosterData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BoosterDAO {

    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS mine_booster (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "nickname VARCHAR(36) NOT NULL, " +
                    "booster_name VARCHAR(50), " +
                    "booster_duration INT)";

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

    public BoosterData findBoosterData(String uuid) {
        String sql = "SELECT * FROM mine_booster WHERE uuid = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new BoosterData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getString("booster_name"),
                            resultSet.getInt("booster_duration")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find booster data: " + e);
        }
        return null;
    }

    public void createBoosterData(BoosterData boosterData) throws SQLException {
        String sql = "INSERT INTO mine_booster (uuid, nickname, booster_name, booster_duration) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, boosterData.getUuid());
                statement.setString(2, boosterData.getNickname());
                statement.setString(3, boosterData.getBoosterName());
                statement.setInt(4, boosterData.getBoosterDuration());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteBoosterData(String uuid) throws SQLException {
        String sql = "DELETE FROM mine_booster WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, uuid);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateBoosterData(BoosterData boosterData) throws SQLException {
        String sql = "UPDATE mine_booster SET nickname = ?, booster_name = ?, booster_duration = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, boosterData.getNickname());
                statement.setString(2, boosterData.getBoosterName());
                statement.setInt(3, boosterData.getBoosterDuration());
                statement.setString(4, boosterData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<BoosterData> getAllBoostersData() throws SQLException {
        String sql = "SELECT * FROM mine_booster";
        List<BoosterData> boosterDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                boosterDataList.add(new BoosterData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("booster_name"),
                        resultSet.getInt("booster_duration")
                ));
            }
        }
        return boosterDataList;
    }
}
