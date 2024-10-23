/*     */ package blizzard.development.rankup.inventories;
/*     */ import blizzard.development.rankup.database.cache.PlayersCacheManager;
/*     */ import blizzard.development.rankup.database.storage.PlayersData;
/*     */ import blizzard.development.rankup.utils.PluginImpl;
/*     */ import blizzard.development.rankup.utils.PrestigeUtils;
/*     */ import blizzard.development.rankup.utils.RanksUtils;
/*     */ import com.github.stefvanschie.inventoryframework.gui.GuiItem;
/*     */ import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
/*     */ import com.github.stefvanschie.inventoryframework.pane.Pane;
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
/*     */ public class RankInventory {
/*     */   public static void openRankInventory(Player player) {
/*  25 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  26 */     int size = config.getInt("rankInventory.size");
/*  27 */     String title = config.getString("rankInventory.title", "Rank");
/*     */     
/*  29 */     ChestGui gui = new ChestGui(size, title);
/*     */     
/*  31 */     StaticPane pane = new StaticPane(0, 0, 9, size);
/*     */     
/*  33 */     GuiItem information = new GuiItem(information(player), event -> event.setCancelled(true));
/*     */     
/*  35 */     GuiItem ranks = new GuiItem(ranks(), event -> {
/*     */           event.setCancelled(true); RanksInventory.openRanksInventory(player);
/*     */         });
/*  38 */     GuiItem tops = new GuiItem(tops(), event -> {
/*     */           event.setCancelled(true); TopsInventory.openTopInventory(player);
/*     */         });
/*  41 */     GuiItem rankup = new GuiItem(rankup(), event -> {
/*     */           event.setCancelled(true); ConfirmationInventory.openConfirmationInventory(player);
/*     */         });
/*  44 */     pane.addItem(information, Slot.fromIndex(config.getInt("rankInventory.items.information.slot")));
/*  45 */     pane.addItem(ranks, Slot.fromIndex(config.getInt("rankInventory.items.ranks.slot")));
/*  46 */     pane.addItem(tops, Slot.fromIndex(config.getInt("rankInventory.items.tops.slot")));
/*  47 */     pane.addItem(rankup, Slot.fromIndex(config.getInt("rankInventory.items.rankup.slot")));
/*     */     
/*  49 */     gui.addPane((Pane)pane);
/*  50 */     gui.show((HumanEntity)player);
/*     */   }
/*     */   
/*     */   public static ItemStack back() {
/*  54 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  55 */     ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.back");
/*     */     
/*  57 */     Material material = Material.valueOf(backConfig.getString("material"));
/*  58 */     String displayName = backConfig.getString("displayName");
/*  59 */     List<String> lore = backConfig.getStringList("lore");
/*     */     
/*  61 */     ItemStack back = new ItemStack(material);
/*  62 */     ItemMeta meta = back.getItemMeta();
/*  63 */     meta.setDisplayName(displayName);
/*  64 */     meta.setLore(lore);
/*     */     
/*  66 */     back.setItemMeta(meta);
/*     */     
/*  68 */     return back;
/*     */   }
/*     */   
/*     */   public static ItemStack information(Player player) {
/*  72 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/*  73 */     YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
/*     */     
/*  75 */     PlayersData playersData = PlayersCacheManager.getPlayerData(player);
/*  76 */     String currentRank = playersData.getRank();
/*  77 */     int prestige = playersData.getPrestige();
/*     */     
/*  79 */     ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
/*  80 */     ConfigurationSection infoConfig = config.getConfigurationSection("rankInventory.items.information");
/*     */     
/*  82 */     Material material = Material.valueOf(infoConfig.getString("material"));
/*  83 */     String displayName = infoConfig.getString("displayName");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     List<String> lore = (List<String>)infoConfig.getStringList("lore").stream().map(line -> line.replace("{current_rank}", RanksUtils.getCurrentRank(ranksConfig, currentRank)).replace("{next_rank}", (RanksUtils.getNextRank(ranksConfig, currentRankSection) != null) ? RanksUtils.getNextRank(ranksConfig, currentRankSection) : "Nenhum").replace("{prestige}", String.valueOf(prestige)).replace("{next_prestige}", String.valueOf(prestige + 1)).replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCostAdd(prestige)))).collect(Collectors.toList());
/*     */     
/*  92 */     ItemStack info = new ItemStack(material);
/*  93 */     ItemMeta meta = info.getItemMeta();
/*  94 */     meta.setDisplayName(displayName);
/*  95 */     meta.setLore(lore);
/*     */     
/*  97 */     info.setItemMeta(meta);
/*     */     
/*  99 */     return info;
/*     */   }
/*     */   
/*     */   public static ItemStack ranks() {
/* 103 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/* 104 */     ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.ranks");
/*     */     
/* 106 */     Material material = Material.valueOf(backConfig.getString("material"));
/* 107 */     String displayName = backConfig.getString("displayName");
/* 108 */     List<String> lore = backConfig.getStringList("lore");
/*     */     
/* 110 */     ItemStack back = new ItemStack(material);
/* 111 */     ItemMeta meta = back.getItemMeta();
/* 112 */     meta.setDisplayName(displayName);
/* 113 */     meta.setLore(lore);
/*     */     
/* 115 */     back.setItemMeta(meta);
/*     */     
/* 117 */     return back;
/*     */   }
/*     */   
/*     */   public static ItemStack tops() {
/* 121 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/* 122 */     ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.tops");
/*     */     
/* 124 */     Material material = Material.valueOf(backConfig.getString("material"));
/* 125 */     String displayName = backConfig.getString("displayName");
/* 126 */     List<String> lore = backConfig.getStringList("lore");
/*     */     
/* 128 */     ItemStack back = new ItemStack(material);
/* 129 */     ItemMeta meta = back.getItemMeta();
/* 130 */     meta.setDisplayName(displayName);
/* 131 */     meta.setLore(lore);
/*     */     
/* 133 */     back.setItemMeta(meta);
/*     */     
/* 135 */     return back;
/*     */   }
/*     */   
/*     */   public static ItemStack rankup() {
/* 139 */     YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
/* 140 */     ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.rankup");
/*     */     
/* 142 */     Material material = Material.valueOf(backConfig.getString("material"));
/* 143 */     String displayName = backConfig.getString("displayName");
/* 144 */     List<String> lore = backConfig.getStringList("lore");
/*     */     
/* 146 */     ItemStack back = new ItemStack(material);
/* 147 */     ItemMeta meta = back.getItemMeta();
/* 148 */     meta.setDisplayName(displayName);
/* 149 */     meta.setLore(lore);
/*     */     
/* 151 */     back.setItemMeta(meta);
/*     */     
/* 153 */     return back;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\inventories\RankInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */