/*     */ package blizzard.development.rankup.database.dao;
/*     */ import blizzard.development.rankup.database.DatabaseConnection;
/*     */ import blizzard.development.rankup.database.storage.PlayersData;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ public class PlayersDAO {
/*     */   public void initializeDatabase() {
/*     */     
/*  15 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  16 */       try { Statement stat = conn.createStatement();
/*     */         
/*  18 */         try { String sql_player = "CREATE TABLE IF NOT EXISTS rankup_users (uuid varchar(36) primary key, nickname varchar(36), rank varchar(50), prestige INT DEFAULT 0)";
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  23 */           stat.execute(sql_player);
/*     */           
/*  25 */           if (stat != null) stat.close();  } catch (Throwable throwable) { if (stat != null) try { stat.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  26 */     { e.printStackTrace(); }
/*     */   
/*     */   }
/*     */   private void executeUpdate(String sql, Consumer<PreparedStatement> setter) throws SQLException {
/*     */     
/*  31 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  32 */       try { PreparedStatement statement = conn.prepareStatement(sql); 
/*  33 */         try { setter.accept(statement);
/*  34 */           statement.executeUpdate();
/*  35 */           if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  36 */     { System.out.println("Database error: " + e.getMessage());
/*  37 */       throw e; }
/*     */   
/*     */   }
/*     */   
/*     */   public PlayersData findPlayerData(String uuid) {
/*  42 */     String sql = "SELECT * FROM rankup_users WHERE uuid = ?"; 
/*  43 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  44 */       try { PreparedStatement statement = conn.prepareStatement(sql); 
/*  45 */         try { statement.setString(1, uuid);
/*     */           
/*  47 */           ResultSet resultSet = statement.executeQuery(); 
/*  48 */           try { if (resultSet.next())
/*     */             
/*     */             { 
/*     */ 
/*     */               
/*  53 */               PlayersData playersData = new PlayersData(resultSet.getString("uuid"), resultSet.getString("nickname"), resultSet.getString("rank"), resultSet.getInt("prestige"));
/*     */               
/*  55 */               if (resultSet != null) resultSet.close(); 
/*  56 */               if (statement != null) statement.close();  if (conn != null) conn.close();  return playersData; }  if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  57 */     { System.out.println("Failed to find player data: " + e); }
/*     */     
/*  59 */     return null;
/*     */   }
/*     */   
/*     */   public PlayersData findPlayerDataByNickname(String nickname) {
/*  63 */     String sql = "SELECT * FROM rankup_users WHERE nickname = ?"; 
/*  64 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  65 */       try { PreparedStatement statement = conn.prepareStatement(sql); 
/*  66 */         try { statement.setString(1, nickname);
/*     */           
/*  68 */           ResultSet resultSet = statement.executeQuery(); 
/*  69 */           try { if (resultSet.next())
/*     */             
/*     */             { 
/*     */ 
/*     */               
/*  74 */               PlayersData playersData = new PlayersData(resultSet.getString("uuid"), resultSet.getString("nickname"), resultSet.getString("rank"), resultSet.getInt("prestige"));
/*     */               
/*  76 */               if (resultSet != null) resultSet.close(); 
/*  77 */               if (statement != null) statement.close();  if (conn != null) conn.close();  return playersData; }  if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  78 */     { System.out.println("Failed to find player data: " + e); }
/*     */     
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public void createPlayerData(PlayersData playerData) throws SQLException {
/*  84 */     String sql = "INSERT INTO rankup_users (uuid, nickname, rank, prestige) VALUES (?, ?, ?, ?)";
/*  85 */     executeUpdate(sql, statement -> {
/*     */           try {
/*     */             statement.setString(1, playerData.getUuid());
/*     */             statement.setString(2, playerData.getNickname());
/*     */             statement.setString(3, playerData.getRank());
/*     */             statement.setInt(4, playerData.getPrestige());
/*  91 */           } catch (SQLException e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void deletePlayerData(PlayersData playerData) throws SQLException {
/*  98 */     String sql = "DELETE FROM rankup_users WHERE uuid = ?";
/*  99 */     executeUpdate(sql, statement -> {
/*     */           try {
/*     */             statement.setString(1, playerData.getUuid());
/* 102 */           } catch (SQLException e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void updatePlayerData(PlayersData playerData) throws SQLException {
/* 109 */     String sql = "UPDATE rankup_users SET nickname = ?, rank = ?, prestige = ? WHERE uuid = ?";
/* 110 */     executeUpdate(sql, statement -> {
/*     */           try {
/*     */             statement.setString(1, playerData.getNickname());
/*     */             statement.setString(2, playerData.getRank());
/*     */             statement.setInt(3, playerData.getPrestige());
/*     */             statement.setString(4, playerData.getUuid());
/* 116 */           } catch (SQLException e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public List<PlayersData> getAllPlayerData() throws SQLException {
/* 123 */     String sql = "SELECT * FROM rankup_users";
/* 124 */     List<PlayersData> playersDataList = new ArrayList<>();
/*     */     
/* 126 */     Connection connection = DatabaseConnection.getInstance().getConnection(); 
/* 127 */     try { Statement statement = connection.createStatement(); 
/* 128 */       try { ResultSet resultSet = statement.executeQuery(sql);
/*     */         
/* 130 */         try { while (resultSet.next()) {
/* 131 */             playersDataList.add(new PlayersData(resultSet
/* 132 */                   .getString("uuid"), resultSet
/* 133 */                   .getString("nickname"), resultSet
/* 134 */                   .getString("rank"), resultSet
/* 135 */                   .getInt("prestige")));
/*     */           }
/* 137 */           if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (connection != null) connection.close();  } catch (Throwable throwable) { if (connection != null)
/* 138 */         try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return playersDataList;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\database\dao\PlayersDAO.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */