/*    */ package blizzard.development.core.tasks;
/*    */ 
/*    */ import blizzard.development.core.clothing.ClothingType;
/*    */ import blizzard.development.core.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.core.managers.CampfireManager;
/*    */ import blizzard.development.core.utils.PluginImpl;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class TemperatureDecayTask
/*    */ {
/* 17 */   public static Map<UUID, Integer> playerTasks = new HashMap<>();
/*    */   
/*    */   public static void startPlayerRunnable(Player player) {
/* 20 */     UUID playerUUID = player.getUniqueId();
/* 21 */     YamlConfiguration config = (PluginImpl.getInstance()).Config.getConfig();
/*    */ 
/*    */     
/* 24 */     int taskId = Bukkit.getScheduler().runTaskTimer((PluginImpl.getInstance()).plugin, () -> decayTemperature(player, config), 0L, 20L * getTime(player, config)).getTaskId();
/*    */     
/* 26 */     playerTasks.put(playerUUID, Integer.valueOf(taskId));
/*    */   }
/*    */   
/*    */   public static void stopPlayerRunnable(Player player) {
/* 30 */     UUID playerUUID = player.getUniqueId();
/* 31 */     if (playerTasks.containsKey(playerUUID)) {
/* 32 */       Bukkit.getScheduler().cancelTask(((Integer)playerTasks.get(playerUUID)).intValue());
/* 33 */       playerTasks.remove(playerUUID);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void decayTemperature(Player player, YamlConfiguration config) {
/* 39 */     double temperatureDecay = config.getDouble("temperature.temperatureDecay");
/* 40 */     double playerTemperature = PlayersCacheManager.getTemperature(player);
/* 41 */     int freezing = config.getInt("temperature.freezingTemperature");
/*    */     
/* 43 */     if (!BlizzardTask.isSnowing && playerTemperature <= 5.0D)
/*    */       return; 
/* 45 */     if (CampfireManager.hasCampfire(player)) {
/* 46 */       if (!BlizzardTask.isSnowing && playerTemperature >= 5.0D)
/*    */         return; 
/* 48 */       PlayersCacheManager.setTemperature(player, playerTemperature + temperatureDecay);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 53 */     if (BlizzardTask.isSnowing && playerTemperature <= freezing) {
/* 54 */       PlayersCacheManager.setTemperature(player, freezing);
/*    */       
/* 56 */       player.damage(config.getDouble("temperature.damage"));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 61 */     PlayersCacheManager.setTemperature(player, playerTemperature - temperatureDecay);
/*    */   }
/*    */   
/*    */   public static long getTime(Player player, YamlConfiguration config) {
/* 65 */     double timeToDecay = config.getDouble("temperature.timeToDecay");
/* 66 */     String playerClothing = PlayersCacheManager.getPlayerClothing(player);
/*    */     
/* 68 */     switch (ClothingType.valueOf(playerClothing)) { case COMMON: 
/*    */       case RARE: 
/*    */       case MYSTIC: 
/*    */       case LEGENDARY: 
/*    */       default:
/* 73 */         break; }  long time = (long)timeToDecay;
/*    */ 
/*    */     
/* 76 */     if (BlizzardTask.isSnowing) {
/* 77 */       time /= 2L;
/* 78 */       return time;
/*    */     } 
/*    */     
/* 81 */     return time;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\tasks\TemperatureDecayTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */