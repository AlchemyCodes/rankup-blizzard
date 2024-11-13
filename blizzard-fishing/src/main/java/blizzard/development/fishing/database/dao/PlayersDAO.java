package blizzard.development.fishing.database.dao;

import blizzard.development.fishing.database.DatabaseConnection;
import blizzard.development.fishing.database.storage.PlayersData;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayersDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS fishing_users ("
                    + "uuid varchar(36) primary key, "
                    + "nickname varchar(36), "
                    + "bacalhau int DEFAULT 0, "
                    + "salmao int DEFAULT 0, "
                    + "caranguejo int DEFAULT 0, "
                    + "lagosta int DEFAULT 0, "
                    + "lula int DEFAULT 0, "
                    + "lula_brilhante int DEFAULT 0, "
                    + "tubarao int DEFAULT 0, "
                    + "baleia int DEFAULT 0, "
                    + "frozen_fish int DEFAULT 0,"
                    + "storage int DEFAULT 50, "
                    + "trash int DEFAULT 0)";
            stat.execute(sql_player);

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

    public PlayersData findPlayerData(String uuid) {
        String sql = "SELECT * FROM fishing_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getInt("bacalhau"),
                            resultSet.getInt("salmao"),
                            resultSet.getInt("caranguejo"),
                            resultSet.getInt("lagosta"),
                            resultSet.getInt("lula"),
                            resultSet.getInt("lula_brilhante"),
                            resultSet.getInt("tubarao"),
                            resultSet.getInt("baleia"),
                            resultSet.getInt("frozen_fish"),
                            resultSet.getInt("storage"),
                            resultSet.getInt("trash"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public PlayersData findPlayerDataByNickname(String nickname) {
        String sql = "SELECT * FROM fishing_users WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nickname);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getInt("bacalhau"),
                            resultSet.getInt("salmao"),
                            resultSet.getInt("caranguejo"),
                            resultSet.getInt("lagosta"),
                            resultSet.getInt("lula"),
                            resultSet.getInt("lula_brilhante"),
                            resultSet.getInt("tubarao"),
                            resultSet.getInt("baleia"),
                            resultSet.getInt("frozen_fish"),
                            resultSet.getInt("storage"),
                            resultSet.getInt("trash"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sql = "INSERT INTO fishing_users (uuid, nickname, bacalhau, salmao, caranguejo, lagosta, lula, lula_brilhante, tubarao, baleia, frozen_fish, storage, trash) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setInt(3, playerData.getBacalhau());
                statement.setInt(4, playerData.getSalmao());
                statement.setInt(5, playerData.getCaranguejo());
                statement.setInt(6, playerData.getLagosta());
                statement.setInt(7, playerData.getLula());
                statement.setInt(8, playerData.getLula_brilhante());
                statement.setInt(9, playerData.getTubarao());
                statement.setInt(10, playerData.getBaleia());
                statement.setInt(11, playerData.getFrozen_fish());
                statement.setInt(12, playerData.getStorage());
                statement.setInt(13, playerData.getTrash());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sql = "DELETE FROM fishing_users WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sql = "UPDATE fishing_users SET nickname = ?, bacalhau = ?, salmao = ?, caranguejo = ?, lagosta = ?, lula = ?, lula_brilhante = ?, tubarao = ?, baleia = ?, frozen_fish = ?, storage = ?, trash = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setInt(2, playerData.getBacalhau());
                statement.setInt(3, playerData.getSalmao());
                statement.setInt(4, playerData.getCaranguejo());
                statement.setInt(5, playerData.getLagosta());
                statement.setInt(6, playerData.getLula());
                statement.setInt(7, playerData.getLula_brilhante());
                statement.setInt(8, playerData.getTubarao());
                statement.setInt(9, playerData.getBaleia());
                statement.setInt(10, playerData.getFrozen_fish());
                statement.setInt(11, playerData.getStorage());
                statement.setInt(12, playerData.getTrash());
                statement.setString(13, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<PlayersData> getAllPlayerData() throws SQLException {
        String sql = "SELECT * FROM fishing_users";
        List<PlayersData> playersDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                playersDataList.add(new PlayersData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("bacalhau"),
                        resultSet.getInt("salmao"),
                        resultSet.getInt("caranguejo"),
                        resultSet.getInt("lagosta"),
                        resultSet.getInt("lula"),
                        resultSet.getInt("lula_brilhante"),
                        resultSet.getInt("tubarao"),
                        resultSet.getInt("baleia"),
                        resultSet.getInt("frozen_fish"),
                        resultSet.getInt("storage"),
                        resultSet.getInt("trash")));
            }
        }
        return playersDataList;
    }
}
