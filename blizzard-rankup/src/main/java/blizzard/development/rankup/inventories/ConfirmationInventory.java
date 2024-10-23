/*     */ package blizzard.development.rankup.inventories;
/*     */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*     */ import blizzard.development.rankup.database.storage.PlayersData;
/*     */ import blizzard.development.rankup.utils.PluginImpl;
/*     */ import blizzard.development.rankup.utils.PrestigeUtils;
/*     */ import blizzard.development.rankup.utils.RanksUtils;
/*     */ import com.github.stefvanschie.inventoryframework.gui.GuiItem;
/*     */ import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
/*     */ import com.github.stefvanschie.inventoryframework.pane.StaticPane;
/*     */ import com.github.stefvanschie.inventoryframework.pane.util.Slot;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class ConfirmationInventory {
/*     */   public static void openConfirmationInventory(Player player) {
/*  24 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  25 */     int size = config.getInt("confirmationInventory.size");
/*  26 */     String title = config.getString("confirmationInventory.title");
/*     */     
/*  28 */     ChestGui gui = new ChestGui(size, title);
/*     */     
/*  30 */     StaticPane pane = new StaticPane(0, 0, 9, size);
/*     */     
/*  32 */     GuiItem information = new GuiItem(information(player), event -> event.setCancelled(true));
/*     */     
/*  34 */     GuiItem confirm = new GuiItem(confirm(), event -> {
/*     */           event.setCancelled(true); processRankUp(player);
/*     */         });
/*  37 */     GuiItem deny = new GuiItem(deny(), event -> {
/*     */           event.setCancelled(true); RankInventory.openRankInventory(player);
/*     */         });
/*  40 */     pane.addItem(confirm, Slot.fromIndex(11));
/*  41 */     pane.addItem(information, Slot.fromIndex(13));
/*  42 */     pane.addItem(deny, Slot.fromIndex(15));
/*     */     
/*  44 */     gui.addPane((Pane)pane);
/*  45 */     gui.show((HumanEntity)player);
/*     */   }
/*     */   
/*     */   public static ItemStack confirm() {
/*  49 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  50 */     ConfigurationSection inventoryConfig = config.getConfigurationSection("confirmationInventory.items.confirm");
/*     */     
/*  52 */     Material material = Material.valueOf(inventoryConfig.getString("material"));
/*  53 */     String displayName = inventoryConfig.getString("displayName");
/*  54 */     List<String> lore = inventoryConfig.getStringList("lore");
/*     */     
/*  56 */     ItemStack back = new ItemStack(material);
/*  57 */     ItemMeta meta = back.getItemMeta();
/*  58 */     meta.setDisplayName(displayName);
/*  59 */     meta.setLore(lore);
/*     */     
/*  61 */     back.setItemMeta(meta);
/*     */     
/*  63 */     return back;
/*     */   }
/*     */   
/*     */   public static ItemStack information(Player player) {
/*  67 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  68 */     YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
/*     */     
/*  70 */     PlayersData playersData = PlayersCacheManager.getPlayerData(player);
/*  71 */     String currentRank = playersData.getRank();
/*     */     
/*  73 */     ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
/*  74 */     ConfigurationSection infoConfig = config.getConfigurationSection("confirmationInventory.items.information");
/*     */     
/*  76 */     Material material = Material.valueOf(infoConfig.getString("material"));
/*  77 */     String displayName = infoConfig.getString("displayName");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     List<String> lore = (List<String>)infoConfig.getStringList("lore").stream().map(line -> line.replace("{current_rank}", RanksUtils.getCurrentRank(ranksConfig, currentRank)).replace("{next_rank}", (RanksUtils.getNextRank(ranksConfig, currentRankSection) != null) ? RanksUtils.getNextRank(ranksConfig, currentRankSection) : "Nenhum")).collect(Collectors.toList());
/*     */     
/*  84 */     ItemStack info = new ItemStack(material);
/*  85 */     ItemMeta meta = info.getItemMeta();
/*  86 */     meta.setDisplayName(displayName);
/*  87 */     meta.setLore(lore);
/*     */     
/*  89 */     info.setItemMeta(meta);
/*     */     
/*  91 */     return info;
/*     */   }
/*     */   
/*     */   public static ItemStack deny() {
/*  95 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  96 */     ConfigurationSection inventoryConfig = config.getConfigurationSection("confirmationInventory.items.deny");
/*     */     
/*  98 */     Material material = Material.valueOf(inventoryConfig.getString("material"));
/*  99 */     String displayName = inventoryConfig.getString("displayName");
/* 100 */     List<String> lore = inventoryConfig.getStringList("lore");
/*     */     
/* 102 */     ItemStack back = new ItemStack(material);
/* 103 */     ItemMeta meta = back.getItemMeta();
/* 104 */     meta.setDisplayName(displayName);
/* 105 */     meta.setLore(lore);
/*     */     
/* 107 */     back.setItemMeta(meta);
/*     */     
/* 109 */     return back;
/*     */   }
/*     */   
/*     */   public static void processRankUp(Player player) {
/* 113 */     PlayersData playersData = PlayersCacheManager.getPlayerData(player);
/* 114 */     String currentRank = playersData.getRank();
/* 115 */     int prestigeLevel = playersData.getPrestige();
/*     */     
/* 117 */     YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
/* 118 */     YamlConfiguration messagesConfig = (PluginImpl.getInstance()).Messages.getConfig();
/*     */     
/* 120 */     ConfigurationSection nextRankSection = getNextRankSection(ranksConfig, currentRank);
/* 121 */     if (nextRankSection == null) {
/* 122 */       sendMessage(player, messagesConfig, "chat.max-rank");
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     if (!hasMoneyForRankUp(player, nextRankSection, prestigeLevel, messagesConfig)) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     applyRankUp(player, playersData, nextRankSection, messagesConfig);
/*     */   }
/*     */   
/*     */   private static ConfigurationSection getNextRankSection(YamlConfiguration ranksConfig, String currentRank) {
/* 134 */     ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
/* 135 */     return RanksUtils.getNextRankSection(ranksConfig, currentRankSection);
/*     */   }
/*     */   
/*     */   private static boolean hasMoneyForRankUp(Player player, ConfigurationSection nextRankSection, int prestigeLevel, YamlConfiguration messagesConfig) {
/* 139 */     double rankUpPrice = getRankUpPrice(nextRankSection, prestigeLevel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   private static double getRankUpPrice(ConfigurationSection nextRankSection, int prestigeLevel) {
/* 151 */     return nextRankSection.getDouble("price") * PrestigeUtils.prestigeCostAdd(prestigeLevel);
/*     */   }
/*     */   
/*     */   private static void applyRankUp(Player player, PlayersData playersData, ConfigurationSection nextRankSection, YamlConfiguration messagesConfig) {
/* 155 */     String nextRankName = nextRankSection.getString("name");
/* 156 */     playersData.setRank(nextRankName);
/*     */     
/* 158 */     executeRankUpCommand(player, nextRankSection);
/* 159 */     sendMessage(player, messagesConfig, "chat.rank-up");
/* 160 */     player.sendMessage(nextRankName);
/*     */   }
/*     */   
/*     */   private static void executeRankUpCommand(Player player, ConfigurationSection nextRankSection) {
/* 164 */     String rankUpCommand = nextRankSection.getString("rankup.command");
/* 165 */     if (rankUpCommand != null && !rankUpCommand.isEmpty()) {
/* 166 */       player.performCommand(rankUpCommand);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void sendMessage(Player player, YamlConfiguration messagesConfig, String path) {
/* 171 */     String message = messagesConfig.getString(path);
/* 172 */     if (message != null)
/* 173 */       player.sendMessage(message); 
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\inventories\ConfirmationInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */