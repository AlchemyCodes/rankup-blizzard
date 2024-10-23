/*     */ package blizzard.development.rankup.utils;
/*     */ 
/*     */ import blizzard.development.essentials.acf.BaseCommand;
/*     */ import blizzard.development.essentials.acf.Locales;
/*     */ import blizzard.development.essentials.acf.PaperCommandManager;
/*     */ import blizzard.development.rankup.commands.PrestigeCommand;
/*     */ import blizzard.development.rankup.commands.RankCommand;
/*     */ import blizzard.development.rankup.commands.RankUpCommand;
/*     */ import blizzard.development.rankup.commands.RanksCommand;
/*     */ import blizzard.development.rankup.database.DatabaseConnection;
/*     */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*     */ import blizzard.development.rankup.database.dao.PlayersDAO;
/*     */ import blizzard.development.rankup.database.storage.PlayersData;
/*     */ import blizzard.development.rankup.listeners.TrafficListener;
/*     */ import blizzard.development.rankup.tasks.PlayerSaveTask;
/*     */ import java.io.File;
/*     */ import java.sql.SQLException;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ public class PluginImpl
/*     */ {
/*     */   public PlayersDAO playersDAO;
/*     */   public final Plugin plugin;
/*     */   private static PluginImpl instance;
/*     */   private static PaperCommandManager commandManager;
/*     */   private static PluginManager pluginManager;
/*     */   public ConfigUtils Database;
/*     */   public ConfigUtils Config;
/*     */   public ConfigUtils Messages;
/*     */   public ConfigUtils Ranks;
/*     */   public ConfigUtils Prestige;
/*     */   public ConfigUtils Inventories;
/*     */   
/*     */   public PluginImpl(Plugin plugin) {
/*  41 */     this.plugin = plugin;
/*  42 */     instance = this;
/*  43 */     commandManager = new PaperCommandManager(plugin);
/*     */     
/*  45 */     commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
/*  46 */     pluginManager = Bukkit.getPluginManager();
/*     */     
/*  48 */     this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
/*  49 */     this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
/*  50 */     this.Messages = new ConfigUtils((JavaPlugin)plugin, "messages.yml");
/*  51 */     this.Ranks = new ConfigUtils((JavaPlugin)plugin, "ranks.yml");
/*  52 */     this.Prestige = new ConfigUtils((JavaPlugin)plugin, "prestige.yml");
/*  53 */     this.Inventories = new ConfigUtils((JavaPlugin)plugin, "inventories.yml");
/*     */   }
/*     */   
/*     */   public void onLoad() {
/*  57 */     loadConfigs();
/*  58 */     registerDatabase();
/*  59 */     registerCommands();
/*  60 */     registerListeners();
/*  61 */     registerTasks();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfigs() {
/*  67 */     if (!this.Database.existsConfig()) {
/*  68 */       this.Database.saveDefaultConfig();
/*     */     }
/*  70 */     if (!this.Config.existsConfig()) {
/*  71 */       this.Config.saveDefaultConfig();
/*     */     }
/*  73 */     if (!this.Messages.existsConfig()) {
/*  74 */       this.Messages.saveDefaultConfig();
/*     */     }
/*  76 */     if (!this.Ranks.existsConfig()) {
/*  77 */       this.Ranks.saveDefaultConfig();
/*     */     }
/*  79 */     if (!this.Prestige.existsConfig()) {
/*  80 */       this.Prestige.saveDefaultConfig();
/*     */     }
/*  82 */     if (!this.Inventories.existsConfig()) {
/*  83 */       this.Inventories.saveDefaultConfig();
/*     */     }
/*     */     
/*  86 */     this.Database.reloadConfig();
/*  87 */     this.Config.reloadConfig();
/*  88 */     this.Messages.reloadConfig();
/*  89 */     this.Ranks.reloadConfig();
/*  90 */     this.Prestige.reloadConfig();
/*  91 */     this.Inventories.reloadConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnload() {
/*  96 */     PlayersCacheManager.playerCache.forEach((player, playersData) -> {
/*     */           try {
/*     */             this.playersDAO.updatePlayerData(playersData);
/*  99 */           } catch (SQLException exception) {
/*     */             throw new RuntimeException(exception);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDatabase() {
/* 108 */     DatabaseConnection.getInstance();
/* 109 */     this.playersDAO = new PlayersDAO();
/* 110 */     this.playersDAO.initializeDatabase();
/*     */     
/* 112 */     (new PlayerSaveTask(this.playersDAO)).runTaskTimerAsynchronously(this.plugin, 0L, 60L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerTasks() {}
/*     */ 
/*     */   
/*     */   private void registerListeners() {
/* 120 */     pluginManager.registerEvents((Listener)new TrafficListener(this.playersDAO), this.plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerCommands() {
/* 125 */     commandManager.registerCommand((BaseCommand)new RankCommand());
/* 126 */     commandManager.registerCommand((BaseCommand)new RanksCommand());
/* 127 */     commandManager.registerCommand((BaseCommand)new RankUpCommand());
/* 128 */     commandManager.registerCommand((BaseCommand)new PrestigeCommand());
/*     */   }
/*     */   
/*     */   public static PluginImpl getInstance() {
/* 132 */     return instance;
/*     */   }
/*     */   
/*     */   public File getDataFolder() {
/* 136 */     return this.plugin.getDataFolder();
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\PluginImpl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */