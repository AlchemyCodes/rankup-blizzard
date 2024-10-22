/*    */ package blizzard.development.core;
/*    */ 
/*    */ import blizzard.development.core.utils.PluginImpl;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class Main
/*    */   extends JavaPlugin {
/*    */   public PluginImpl pluginImpl;
/*    */   public static Main instance;
/*    */   
/*    */   public void onEnable() {
/* 13 */     instance = this;
/*    */     
/* 15 */     this.pluginImpl = new PluginImpl((Plugin)this);
/* 16 */     this.pluginImpl.onLoad();
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 20 */     this.pluginImpl.onUnload();
/*    */   }
/*    */   
/*    */   public static Main getInstance() {
/* 24 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\Main.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */