package blizzard.development.monsters.database.dao;

import blizzard.development.monsters.database.DatabaseConnection;
import blizzard.development.monsters.database.storage.MonstersData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MonstersDAO {
    public void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stat = conn.createStatement()) {

            String sql_monsters = "CREATE TABLE IF NOT EXISTS monsters (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "id VARCHAR(36), " +
                    "type VARCHAR(36), " +
                    "location VARCHAR(255), " +
                    "life INTEGER, " +
                    "owner VARCHAR(36)" +
                    ")";
            stat.execute(sql_monsters);
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

    public MonstersData findMonsterData(String uuid) {
        String sql = "SELECT * FROM monsters WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MonstersData(
                            resultSet.getString("uuid"),
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("location"),
                            resultSet.getInt("life"),
                            resultSet.getString("owner")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find monster data: " + e);
        }
        return null;
    }

    public void createMonsterData(MonstersData monsterData) throws SQLException {
        String sql = "INSERT INTO monsters (uuid, id, type, location, life, owner) VALUES (?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, monsterData.getUuid());
                statement.setString(2, monsterData.getId());
                statement.setString(3, monsterData.getType());
                statement.setString(4, monsterData.getLocation());
                statement.setInt(5, monsterData.getLife());
                statement.setString(6, monsterData.getOwner());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteMonsterData(MonstersData monsterData) throws SQLException {
        String sql = "DELETE FROM monsters WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, monsterData.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateMonsterData(MonstersData monsterData) throws SQLException {
        String sql = "UPDATE monsters SET id = ?, type = ?, location = ?, life = ?, owner = ? WHERE uuid = ?";
        executeUpdate(sql, statement -> {
            try {
                statement.setString(1, monsterData.getId());
                statement.setString(2, monsterData.getType());
                statement.setString(3, monsterData.getLocation());
                statement.setInt(4, monsterData.getLife());
                statement.setString(5, monsterData.getOwner());
                statement.setString(6, monsterData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<MonstersData> getAllMonstersData() throws SQLException {
        String sql = "SELECT * FROM monsters";
        List<MonstersData> monstersDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                monstersDataList.add(new MonstersData(
                        resultSet.getString("uuid"),
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("location"),
                        resultSet.getInt("life"),
                        resultSet.getString("owner")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve monsters data: " + e.getMessage());
        }

        return monstersDataList;
    }
}
