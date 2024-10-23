/*     */ package blizzard.development.rankup.utils;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ public class RanksUtils
/*     */ {
/*     */   public static ConfigurationSection getCurrentRankSection(YamlConfiguration ranksConfig, String currentRank) {
/*  12 */     Set<String> ranks = ((ConfigurationSection)Objects.<ConfigurationSection>requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
/*     */     
/*  14 */     for (String rankKey : ranks) {
/*  15 */       ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
/*     */       
/*  17 */       assert rankSection != null;
/*  18 */       String rankName = rankSection.getString("name");
/*     */       
/*  20 */       if (Objects.equals(rankName, currentRank)) {
/*  21 */         return rankSection;
/*     */       }
/*     */     } 
/*  24 */     return null;
/*     */   }
/*     */   
/*     */   public static String getCurrentRank(YamlConfiguration ranksConfig, String currentRank) {
/*  28 */     Set<String> ranks = ((ConfigurationSection)Objects.<ConfigurationSection>requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
/*     */     
/*  30 */     for (String rankKey : ranks) {
/*  31 */       ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
/*     */       
/*  33 */       assert rankSection != null;
/*  34 */       String rankName = rankSection.getString("name");
/*     */       
/*  36 */       if (Objects.equals(rankName, currentRank)) {
/*  37 */         return rankSection.getString("name");
/*     */       }
/*     */     } 
/*  40 */     return null;
/*     */   }
/*     */   
/*     */   public static ConfigurationSection getNextRankSection(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
/*  44 */     Set<String> ranks = ((ConfigurationSection)Objects.<ConfigurationSection>requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
/*     */     
/*  46 */     int currentOrder = currentRankSection.getInt("order");
/*     */     
/*  48 */     for (String rankKey : ranks) {
/*  49 */       ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
/*     */       
/*  51 */       assert rankSection != null;
/*  52 */       int rankOrder = rankSection.getInt("order");
/*     */       
/*  54 */       if (rankOrder == currentOrder + 1) {
/*  55 */         return rankSection;
/*     */       }
/*     */     } 
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   public static String getNextRank(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
/*  62 */     Set<String> ranks = ((ConfigurationSection)Objects.<ConfigurationSection>requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
/*     */     
/*  64 */     int currentOrder = currentRankSection.getInt("order");
/*     */     
/*  66 */     for (String rankKey : ranks) {
/*  67 */       ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
/*     */       
/*  69 */       assert rankSection != null;
/*  70 */       int rankOrder = rankSection.getInt("order");
/*     */       
/*  72 */       if (rankOrder == currentOrder + 1) {
/*  73 */         return rankSection.getString("name");
/*     */       }
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public static int getCurrentOrder(YamlConfiguration ranksConfig, String currentRank) {
/*  80 */     Set<String> ranks = ((ConfigurationSection)Objects.<ConfigurationSection>requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
/*     */     
/*  82 */     for (String rankKey : ranks) {
/*  83 */       ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
/*     */       
/*  85 */       assert rankSection != null;
/*  86 */       String rankName = rankSection.getString("name");
/*     */       
/*  88 */       if (Objects.equals(rankName, currentRank)) {
/*  89 */         return rankSection.getInt("order");
/*     */       }
/*     */     } 
/*     */     
/*  93 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getRankWithMinOrder() {
/*  98 */     ConfigurationSection ranks = (PluginImpl.getInstance()).Ranks.getConfig().getConfigurationSection("ranks");
/*  99 */     String minRankName = null;
/*     */     
/* 101 */     assert ranks != null;
/* 102 */     for (String key : ranks.getKeys(false)) {
/* 103 */       int order = ranks.getInt(key + ".order");
/*     */       
/* 105 */       if (order == 1) {
/* 106 */         minRankName = ranks.getString(key + ".name");
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return minRankName;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\RanksUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */