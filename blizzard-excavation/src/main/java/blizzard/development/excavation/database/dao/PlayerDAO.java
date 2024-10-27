package blizzard.development.excavation.database.dao;

import blizzard.development.excavation.database.DatabaseConnection;
import blizzard.development.excavation.database.storage.PlayerData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerDAO {

    public void initializeDatabase() {
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement()) {

            String sql_excavator = "CREATE TABLE IF NOT EXISTS excavator_player (uuid varchar(36) primary key, nickname varchar(36), excavation TINYINT(1), blocks int)";

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

    public PlayerData findPlayerData(String uuid) {
        String sqlpar = "SELECT * FROM excavator_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayerData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getBoolean("excavation"),
                            resultSet.getInt("blocks"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public PlayerData findPlayerDataByNickname(String player) {
        String sql = "SELECT * FROM excavator_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayerData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getBoolean("excavation"),
                            resultSet.getInt("blocks"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayerData playerData) throws SQLException {
        String sql_par = "INSERT INTO excavator_player (uuid, nickname, excavation, blocks) VALUES (?, ?, ?, ?)";

        executeUpdate(sql_par, (statement) -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setBoolean(3, playerData.getInExcavation());
                statement.setInt(4, playerData.getBlocks());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayerData playerData) throws SQLException {
        String sqlpar = "DELETE FROM excavator_player WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayerData playerData) throws SQLException {
        String sqlpar = "UPDATE excavator_player SET nickname = ?, excavation = ?, blocks = ? WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setBoolean(2, playerData.getInExcavation());
                statement.setInt(3, playerData.getBlocks());
                statement.setString(4, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<PlayerData> getAllPlayersData() throws SQLException {
        String sql = "SELECT * FROM excavator_player";
        List<PlayerData> playerDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                playerDataList.add(new PlayerData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getBoolean("excavation"),
                        resultSet.getInt("blocks")
                ));
            }
        }
        return playerDataList;
    }

//    public List<PlayerData> getTopPlayers() throws Exception {
//        List<PlayerData> topPlayers = new ArrayList<>();
//        String sql = "SELECT uuid, nickname, excavation, blocks FROM excavator_player ORDER BY blocks DESC LIMIT 10";
//
//        try (Connection connection = DatabaseConnection.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            while (resultSet.next()) {
//                PlayerData playerData = new PlayerData(
//                        resultSet.getString("uuid"),
//                        resultSet.getString("nickname"),
//                        resultSet.getBoolean("excavation"),
//                        resultSet.getInt("blocks"));
//                topPlayers.add(playerData);
//            }
//        } catch (SQLException exception) {
//            throw new RuntimeException("Erro ao buscar top players: " + exception.getMessage(), exception);
//        }
//
//        return topPlayers;
//    }

}
