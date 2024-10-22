/*    */ package blizzard.development.core.managers;
/*    */ 
/*    */ import blizzard.development.core.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.core.utils.PluginImpl;
/*    */ import java.util.HashMap;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.boss.BarColor;
/*    */ import org.bukkit.boss.BarStyle;
/*    */ import org.bukkit.boss.BossBar;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ public class BossBarManager
/*    */ {
/* 17 */   private static final HashMap<UUID, BossBar> playerBossBars = new HashMap<>();
/*    */   
/*    */   public static void createBossBar(Player player) {
/* 20 */     BossBar bossBar = Bukkit.createBossBar("Temperatura: " + PlayersCacheManager.getTemperature(player) + " °C", BarColor.BLUE, BarStyle.SEGMENTED_6, new org.bukkit.boss.BarFlag[0]);
/*    */ 
/*    */     
/* 23 */     playerBossBars.put(player.getUniqueId(), bossBar);
/* 24 */     bossBar.addPlayer(player);
/* 25 */     startBossBarUpdate(player);
/*    */   }
/*    */   
/*    */   public static void removeBossBar(Player player) {
/* 29 */     BossBar bossBar = playerBossBars.remove(player.getUniqueId());
/* 30 */     if (bossBar != null) {
/* 31 */       bossBar.removePlayer(player);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void startBossBarUpdate(final Player player) {
/* 36 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 39 */           if (!player.isOnline()) {
/* 40 */             cancel();
/* 41 */             BossBarManager.removeBossBar(player);
/*    */             
/*    */             return;
/*    */           } 
/* 45 */           double temperature = PlayersCacheManager.getTemperature(player);
/* 46 */           BossBarManager.updateBossBar(player, temperature);
/*    */         }
/* 48 */       }).runTaskTimer((PluginImpl.getInstance()).plugin, 0L, 60L);
/*    */   }
/*    */   
/*    */   private static void updateBossBar(Player player, double temperature) {
/* 52 */     BossBar bossBar = playerBossBars.get(player.getUniqueId());
/*    */     
/* 54 */     if (bossBar == null)
/*    */       return; 
/* 56 */     double progress = Math.min(Math.max(temperature / 20.0D, 0.0D), 1.0D);
/* 57 */     bossBar.setProgress(progress);
/*    */     
/* 59 */     if (temperature <= 0.0D) {
/* 60 */       bossBar.setTitle("§b❄❄❄ Você está congelando! ❄❄❄");
/*    */     } else {
/* 62 */       bossBar.setTitle("§bTemperatura: §b§l" + temperature + " §b°C");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\managers\BossBarManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */