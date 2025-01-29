package blizzard.development.farm.database.dao;

import blizzard.development.farm.database.DatabaseConnection;
import blizzard.development.farm.database.storage.StorageData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StorageDAO {

    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String sql_storage = "CREATE TABLE IF NOT EXISTS storage_player (uuid varchar(36) primary key, nickname varchar(36), carrots_stored int, potatoes_stored int, wheat_stored int, melon_stored int, cactus_stored int)";

            statement.execute(sql_storage);

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

    public StorageData findStorageData(String uuid) {
        String sqlpar = "SELECT * FROM storage_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlpar)) {
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    return new StorageData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("carrots_stored"),
                        resultSet.getInt("potatoes_stored"),
                        resultSet.getInt("wheat_stored"),
                        resultSet.getInt("melon_stored"),
                        resultSet.getInt("cactus_stored")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data: " + e);
        }
        return null;
    }

    public StorageData findStorageDataByNickname(String player) {
        String sql = "SELECT * FROM storage_player WHERE uuid = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    return new StorageData(
                        resultSet.getString("uuid"),
                        resultSet.getString("nickname"),
                        resultSet.getInt("carrots_stored"),
                        resultSet.getInt("potatoes_stored"),
                        resultSet.getInt("wheat_stored"),
                        resultSet.getInt("melon_stored"),
                        resultSet.getInt("cactus_stored")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find player data by name: " + e);
        }
        return null;
    }

    public void createStorageData(StorageData storageData) throws SQLException {
        String sql_par = "INSERT INTO storage_player (uuid, nickname, carrots_stored, potatoes_stored, wheat_stored, melon_stored, cactus_stored) VALUES (?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(sql_par, (statement) -> {
            try {
                statement.setString(1, storageData.getUuid());
                statement.setString(2, storageData.getNickname());
                statement.setInt(3, storageData.getCarrotsStored());
                statement.setInt(4, storageData.getPotatoesStored());
                statement.setInt(5, storageData.getWheatStored());
                statement.setInt(6, storageData.getMelonStored());
                statement.setInt(7, storageData.getCactusStored());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteStorageData(StorageData storageData) throws SQLException {
        String sqlpar = "DELETE FROM storage_player WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, storageData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateStorageData(StorageData storageData) throws SQLException {
        String sqlpar = "UPDATE storage_player SET nickname = ?, carrots_stored = ?, potatoes_stored = ?, wheat_stored = ?, melon_stored = ?, cactus_stored = ? WHERE uuid = ?";
        executeUpdate(sqlpar, statement -> {
            try {
                statement.setString(1, storageData.getNickname());
                statement.setInt(2, storageData.getCarrotsStored());
                statement.setInt(3, storageData.getPotatoesStored());
                statement.setInt(4, storageData.getWheatStored());
                statement.setInt(5, storageData.getMelonStored());
                statement.setInt(6, storageData.getCactusStored());
                statement.setString(7, storageData.getUuid());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<StorageData> getAllStorageData() throws SQLException {
        String sql = "SELECT * FROM storage_player";
        List<StorageData> storageDataList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {

                storageDataList.add(new StorageData(
                    resultSet.getString("uuid"),
                    resultSet.getString("nickname"),
                    resultSet.getInt("carrots_stored"),
                    resultSet.getInt("potatoes_stored"),
                    resultSet.getInt("wheat_stored"),
                    resultSet.getInt("melon_stored"),
                    resultSet.getInt("cactus_stored")
                ));
            }
        }
        return storageDataList;
    }
}
