/*    */ package blizzard.development.rankup.database;
/*    */ 
/*    */ import blizzard.development.rankup.utils.PluginImpl;
/*    */ import com.zaxxer.hikari.HikariConfig;
/*    */ import com.zaxxer.hikari.HikariDataSource;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class DatabaseConnection
/*    */ {
/*    */   private static DatabaseConnection instance;
/*    */   private HikariDataSource dataSource;
/*    */   
/*    */   private DatabaseConnection() {
/* 15 */     loadDatabaseConfig();
/*    */   }
/*    */   
/*    */   public static synchronized DatabaseConnection getInstance() {
/* 19 */     if (instance == null) {
/* 20 */       instance = new DatabaseConnection();
/*    */     }
/* 22 */     return instance;
/*    */   }
/*    */   
/*    */   private void loadDatabaseConfig() {
/* 26 */     String host = (PluginImpl.getInstance()).Database.getString("mysql.host");
/* 27 */     String dbName = (PluginImpl.getInstance()).Database.getString("mysql.db_name");
/* 28 */     String user = (PluginImpl.getInstance()).Database.getString("mysql.user");
/* 29 */     String password = (PluginImpl.getInstance()).Database.getString("mysql.password");
/* 30 */     String url = String.format("jdbc:mysql://%s/%s", new Object[] { host, dbName });
/*    */     
/* 32 */     HikariConfig config = new HikariConfig();
/* 33 */     config.setJdbcUrl(url);
/* 34 */     config.setUsername(user);
/* 35 */     config.setPassword(password);
/* 36 */     config.setMaximumPoolSize(10);
/* 37 */     config.setMinimumIdle(5);
/* 38 */     config.setMaxLifetime(60000L);
/* 39 */     config.setConnectionTimeout(30000L);
/*    */     
/* 41 */     this.dataSource = new HikariDataSource(config);
/*    */   }
/*    */   
/*    */   public Connection getConnection() throws SQLException {
/* 45 */     return this.dataSource.getConnection();
/*    */   }
/*    */   
/*    */   public void close() {
/* 49 */     if (this.dataSource != null && !this.dataSource.isClosed()) {
/* 50 */       this.dataSource.close();
/* 51 */       System.out.println("Database connection pool closed.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\database\DatabaseConnection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */