/*    */ package blizzard.development.core.tasks;
/*    */ 
/*    */ import blizzard.development.core.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.core.database.dao.PlayersDAO;
/*    */ import blizzard.development.core.database.storage.PlayersData;
/*    */ import java.sql.SQLException;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class PlayerSaveTask extends BukkitRunnable {
/*    */   private final PlayersDAO playersDAO;
/*    */   
/*    */   public PlayerSaveTask(PlayersDAO playersDAO) {
/* 13 */     this.playersDAO = playersDAO;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 18 */     PlayersCacheManager.playersCache.forEach((player, playersData) -> {
/*    */           try {
/*    */             this.playersDAO.updatePlayerData(playersData);
/* 21 */           } catch (SQLException e) {
/*    */             throw new RuntimeException(e);
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\tasks\PlayerSaveTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */