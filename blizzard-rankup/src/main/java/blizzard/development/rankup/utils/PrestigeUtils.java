/*    */ package blizzard.development.rankup.utils;
/*    */ 
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class PrestigeUtils
/*    */ {
/*    */   public static double prestigePrice(int prestigeLevel) {
/*  8 */     YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();
/*    */     
/* 10 */     double prestigePrice = prestigeConfig.getDouble("prestige.price");
/*    */     
/* 12 */     return prestigePrice * (prestigeLevel + 1);
/*    */   }
/*    */   
/*    */   public static double prestigeCostAdd(int prestigeLevel) {
/* 16 */     YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();
/*    */     
/* 18 */     double costAdd = prestigeConfig.getDouble("prestige.cost-add");
/*    */     
/* 20 */     if (prestigeLevel == 0) {
/* 21 */       return 1.0D;
/*    */     }
/*    */     
/* 24 */     return costAdd * prestigeLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\PrestigeUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */