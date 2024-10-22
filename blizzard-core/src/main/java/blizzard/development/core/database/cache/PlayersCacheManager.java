/*    */ package blizzard.development.core.database.cache;
/*    */ 
/*    */ import blizzard.development.core.clothing.ClothingType;
/*    */ import blizzard.development.core.database.dao.PlayersDAO;
/*    */ import blizzard.development.core.database.storage.PlayersData;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PlayersCacheManager
/*    */ {
/* 11 */   public static final ConcurrentHashMap<String, PlayersData> playersCache = new ConcurrentHashMap<>();
/* 12 */   private static final PlayersDAO playersDAO = new PlayersDAO();
/*    */   
/*    */   public static PlayersData getPlayerData(Player player) {
/* 15 */     return getPlayerDataByName(player.getName());
/*    */   }
/*    */   
/*    */   public static void cachePlayerData(Player player, PlayersData playerData) {
/* 19 */     playersCache.put(player.getName(), playerData);
/*    */   }
/*    */   
/*    */   public static void cachePlayerDataByName(String player, PlayersData playerData) {
/* 23 */     playersCache.put(player, playerData);
/*    */   }
/*    */   
/*    */   public static PlayersData getPlayerDataByName(String playerName) {
/* 27 */     PlayersData data = playersCache.get(playerName);
/*    */     
/* 29 */     if (data == null) {
/* 30 */       data = playersDAO.findPlayerDataByName(playerName);
/* 31 */       if (data != null) {
/* 32 */         playersCache.put(playerName, data);
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return data;
/*    */   }
/*    */   
/*    */   public static void setTemperature(Player player, double temperature) {
/* 40 */     PlayersData data = getPlayerData(player);
/* 41 */     if (data != null) {
/* 42 */       data.setTemperature(temperature);
/* 43 */       playersCache.put(player.getName(), data);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static double getTemperature(Player player) {
/* 49 */     PlayersData data = getPlayerData(player);
/* 50 */     if (data != null) {
/* 51 */       return data.getTemperature();
/*    */     }
/* 53 */     return 0.0D;
/*    */   }
/*    */   
/*    */   public static void setPlayerClothing(Player player, ClothingType clothingType) {
/* 57 */     PlayersData data = getPlayerData(player);
/*    */     
/* 59 */     if (data != null) {
/* 60 */       data.setClothingType(clothingType);
/* 61 */       playersCache.put(player.getName(), data);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getPlayerClothing(Player player) {
/* 66 */     PlayersData data = getPlayerData(player);
/*    */     
/* 68 */     if (data != null) {
/* 69 */       return data.getClothingType().toString();
/*    */     }
/*    */     
/* 72 */     return "Inválido";
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\database\cache\PlayersCacheManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */