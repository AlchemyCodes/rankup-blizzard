/*     */ package blizzard.development.rankup.inventories;
/*     */ import blizzard.development.rankup.utils.NumberFormat;
/*     */ import blizzard.development.rankup.utils.PluginImpl;
/*     */ import com.github.stefvanschie.inventoryframework.gui.GuiItem;
/*     */ import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
/*     */ import com.github.stefvanschie.inventoryframework.pane.Pane;
/*     */ import com.github.stefvanschie.inventoryframework.pane.StaticPane;
/*     */ import com.github.stefvanschie.inventoryframework.pane.util.Slot;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class RanksInventory {
/*  22 */   private static final int[] SLOTS = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25 };
/*  23 */   private static final int MAX_ITEMS_PER_PAGE = SLOTS.length;
/*     */   
/*     */   public static void openRanksInventory(Player player) {
/*  26 */     YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
/*  27 */     Set<String> ranks = ranksConfig.getConfigurationSection("ranks").getKeys(false);
/*     */ 
/*     */ 
/*     */     
/*  31 */     List<String> orderedRanks = (List<String>)ranks.stream().sorted(Comparator.comparingInt(rank -> ranksConfig.getInt("ranks." + rank + ".order"))).collect(Collectors.toList());
/*     */     
/*  33 */     int totalPages = (int)Math.ceil(orderedRanks.size() / MAX_ITEMS_PER_PAGE);
/*     */     
/*  35 */     createRanksPage(player, orderedRanks, ranksConfig, 0, totalPages);
/*     */   }
/*     */   
/*     */   private static void createRanksPage(Player player, List<String> orderedRanks, YamlConfiguration ranksConfig, int page, int totalPages) {
/*  39 */     ChestGui gui = new ChestGui(5, "Ranks - Página " + page + 1);
/*  40 */     StaticPane pane = new StaticPane(0, 0, 9, 5);
/*     */     
/*  42 */     int start = page * MAX_ITEMS_PER_PAGE;
/*  43 */     int end = Math.min(start + MAX_ITEMS_PER_PAGE, orderedRanks.size());
/*     */     
/*  45 */     for (int i = start; i < end; i++) {
/*  46 */       String rank = orderedRanks.get(i);
/*  47 */       ItemStack rankItem = createRankItem(ranksConfig, rank);
/*  48 */       GuiItem guiItem = new GuiItem(rankItem, event -> event.setCancelled(true));
/*     */       
/*  50 */       pane.addItem(guiItem, Slot.fromIndex(SLOTS[i - start]));
/*     */     } 
/*     */     
/*  53 */     if (page + 1 < totalPages) {
/*  54 */       ItemStack nextPageItem = new ItemStack(Material.ARROW);
/*  55 */       ItemMeta nextPageMeta = nextPageItem.getItemMeta();
/*  56 */       nextPageMeta.setDisplayName("§aPróxima página");
/*  57 */       nextPageItem.setItemMeta(nextPageMeta);
/*     */       
/*  59 */       ItemStack rankInventoryItem = new ItemStack(Material.ARROW);
/*  60 */       ItemMeta rankInventoryMeta = rankInventoryItem.getItemMeta();
/*  61 */       nextPageMeta.setDisplayName("§aPróxima página");
/*  62 */       rankInventoryItem.setItemMeta(rankInventoryMeta);
/*     */       
/*  64 */       GuiItem nextPage = new GuiItem(nextPageItem, event -> {
/*     */             event.setCancelled(true);
/*     */             
/*     */             createRanksPage(player, orderedRanks, ranksConfig, page + 1, totalPages);
/*     */           });
/*  69 */       GuiItem rankInventory = new GuiItem(rankInventoryItem, event -> {
/*     */             event.setCancelled(true);
/*     */             
/*     */             RankInventory.openRankInventory(player);
/*     */           });
/*  74 */       pane.addItem(nextPage, Slot.fromIndex(44));
/*  75 */       pane.addItem(rankInventory, Slot.fromIndex(36));
/*     */     } 
/*     */     
/*  78 */     if (page > 0) {
/*  79 */       ItemStack previousPageItem = new ItemStack(Material.ARROW);
/*  80 */       ItemMeta previousPageMeta = previousPageItem.getItemMeta();
/*  81 */       previousPageMeta.setDisplayName("§cPágina anterior");
/*  82 */       previousPageItem.setItemMeta(previousPageMeta);
/*     */       
/*  84 */       GuiItem previousPage = new GuiItem(previousPageItem, event -> {
/*     */             event.setCancelled(true);
/*     */             
/*     */             createRanksPage(player, orderedRanks, ranksConfig, page - 1, totalPages);
/*     */           });
/*  89 */       pane.addItem(previousPage, Slot.fromIndex(36));
/*     */     } 
/*     */     
/*  92 */     gui.addPane((Pane)pane);
/*  93 */     gui.show((HumanEntity)player);
/*     */   }
/*     */   
/*     */   private static ItemStack createRankItem(YamlConfiguration config, String rank) {
/*  97 */     Material material = Material.valueOf(config.getString("ranks." + rank + ".item.type", "STONE").toUpperCase());
/*  98 */     int amount = config.getInt("ranks." + rank + ".item.amount", 1);
/*     */     
/* 100 */     ItemStack item = new ItemStack(material, amount);
/* 101 */     ItemMeta meta = item.getItemMeta();
/*     */     
/* 103 */     if (meta != null) {
/* 104 */       String displayName = config.getString("ranks." + rank + ".name");
/* 105 */       meta.setDisplayName("§6Rank §6§l" + displayName);
/*     */ 
/*     */ 
/*     */       
/* 109 */       List<String> lore = (List<String>)config.getStringList("ranks." + rank + ".lore").stream().map(line -> line.replace("{price}", NumberFormat.formatNumber(config.getInt("ranks." + rank + ".price")))).collect(Collectors.toList());
/* 110 */       meta.setLore(lore);
/*     */       
/* 112 */       item.setItemMeta(meta);
/*     */     } 
/*     */     
/* 115 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\inventories\RanksInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */