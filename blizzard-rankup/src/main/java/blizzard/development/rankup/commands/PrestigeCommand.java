/*    */ package blizzard.development.rankup.commands;
/*    */ 
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.annotation.CommandAlias;
/*    */ import blizzard.development.essentials.acf.annotation.Default;
/*    */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.rankup.database.storage.PlayersData;
/*    */ import blizzard.development.rankup.utils.PluginImpl;
/*    */ import blizzard.development.rankup.utils.PrestigeUtils;
/*    */ import blizzard.development.rankup.utils.RanksUtils;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @CommandAlias("prestigio")
/*    */ public class PrestigeCommand
/*    */   extends BaseCommand
/*    */ {
/*    */   @Default
/*    */   public void onCommand(Player player) {
/* 23 */     PlayersData playersData = PlayersCacheManager.getPlayerData(player);
/* 24 */     YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
/* 25 */     YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();
/* 26 */     YamlConfiguration messagesConfig = (PluginImpl.getInstance()).Messages.getConfig();
/*    */     
/* 28 */     if (!hasRankForPrestige(playersData.getRank(), ranksConfig, prestigeConfig)) {
/* 29 */       sendMessage(player, messagesConfig, "chat.no-rank-for-prestige");
/*    */       
/*    */       return;
/*    */     } 
/* 33 */     if (playersData.getPrestige() >= 10) {
/* 34 */       sendMessage(player, messagesConfig, "chat.max-prestige");
/*    */       
/*    */       return;
/*    */     } 
/* 38 */     double prestigePrice = PrestigeUtils.prestigePrice(playersData.getPrestige());
/* 39 */     if (!hasCoinsForPrestige(player, prestigePrice)) {
/* 40 */       sendMessage(player, messagesConfig, "chat.no-money-for-prestige");
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     onPrestige(player, playersData, ranksConfig, messagesConfig);
/*    */   }
/*    */   
/*    */   private boolean hasRankForPrestige(String currentRank, YamlConfiguration ranksConfig, YamlConfiguration prestigeConfig) {
/* 48 */     int currentRankOrder = RanksUtils.getCurrentOrder(ranksConfig, currentRank);
/* 49 */     int requiredRankOrder = prestigeConfig.getInt("prestige.need-rank");
/* 50 */     return (currentRankOrder >= requiredRankOrder);
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean hasCoinsForPrestige(Player player, double requiredCoins) {
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   private void onPrestige(Player player, PlayersData playersData, YamlConfiguration ranksConfig, YamlConfiguration messagesConfig) {
/* 59 */     Set<String> ranks = ((ConfigurationSection)Objects.<ConfigurationSection>requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
/* 60 */     for (String rankKey : ranks) {
/* 61 */       ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
/* 62 */       if (rankSection != null && rankSection.getInt("order") == 1) {
/* 63 */         playersData.setRank(rankSection.getString("name"));
/* 64 */         playersData.setPrestige(playersData.getPrestige() + 1);
/*    */         
/* 66 */         sendMessage(player, messagesConfig, "chat.prestige");
/* 67 */         player.sendMessage("§7[DEBUG] §fSeu prestígio: " + playersData.getPrestige());
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void sendMessage(Player player, YamlConfiguration config, String path) {
/* 74 */     String message = config.getString(path);
/* 75 */     if (message != null)
/* 76 */       player.sendMessage(message); 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\commands\PrestigeCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */