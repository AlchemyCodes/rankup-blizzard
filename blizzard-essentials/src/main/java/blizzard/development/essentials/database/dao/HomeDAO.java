package blizzard.development.essentials.database.dao;

import blizzard.development.essentials.database.DatabaseConnection;
import blizzard.development.essentials.database.storage.HomeData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HomeDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS essentials_homes (uuid varchar(36) primary key, nickname varchar(36), home_name varchar(256), location varchar(256))";

            stat.execute(sql);
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

    public HomeData findHomeData(String id) {
        String sql = "SELECT * FROM essentials_homes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new HomeData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getString("home_name"),
                        resultSet.getString("location")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find spawner data: " + e);
        }
        return null;
    }

    public void createHomeData(HomeData homeData) throws SQLException {
        String sql = "INSERT INTO essentials_homes (uuid, nickname, home_name, location) VALUES (?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, homeData.getUuid());
                statement.setString(2, homeData.getNickname());
                statement.setString(3, homeData.getName());
                statement.setString(4, homeData.getLocation());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteHomeData(String id) throws SQLException {
        String sql = "DELETE FROM essentials_homes WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateHomeData(HomeData homeData) throws SQLException {
        String sql = "UPDATE essentials_homes SET nickname = ?, home_name = ?, location = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, homeData.getNickname());
                statement.setString(2, homeData.getName());
                statement.setString(3, homeData.getLocation());
                statement.setString(4, homeData.getUuid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<HomeData> getAllHomeData() throws SQLException {
        String sql = "SELECT * FROM spawners";
        List<HomeData> homeDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                homeDataList.add(new HomeData(
                    resultSet.getString("uuid"),
                    resultSet.getString("nickname"),
                    resultSet.getString("home_name"),
                    resultSet.getString("location")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve home data: " + e.getMessage());
        }

        return homeDataList;
    }
}
