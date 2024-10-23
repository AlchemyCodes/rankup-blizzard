/*    */ package blizzard.development.rankup.listeners;
/*    */ 
/*    */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.rankup.database.dao.PlayersDAO;
/*    */ import blizzard.development.rankup.database.storage.PlayersData;
/*    */ import blizzard.development.rankup.utils.RanksUtils;
/*    */ import java.sql.SQLException;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public final class TrafficListener
/*    */   implements Listener
/*    */ {
/*    */   private final PlayersDAO playersDAO;
/*    */   
/*    */   public TrafficListener(PlayersDAO playersDAO) {
/* 20 */     this.playersDAO = playersDAO;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onQuit(PlayerQuitEvent event) {}
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent event) throws SQLException {
/* 30 */     Player player = event.getPlayer();
/* 31 */     String name = player.getName();
/* 32 */     String uuid = player.getUniqueId().toString();
/*    */     
/* 34 */     PlayersData playersData = this.playersDAO.findPlayerData(uuid);
/*    */     
/* 36 */     if (playersData == null) {
/* 37 */       playersData = new PlayersData(uuid, name, RanksUtils.getRankWithMinOrder(), 0);
/* 38 */       this.playersDAO.createPlayerData(playersData);
/*    */     } 
/*    */     
/* 41 */     PlayersCacheManager.cachePlayerData(player, playersData);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\listeners\TrafficListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */