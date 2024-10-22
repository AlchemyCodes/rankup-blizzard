/*     */ package blizzard.development.core.database.dao;
/*     */ import blizzard.development.core.database.DatabaseConnection;
/*     */ import blizzard.development.core.database.storage.PlayersData;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ 
/*     */ public class PlayersDAO {
/*     */   public void initializeDatabase() {
/*     */     
/*  13 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  14 */       try { Statement stat = conn.createStatement();
/*     */         
/*  16 */         try { String sql_player = "CREATE TABLE IF NOT EXISTS player_core (uuid varchar(36) primary key, nickname varchar(36), temperature double, clothing varchar(36))";
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  21 */           stat.execute(sql_player);
/*     */           
/*  23 */           if (stat != null) stat.close();  } catch (Throwable throwable) { if (stat != null) try { stat.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  24 */     { e.printStackTrace(); }
/*     */   
/*     */   }
/*     */   private void executeUpdate(String sql, Consumer<PreparedStatement> setter) throws SQLException {
/*     */     
/*  29 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  30 */       try { PreparedStatement statement = conn.prepareStatement(sql); 
/*  31 */         try { setter.accept(statement);
/*  32 */           statement.executeUpdate();
/*  33 */           if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  34 */     { System.out.println("Database error: " + e.getMessage());
/*  35 */       throw e; }
/*     */   
/*     */   }
/*     */   
/*     */   public PlayersData findPlayerData(String uuid) {
/*  40 */     String sqlpar = "SELECT * FROM player_core WHERE uuid = ?"; 
/*  41 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  42 */       try { PreparedStatement statement = conn.prepareStatement(sqlpar); 
/*  43 */         try { statement.setString(1, uuid);
/*     */           
/*  45 */           ResultSet resultSet = statement.executeQuery(); 
/*  46 */           try { if (resultSet.next())
/*     */             
/*     */             { 
/*     */ 
/*     */               
/*  51 */               PlayersData playersData = new PlayersData(resultSet.getString("uuid"), resultSet.getString("nickname"), resultSet.getDouble("temperature"), ClothingType.valueOf(resultSet.getString("clothing")));
/*     */               
/*  53 */               if (resultSet != null) resultSet.close(); 
/*  54 */               if (statement != null) statement.close();  if (conn != null) conn.close();  return playersData; }  if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  55 */     { System.out.println("Failed to find player data: " + e); }
/*     */     
/*  57 */     return null;
/*     */   }
/*     */   
/*     */   public PlayersData findPlayerDataByName(String player) {
/*  61 */     String sql = "SELECT * FROM player_core WHERE nickname = ?"; 
/*  62 */     try { Connection conn = DatabaseConnection.getInstance().getConnection(); 
/*  63 */       try { PreparedStatement statement = conn.prepareStatement(sql); 
/*  64 */         try { statement.setString(1, player);
/*     */           
/*  66 */           ResultSet resultSet = statement.executeQuery(); 
/*  67 */           try { if (resultSet.next())
/*     */             
/*     */             { 
/*     */ 
/*     */               
/*  72 */               PlayersData playersData = new PlayersData(resultSet.getString("uuid"), resultSet.getString("nickname"), resultSet.getDouble("temperature"), ClothingType.valueOf(resultSet.getString("clothing")));
/*     */               
/*  74 */               if (resultSet != null) resultSet.close(); 
/*  75 */               if (statement != null) statement.close();  if (conn != null) conn.close();  return playersData; }  if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null) try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (conn != null) conn.close();  } catch (Throwable throwable) { if (conn != null) try { conn.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/*  76 */     { System.out.println("Failed to find player data by name: " + e); }
/*     */     
/*  78 */     return null;
/*     */   }
/*     */   
/*     */   public void createPlayerData(PlayersData playerData) throws SQLException {
/*  82 */     String sqlpar = "INSERT INTO player_core (uuid, nickname, temperature, clothing) VALUES (?, ?, ?, ?)";
/*  83 */     executeUpdate(sqlpar, statement -> {
/*     */           try {
/*     */             statement.setString(1, playerData.getUuid());
/*     */             statement.setString(2, playerData.getNickname());
/*     */             statement.setDouble(3, playerData.getTemperature());
/*     */             statement.setString(4, playerData.getClothingType().name());
/*  89 */           } catch (SQLException e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void deletePlayerData(PlayersData playerData) throws SQLException {
/*  96 */     String sqlpar = "DELETE FROM player_core WHERE uuid = ?";
/*  97 */     executeUpdate(sqlpar, statement -> {
/*     */           try {
/*     */             statement.setString(1, playerData.getUuid());
/* 100 */           } catch (SQLException e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void updatePlayerData(PlayersData playerData) throws SQLException {
/* 107 */     String sqlpar = "UPDATE player_core SET nickname = ?, temperature = ?, clothing = ? WHERE uuid = ?";
/* 108 */     executeUpdate(sqlpar, statement -> {
/*     */           try {
/*     */             statement.setString(1, playerData.getNickname());
/*     */             statement.setDouble(2, playerData.getTemperature());
/*     */             statement.setString(3, playerData.getClothingType().name());
/*     */             statement.setString(4, playerData.getUuid());
/* 114 */           } catch (SQLException e) {
/*     */             throw new RuntimeException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\database\dao\PlayersDAO.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */