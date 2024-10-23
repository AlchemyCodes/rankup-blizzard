/*    */ package blizzard.development.rankup.database.cache;
/*    */ 
/*    */ import blizzard.development.rankup.database.dao.PlayersDAO;
/*    */ import blizzard.development.rankup.database.storage.PlayersData;
/*    */ import blizzard.development.rankup.utils.PluginImpl;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PlayersCacheManager
/*    */ {
/* 13 */   public static final ConcurrentHashMap<Player, PlayersData> playerCache = new ConcurrentHashMap<>();
/*    */   public static PlayersDAO playersDAO;
/*    */   
/*    */   public static void cachePlayerData(Player player, PlayersData playerData) {
/* 17 */     playerCache.put(player, playerData);
/*    */   }
/*    */   
/*    */   public static PlayersData getPlayerData(Player player) {
/* 21 */     return playerCache.get(player);
/*    */   }
/*    */   
/*    */   public static void removePlayerData(Player player) {
/* 25 */     playerCache.remove(player);
/*    */   }
/*    */   
/*    */   public static void setNickname(Player player, String nickname) {
/* 29 */     PlayersData data = playerCache.get(player);
/* 30 */     if (data != null) {
/* 31 */       data.setNickname(nickname);
/*    */     } else {
/* 33 */       data = new PlayersData(player.getUniqueId().toString(), nickname, getRankWithMinOrder(), 0);
/*    */     } 
/* 35 */     playerCache.put(player, data);
/*    */   }
/*    */   
/*    */   public static String getRankWithMinOrder() {
/* 39 */     ConfigurationSection ranks = (PluginImpl.getInstance()).Config.getConfig().getConfigurationSection("ranks");
/* 40 */     String minRankName = null;
/*    */     
/* 42 */     assert ranks != null;
/* 43 */     for (String key : ranks.getKeys(false)) {
/* 44 */       int order = ranks.getInt(key + ".order");
/*    */       
/* 46 */       if (order == 1) {
/* 47 */         minRankName = ranks.getString(key + ".name");
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return minRankName;
/*    */   }
/*    */   
/*    */   public static Collection<PlayersData> getAllPlayersData() {
/* 55 */     return playerCache.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\database\cache\PlayersCacheManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */