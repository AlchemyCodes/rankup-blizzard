package blizzard.development.clans.database.dao;

import blizzard.development.clans.database.DatabaseConnection;
import blizzard.development.clans.database.storage.ClansData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ClansDAO {

    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_clans = "CREATE TABLE IF NOT EXISTS clans (" +
                    "clan VARCHAR(36) PRIMARY KEY, " +
                    "owner VARCHAR(36), " +
                    "tag VARCHAR(36), " +
                    "name VARCHAR(36), " +
                    "members TEXT, " +
                    "max INT, " +
                    "money BIGINT, " +
                    "friendlyfire BOOLEAN, " +
                    "kdr DOUBLE, " +
                    "allies TEXT, " +
                    "base varchar(255), " +
                    "creationdate VARCHAR(10))";
            stat.execute(sql_clans);

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

    public ClansData findClansData(String clan) {
        String sqlpar = "SELECT * FROM clans WHERE clan = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, clan);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ClansData(
                            resultSet.getString("clan"),
                            resultSet.getString("owner"),
                            resultSet.getString("tag"),
                            resultSet.getString("name"),
                            resultSet.getString("members"),
                            resultSet.getInt("max"),
                            resultSet.getLong("money"),
                            resultSet.getBoolean("friendlyfire"),
                            resultSet.getDouble("kdr"),
                            resultSet.getString("allies"),
                            resultSet.getString("base"),
                            resultSet.getString("creationdate"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find clan data: " + e);
        }
        return null;
    }

    public void createClansData(ClansData clansData) throws SQLException {
        String sqlpar = "INSERT INTO clans (clan, owner, tag, name, members, max, money, friendlyfire, kdr, allies, base, creationdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sqlpar, (statement) -> {
            try {
                statement.setString(1, clansData.getClan());
                statement.setString(2, clansData.getOwner());
                statement.setString(3, clansData.getTag());
                statement.setString(4, clansData.getName());
                statement.setString(5, clansData.getMembers());
                statement.setInt(6, clansData.getMax());
                statement.setLong(7, clansData.getMoney());
                statement.setBoolean(8, clansData.isFriendlyfire());
                statement.setDouble(9, clansData.getKdr());
                statement.setString(10, clansData.getAllies());
                statement.setString(11, clansData.getBase());
                statement.setString(12, clansData.getCreationDate());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateClansData(ClansData clansData) throws SQLException {
        String sqlpar = "UPDATE clans SET owner = ?, tag = ?, name = ?, members = ?, max = ?, money = ?, friendlyfire = ?, kdr = ?, allies = ?, base = ?, creationdate = ? WHERE clan = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, clansData.getOwner());
                statement.setString(2, clansData.getTag());
                statement.setString(3, clansData.getName());
                statement.setString(4, clansData.getMembers());
                statement.setInt(5, clansData.getMax());
                statement.setLong(6, clansData.getMoney());
                statement.setBoolean(7, clansData.isFriendlyfire());
                statement.setDouble(8, clansData.getKdr());
                statement.setString(9, clansData.getAllies());
                statement.setString(10, clansData.getBase());
                statement.setString(11, clansData.getCreationDate());
                statement.setString(12, clansData.getClan());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteClansData(ClansData clansData) throws SQLException {
        String sqlpar = "DELETE FROM clans WHERE clan = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, clansData.getClan());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteClan(String clan) throws SQLException {
        String sql = "DELETE FROM clans WHERE clan = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, clan);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean clanTagExists(String tag) {
        String sqlpar = "SELECT COUNT(*) FROM clans WHERE tag = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, tag);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check clan tag: " + e);
        }
        return false;
    }

    public boolean clanNameExists(String name) {
        String sqlpar = "SELECT COUNT(*) FROM clans WHERE name = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check clan name: " + e);
        }
        return false;
    }

    public List<String> getMembers(String clan) {
        String sql = "SELECT members FROM clans WHERE clan = ?";
        List<String> membersList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, clan);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String members = resultSet.getString("members");
                    if (members != null && !members.isEmpty()) {
                        membersList = new ArrayList<>(Arrays.asList(members.split(",")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get members: " + e);
        }

        return membersList;
    }

    public boolean isMemberInClan(String clan, String member) {
        String sql = "SELECT members FROM clans WHERE clan = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, clan);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String members = resultSet.getString("members");
                    if (members != null && !members.isEmpty()) {
                        List<String> membersList = Arrays.asList(members.split(","));
                        return membersList.contains(member);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check member in clan: " + e);
        }
        return false;
    }

    public List<String> getAllies(String clan) {
        String sql = "SELECT allies FROM clans WHERE clan = ?";
        List<String> alliesList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, clan);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String allies = resultSet.getString("allies");
                    if (allies != null && !allies.isEmpty()) {
                        alliesList = new ArrayList<>(Arrays.asList(allies.split(",")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get allies: " + e);
        }

        return alliesList;
    }

    public List<ClansData> getAllClans() {
        List<ClansData> clansList = new ArrayList<>();
        String sql = "SELECT * FROM clans";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ClansData clan = new ClansData(
                        resultSet.getString("clan"),
                        resultSet.getString("owner"),
                        resultSet.getString("tag"),
                        resultSet.getString("name"),
                        resultSet.getString("members"),
                        resultSet.getInt("max"),
                        resultSet.getLong("money"),
                        resultSet.getBoolean("friendlyfire"),
                        resultSet.getDouble("kdr"),
                        resultSet.getString("allies"),
                        resultSet.getString("base"),
                        resultSet.getString("creationdate"));
                clansList.add(clan);
            }
        } catch (SQLException e) {
            System.out.println("Failed to load all clans: " + e);
        }
        return clansList;
    }
}
