/*    */ package blizzard.development.core.tasks;
/*    */ 
/*    */ import blizzard.development.core.managers.CampfireManager;
/*    */ import blizzard.development.core.utils.PluginImpl;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlizzardTask
/*    */   implements Runnable
/*    */ {
/* 18 */   private final Plugin plugin = (PluginImpl.getInstance()).plugin;
/* 19 */   private final YamlConfiguration config = (PluginImpl.getInstance()).Config.getConfig(); public static boolean isSnowing;
/*    */   public BlizzardTask() {
/* 21 */     int time = this.config.getInt("blizzard.time");
/*    */     
/* 23 */     this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this, 20L * time, 20L * time);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 28 */     int duration = this.config.getInt("blizzard.duration");
/*    */     
/* 30 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 31 */       World world = player.getWorld();
/*    */       
/* 33 */       world.setStorm(true);
/* 34 */       isSnowing = true;
/* 35 */       player.sendTitle("§b§lO FRIO CHEGOU.", "§fProteja-se, a tempestade de neve chegou.", 10, 70, 20);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       Bukkit.getScheduler().runTaskLater(this.plugin, () -> { world.setStorm(false); isSnowing = false; CampfireManager.removeCampfire(player); player.sendTitle("§b§lA TEMPESTADE PASSOU.", "§fAproveite a calmaria antes que ela volte.", 10, 70, 20); }20L * duration);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\tasks\BlizzardTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */