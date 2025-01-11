package blizzard.development.mine.database.dao;

import blizzard.development.mine.database.DatabaseConnection;
import blizzard.development.mine.database.storage.PlayerData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerDAO {

    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String sql_mine = "CREATE TABLE IF NOT EXISTS mine_player (uuid varchar(36) primary key, nickname varchar(36), area int, area_block varchar(36), blocks int, in_mine TINYINT(1), friends TEXT)";

            statement.execute(sql_mine);

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

    public PlayerData findPlayerData(String uuid) {
        String sqlpar = "SELECT * FROM mine_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    Gson gson = new Gson();
                    String friendsJson = resultSet.getString("friends");
                    List<String> friends = friendsJson != null
                        ? gson.fromJson(friendsJson, new TypeToken<List<String>>() {}.getType())
                        : new ArrayList<>();

                    return new PlayerData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("area"),
                        resultSet.getString("area_block"),
                        resultSet.getInt("blocks"),
                        resultSet.getBoolean("in_mine"),
                        friends
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public PlayerData findPlayerDataByNickname(String player) {
        String sql = "SELECT * FROM mine_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    Gson gson = new Gson();
                    String friendsJson = resultSet.getString("friends");
                    List<String> friends = friendsJson != null
                        ? gson.fromJson(friendsJson, new TypeToken<List<String>>() {}.getType())
                        : new ArrayList<>();

                    return new PlayerData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("area"),
                        resultSet.getString("area_block"),
                        resultSet.getInt("blocks"),
                        resultSet.getBoolean("in_mine"),
                        friends
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayerData playerData) throws SQLException {
        String sql_par = "INSERT INTO mine_player (uuid, nickname, area, area_block, blocks, in_mine, friends) VALUES (?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(sql_par, (statement) -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setInt(3, playerData.getArea());
                statement.setString(4, playerData.getAreaBlock());
                statement.setInt(5, playerData.getBlocks());
                statement.setBoolean(6, playerData.getIsInMine());
                statement.setString(7, new Gson().toJson(playerData.getFriends()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayerData playerData) throws SQLException {
        String sqlpar = "DELETE FROM mine_player WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayerData playerData) throws SQLException {
        String sqlpar = "UPDATE mine_player SET nickname = ?, area = ?, area_block = ?, blocks = ?, in_mine = ?, friends = ? WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setInt(2, playerData.getArea());
                statement.setString(3, playerData.getAreaBlock());
                statement.setInt(4, playerData.getBlocks());
                statement.setBoolean(5, playerData.getIsInMine());
                statement.setString(6, new Gson().toJson(playerData.getFriends()));
                statement.setString(7, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<PlayerData> getAllPlayersData() throws SQLException {
        String sql = "SELECT * FROM mine_player";
        List<PlayerData> playerDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {

                Gson gson = new Gson();
                String friendsJson = resultSet.getString("friends");
                List<String> friends = friendsJson != null
                    ? gson.fromJson(friendsJson, new TypeToken<List<String>>() {}.getType())
                    : new ArrayList<>();

                playerDataList.add(new PlayerData(
                    resultSet.getString("uuid"),
                    resultSet.getString("nickname"),
                    resultSet.getInt("area"),
                    resultSet.getString("area_block"),
                    resultSet.getInt("blocks"),
                    resultSet.getBoolean("in_mine"),
                    friends
                ));
            }
        }
        return playerDataList;
    }
}

