/*    */ package blizzard.development.rankup;
/*    */ 
/*    */ import blizzard.development.rankup.utils.PluginImpl;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class Main extends JavaPlugin {
/*    */   public PluginImpl pluginImpl;
/*    */   public static Main instance;
/*    */   
/*    */   public void onEnable() {
/* 12 */     instance = this;
/*    */     
/* 14 */     this.pluginImpl = new PluginImpl((Plugin)this);
/* 15 */     this.pluginImpl.onLoad();
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 19 */     this.pluginImpl.onUnload();
/*    */   }
/*    */   
/*    */   public static Main getInstance() {
/* 23 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\Main.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */