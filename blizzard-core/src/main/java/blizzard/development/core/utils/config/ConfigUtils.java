/*     */ package blizzard.development.core.utils.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class ConfigUtils
/*     */ {
/*     */   public ConfigUtils(JavaPlugin plugin, String name) {
/*  12 */     this.plugin = plugin;
/*  13 */     setName(name);
/*  14 */     reloadConfig();
/*     */   }
/*     */   private JavaPlugin plugin;
/*     */   private String name;
/*     */   private File file;
/*     */   private YamlConfiguration config;
/*     */   
/*     */   public JavaPlugin getPlugin() {
/*  22 */     return this.plugin;
/*     */   }
/*     */   
/*     */   public void setPlugin(JavaPlugin plugin) {
/*  26 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  30 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  34 */     this.name = name;
/*     */   }
/*     */   
/*     */   public File getFile() {
/*  38 */     return this.file;
/*     */   }
/*     */   
/*     */   public YamlConfiguration getConfig() {
/*  42 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveConfig() {
/*     */     try {
/*  49 */       getConfig().save(getFile());
/*  50 */     } catch (IOException e) {
/*  51 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveDefault() {
/*  56 */     getConfig().options().copyDefaults(true);
/*     */   }
/*     */   
/*     */   public void saveDefaultConfig() {
/*  60 */     getPlugin().saveResource(getName(), false);
/*     */   }
/*     */   
/*     */   public void reloadConfig() {
/*  64 */     this.file = new File(getPlugin().getDataFolder(), getName());
/*  65 */     this.config = YamlConfiguration.loadConfiguration(getFile());
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteConfig() {
/*  70 */     getFile().delete();
/*     */   }
/*     */   
/*     */   public boolean existsConfig() {
/*  74 */     return getFile().exists();
/*     */   }
/*     */   
/*     */   public String getString(String path) {
/*  78 */     return getConfig().getString(path);
/*     */   }
/*     */   
/*     */   public int getInt(String path) {
/*  82 */     return getConfig().getInt(path);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String path) {
/*  86 */     return getConfig().getBoolean(path);
/*     */   }
/*     */   
/*     */   public double getDouble(String path) {
/*  90 */     return getConfig().getDouble(path);
/*     */   }
/*     */   
/*     */   public List<?> getList(String path) {
/*  94 */     return getConfig().getList(path);
/*     */   }
/*     */   
/*     */   public boolean contains(String path) {
/*  98 */     return getConfig().contains(path);
/*     */   }
/*     */   
/*     */   public void set(String path, Object value) {
/* 102 */     getConfig().set(path, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\cor\\utils\config\ConfigUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */