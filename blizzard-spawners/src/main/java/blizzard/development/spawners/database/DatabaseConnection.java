package blizzard.development.spawners.database;

import blizzard.development.spawners.utils.PluginImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private HikariDataSource dataSource;

    private DatabaseConnection() {
        loadDatabaseConfig();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void loadDatabaseConfig() {
        String host = PluginImpl.getInstance().Database.getString("mysql.host");
        String dbName = PluginImpl.getInstance().Database.getString("mysql.db_name");
        String user = PluginImpl.getInstance().Database.getString("mysql.user");
        String password = PluginImpl.getInstance().Database.getString("mysql.password");
        String url = String.format("jdbc:mysql://%s/%s", host, dbName);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(10);

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Database connection pool closed.");
        }
    }
}
