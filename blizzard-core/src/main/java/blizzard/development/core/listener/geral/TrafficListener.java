/*    */ package blizzard.development.core.listener.geral;
/*    */ 
/*    */ import blizzard.development.core.clothing.ClothingType;
/*    */ import blizzard.development.core.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.core.database.dao.PlayersDAO;
/*    */ import blizzard.development.core.database.storage.PlayersData;
/*    */ import blizzard.development.core.managers.BossBarManager;
/*    */ import blizzard.development.core.tasks.TemperatureDecayTask;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class TrafficListener
/*    */   implements Listener {
/*    */   private final PlayersDAO database;
/*    */   
/*    */   public TrafficListener(PlayersDAO database) {
/* 20 */     this.database = database;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent event) {
/* 25 */     Player player = event.getPlayer();
/*    */     
/* 27 */     PlayersData playersData = this.database.findPlayerData(player.getUniqueId().toString());
/*    */     
/* 29 */     if (playersData == null) {
/* 30 */       playersData = new PlayersData(player.getUniqueId().toString(), player.getName(), 10.0D, ClothingType.COMMON);
/*    */       try {
/* 32 */         this.database.createPlayerData(playersData);
/* 33 */       } catch (Exception err) {
/* 34 */         err.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 38 */     PlayersCacheManager.cachePlayerData(player, playersData);
/* 39 */     BossBarManager.createBossBar(player);
/* 40 */     TemperatureDecayTask.startPlayerRunnable(player);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onQuit(PlayerQuitEvent event) {
/* 45 */     Player player = event.getPlayer();
/*    */     
/* 47 */     BossBarManager.removeBossBar(player);
/* 48 */     TemperatureDecayTask.stopPlayerRunnable(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\listener\geral\TrafficListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */