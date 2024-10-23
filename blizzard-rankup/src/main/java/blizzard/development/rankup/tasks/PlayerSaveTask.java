/*    */ package blizzard.development.rankup.tasks;
/*    */ 
/*    */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.rankup.database.dao.PlayersDAO;
/*    */ import blizzard.development.rankup.database.storage.PlayersData;
/*    */ import java.sql.SQLException;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class PlayerSaveTask
/*    */   extends BukkitRunnable {
/*    */   public PlayerSaveTask(PlayersDAO playersDAO) {
/* 13 */     this.playersDAO = playersDAO;
/*    */   }
/*    */   private final PlayersDAO playersDAO;
/*    */   
/*    */   public void run() {
/* 18 */     PlayersCacheManager.playerCache.forEach((player, playersData) -> {
/*    */           try {
/*    */             this.playersDAO.updatePlayerData(playersData);
/* 21 */           } catch (SQLException e) {
/*    */             throw new RuntimeException(e);
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\tasks\PlayerSaveTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */