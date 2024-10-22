/*    */ package blizzard.development.core.utils;
/*    */ 
/*    */ import blizzard.development.core.commands.CommandRegistry;
/*    */ import blizzard.development.core.database.DatabaseConnection;
/*    */ import blizzard.development.core.database.dao.PlayersDAO;
/*    */ import blizzard.development.core.listener.ListenerRegistry;
/*    */ import blizzard.development.core.tasks.PlayerSaveTask;
/*    */ import blizzard.development.core.utils.config.ConfigUtils;
/*    */ import blizzard.development.essentials.acf.Locales;
/*    */ import blizzard.development.essentials.acf.PaperCommandManager;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginImpl
/*    */ {
/*    */   public final Plugin plugin;
/*    */   private static PluginImpl instance;
/*    */   private static PluginManager pluginManager;
/*    */   private static PaperCommandManager commandManager;
/*    */   private static PlayersDAO playersDAO;
/*    */   public ConfigUtils Config;
/*    */   public ConfigUtils Database;
/*    */   
/*    */   public PluginImpl(Plugin plugin) {
/* 32 */     this.plugin = plugin;
/* 33 */     instance = this;
/* 34 */     commandManager = new PaperCommandManager(plugin);
/* 35 */     pluginManager = Bukkit.getPluginManager();
/* 36 */     commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
/*    */     
/* 38 */     playersDAO = new PlayersDAO();
/*    */     
/* 40 */     this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
/* 41 */     this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
/*    */   }
/*    */   
/*    */   public void onLoad() {
/* 45 */     this.Config.saveDefaultConfig();
/* 46 */     this.Database.saveDefaultConfig();
/* 47 */     registerDatabase();
/* 48 */     registerListeners();
/* 49 */     registerTasks();
/* 50 */     registerCommands();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUnload() {}
/*    */   
/*    */   public void registerDatabase() {
/* 57 */     DatabaseConnection.getInstance();
/* 58 */     playersDAO = new PlayersDAO();
/* 59 */     playersDAO.initializeDatabase();
/*    */     
/* 61 */     (new PlayerSaveTask(playersDAO)).runTaskTimerAsynchronously(this.plugin, 0L, 60L);
/*    */   }
/*    */   
/*    */   private void registerListeners() {
/* 65 */     ListenerRegistry listenerRegistry = new ListenerRegistry(playersDAO);
/* 66 */     listenerRegistry.register();
/*    */   }
/*    */ 
/*    */   
/*    */   private void registerTasks() {}
/*    */ 
/*    */   
/*    */   private void registerCommands() {
/* 74 */     CommandRegistry commandRegistry = new CommandRegistry();
/* 75 */     commandRegistry.register();
/*    */   }
/*    */   
/*    */   public static PluginImpl getInstance() {
/* 79 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\cor\\utils\PluginImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */