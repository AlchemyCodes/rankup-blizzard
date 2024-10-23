/*     */ package blizzard.development.rankup.inventories;
/*     */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*     */ import blizzard.development.rankup.database.storage.PlayersData;
/*     */ import blizzard.development.rankup.utils.PluginImpl;
/*     */ import blizzard.development.rankup.utils.PrestigeUtils;
/*     */ import blizzard.development.rankup.utils.RanksUtils;
/*     */ import blizzard.development.rankup.utils.skulls.SkullAPI;
/*     */ import com.github.stefvanschie.inventoryframework.gui.GuiItem;
/*     */ import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
/*     */ import com.github.stefvanschie.inventoryframework.pane.StaticPane;
/*     */ import com.github.stefvanschie.inventoryframework.pane.util.Slot;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class TopsInventory {
/*     */   public static void openTopInventory(Player player) {
/*  27 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  28 */     int size = config.getInt("topsInventory.size");
/*  29 */     String title = config.getString("topsInventory.title");
/*     */     
/*  31 */     ChestGui inventory = new ChestGui(size, title);
/*  32 */     StaticPane pane = new StaticPane(0, 0, 9, size);
/*     */ 
/*     */ 
/*     */     
/*  36 */     List<PlayersData> topPlayers = (List<PlayersData>)PlayersCacheManager.getAllPlayersData().stream().sorted((p1, p2) -> Integer.compare(p2.getPrestige(), p1.getPrestige())).collect(Collectors.toList());
/*     */     
/*  38 */     int numberOfItems = Math.min(topPlayers.size(), 10);
/*     */     
/*  40 */     int[] itemSlots = { 10, 11, 12, 13, 14, 15, 16, 21, 22, 23 };
/*     */     
/*  42 */     for (int i = 0; i < numberOfItems; i++) {
/*  43 */       PlayersData playerData = topPlayers.get(i);
/*     */       
/*  45 */       ConfigurationSection inventoryConfig = config.getConfigurationSection("topsInventory.items.head");
/*     */       
/*  47 */       Material itemType = Material.valueOf(inventoryConfig.getString("material"));
/*  48 */       String displayName = inventoryConfig.getString("displayName").replace("{rank}", String.valueOf(i + 1));
/*     */       
/*  50 */       List<String> lore = new ArrayList<>();
/*  51 */       for (String line : inventoryConfig.getStringList("lore")) {
/*  52 */         lore.add(line
/*  53 */             .replace("{nickname}", playerData.getNickname())
/*  54 */             .replace("{prestige}", String.valueOf(playerData.getPrestige())));
/*     */       }
/*     */ 
/*     */       
/*  58 */       ItemStack ranking = SkullAPI.withName(new ItemStack(itemType), playerData.getNickname());
/*  59 */       ItemMeta meta = ranking.getItemMeta();
/*  60 */       meta.setDisplayName(displayName);
/*  61 */       meta.setLore(lore);
/*  62 */       ranking.setItemMeta(meta);
/*     */       
/*  64 */       GuiItem rankingItem = new GuiItem(ranking, event -> event.setCancelled(true));
/*     */ 
/*     */ 
/*     */       
/*  68 */       pane.addItem(rankingItem, Slot.fromIndex(itemSlots[i]));
/*     */     } 
/*     */     
/*  71 */     GuiItem informationItem = new GuiItem(informations(player), event -> event.setCancelled(true));
/*     */ 
/*     */ 
/*     */     
/*  75 */     GuiItem backItem = new GuiItem(back(), event -> {
/*     */           RankInventory.openRankInventory(player);
/*     */           
/*     */           event.setCancelled(true);
/*     */         });
/*  80 */     pane.addItem(informationItem, Slot.fromIndex(41));
/*  81 */     pane.addItem(backItem, Slot.fromIndex(27));
/*     */     
/*  83 */     inventory.addPane((Pane)pane);
/*  84 */     inventory.show((HumanEntity)player);
/*  85 */     player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public static ItemStack back() {
/*  89 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  90 */     ConfigurationSection backConfig = config.getConfigurationSection("topsInventory.items.back");
/*     */     
/*  92 */     Material material = Material.valueOf(backConfig.getString("material"));
/*  93 */     String displayName = backConfig.getString("displayName");
/*  94 */     List<String> lore = backConfig.getStringList("lore");
/*     */     
/*  96 */     ItemStack back = new ItemStack(material);
/*  97 */     ItemMeta meta = back.getItemMeta();
/*  98 */     meta.setDisplayName(displayName);
/*  99 */     meta.setLore(lore);
/*     */     
/* 101 */     back.setItemMeta(meta);
/*     */     
/* 103 */     return back;
/*     */   }
/*     */   
/*     */   public static ItemStack informations(Player player) {
/* 107 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/* 108 */     YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
/*     */     
/* 110 */     PlayersData playersData = PlayersCacheManager.getPlayerData(player);
/* 111 */     String currentRank = playersData.getRank();
/* 112 */     int prestige = playersData.getPrestige();
/*     */     
/* 114 */     ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
/* 115 */     ConfigurationSection infoConfig = config.getConfigurationSection("topsInventory.items.information");
/*     */     
/* 117 */     Material material = Material.valueOf(infoConfig.getString("material"));
/* 118 */     String displayName = infoConfig.getString("displayName");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     List<String> lore = (List<String>)infoConfig.getStringList("lore").stream().map(line -> line.replace("{current_rank}", RanksUtils.getCurrentRank(ranksConfig, currentRank)).replace("{next_rank}", (RanksUtils.getNextRank(ranksConfig, currentRankSection) != null) ? RanksUtils.getNextRank(ranksConfig, currentRankSection) : "Nenhum").replace("{prestige}", String.valueOf(prestige)).replace("{next_prestige}", String.valueOf(prestige + 1)).replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCostAdd(prestige)))).collect(Collectors.toList());
/*     */     
/* 127 */     ItemStack info = new ItemStack(material);
/* 128 */     ItemMeta meta = info.getItemMeta();
/* 129 */     meta.setDisplayName(displayName);
/* 130 */     meta.setLore(lore);
/*     */     
/* 132 */     info.setItemMeta(meta);
/*     */     
/* 134 */     return info;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\inventories\TopsInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */