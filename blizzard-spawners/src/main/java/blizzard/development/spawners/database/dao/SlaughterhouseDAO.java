package blizzard.development.spawners.database.dao;

import blizzard.development.spawners.database.DatabaseConnection;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SlaughterhouseDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS spawners_slaughterhouses (" +
                    "id VARCHAR(36) PRIMARY KEY, " +
                    "tier VARCHAR(50), " +
                    "location VARCHAR(255), " +
                    "nickname VARCHAR(36), " +
                    "state VARCHAR(50), " +
                    "plotId VARCHAR(36), " +
                    "friends TEXT, " +
                    "friendsLimit INTEGER)";
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
        }
    }

    public List<SlaughterhouseData> findSlaughterhouseDataByPlotId(String plotId) {
        String sql = "SELECT * FROM spawners_slaughterhouses WHERE plotId = ?";
        List<SlaughterhouseData> slaughterhouseDataList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, plotId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Gson gson = new Gson();
                    String friendsJson = resultSet.getString("friends");
                    List<String> friends = friendsJson != null
                            ? gson.fromJson(friendsJson, new TypeToken<List<String>>() {}.getType())
                            : new ArrayList<>();
                    slaughterhouseDataList.add(new SlaughterhouseData(
                            resultSet.getString("id"),
                            resultSet.getString("tier"),
                            resultSet.getString("location"),
                            resultSet.getString("nickname"),
                            resultSet.getString("state"),
                            resultSet.getString("plotId"),
                            friends,
                            resultSet.getInt("friendsLimit")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slaughterhouseDataList;
    }

    public void createSlaughterhouseData(SlaughterhouseData slaughterhouseData) throws SQLException {
        String sql = "INSERT INTO spawners_slaughterhouses (id, tier, location, nickname, state, plotId, friends, friendsLimit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, slaughterhouseData.getId());
                statement.setString(2, slaughterhouseData.getTier());
                statement.setString(3, slaughterhouseData.getLocation());
                statement.setString(4, slaughterhouseData.getNickname());
                statement.setString(5, slaughterhouseData.getState());
                statement.setString(6, slaughterhouseData.getPlotId());
                statement.setString(7, new Gson().toJson(slaughterhouseData.getFriends()));
                statement.setInt(8, slaughterhouseData.getFriendsLimit());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteSlaughterhouseData(String id) throws SQLException {
        String sql = "DELETE FROM spawners_slaughterhouses WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateSlaughterhouseData(SlaughterhouseData slaughterhouseData) throws SQLException {
        String sql = "UPDATE spawners_slaughterhouses SET tier = ?, location = ?, nickname = ?, state = ?, plotId = ?, friends = ?, friendsLimit = ? WHERE id = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, slaughterhouseData.getTier());
                statement.setString(2, slaughterhouseData.getLocation());
                statement.setString(3, slaughterhouseData.getNickname());
                statement.setString(4, slaughterhouseData.getState());
                statement.setString(5, slaughterhouseData.getPlotId());
                statement.setString(6, new Gson().toJson(slaughterhouseData.getFriends()));
                statement.setInt(7, slaughterhouseData.getFriendsLimit());
                statement.setString(8, slaughterhouseData.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<SlaughterhouseData> getAllSlaughterhousesData() throws SQLException {
        String sql = "SELECT * FROM spawners_slaughterhouses";
        List<SlaughterhouseData> slaughterhouseDataList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Gson gson = new Gson();
                String friendsJson = resultSet.getString("friends");
                List<String> friends = friendsJson != null
                        ? gson.fromJson(friendsJson, new TypeToken<List<String>>() {}.getType())
                        : new ArrayList<>();
                slaughterhouseDataList.add(new SlaughterhouseData(
                        resultSet.getString("id"),
                        resultSet.getString("tier"),
                        resultSet.getString("location"),
                        resultSet.getString("nickname"),
                        resultSet.getString("state"),
                        resultSet.getString("plotId"),
                        friends,
                        resultSet.getInt("friendsLimit")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slaughterhouseDataList;
    }
}
