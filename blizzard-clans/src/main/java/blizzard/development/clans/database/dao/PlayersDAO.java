package blizzard.development.clans.database.dao;

import blizzard.development.clans.database.DatabaseConnection;
import blizzard.development.clans.database.storage.PlayersData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PlayersDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_player = "CREATE TABLE IF NOT EXISTS clans_users (" +
                    "uuid varchar(36) primary key, " +
                    "nickname varchar(36), " +
                    "clan varchar(36), " +
                    "role varchar(36), " +
                    "invites TEXT, " +
                    "kills INT, " +
                    "deaths INT)";
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
        String sqlpar = "SELECT * FROM clans_users WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getString("clan"),
                            resultSet.getString("role"),
                            resultSet.getString("invites"),
                            resultSet.getInt("kills"),
                            resultSet.getInt("deaths"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public PlayersData findPlayerDataByName(String player) {
        String sql = "SELECT * FROM clans_users WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PlayersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("nickname"),
                            resultSet.getString("clan"),
                            resultSet.getString("role"),
                            resultSet.getString("invites"),
                            resultSet.getInt("kills"),
                            resultSet.getInt("deaths"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }


    public List<String> getInvites(String player) {
        String sql = "SELECT invites FROM clans_users WHERE nickname = ?";
        List<String> invitesList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String invites = resultSet.getString("invites");
                    if (invites != null && !invites.isEmpty()) {
                        invitesList = new ArrayList<>(Arrays.asList(invites.split(",")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get invites: " + e);
        }

        return invitesList;
    }

    public int getInviteCount(String player) {
        String sql = "SELECT invites FROM clans_users WHERE nickname = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String invites = resultSet.getString("invites");
                    if (invites != null && !invites.isEmpty()) {
                        return invites.split(",").length;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get invite count: " + e);
        }

        return 0;
    }


    public void createPlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "INSERT INTO clans_users (uuid, nickname, clan, role, invites, kills, deaths) VALUES (?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sqlpar, (statement) -> {
            try {
                statement.setString(1, playerData.getUuid());
                statement.setString(2, playerData.getNickname());
                statement.setString(3, playerData.getClan());
                statement.setString(4, playerData.getRole());
                statement.setString(5, playerData.getInvites());
                statement.setInt(6, playerData.getKills());
                statement.setInt(7, playerData.getDeaths());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void deletePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "DELETE FROM clans_users WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePlayerData(PlayersData playerData) throws SQLException {
        String sqlpar = "UPDATE clans_users SET nickname = ?, clan = ?, role = ?, invites = ?, kills = ?, deaths = ? WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, playerData.getNickname());
                statement.setString(2, playerData.getClan());
                statement.setString(3, playerData.getRole());
                statement.setString(4, playerData.getInvites());
                statement.setInt(5, playerData.getKills());
                statement.setInt(6, playerData.getDeaths());
                statement.setString(7, playerData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
