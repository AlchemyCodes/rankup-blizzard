/*     */ package blizzard.development.core.inventories;
/*     */ 
/*     */ import blizzard.development.core.Main;
/*     */ import blizzard.development.core.builder.ItemBuilder;
/*     */ import blizzard.development.core.clothing.ClothingType;
/*     */ import blizzard.development.core.clothing.adapters.CommonClothingAdapter;
/*     */ import blizzard.development.core.clothing.adapters.LegendaryClothingAdapter;
/*     */ import blizzard.development.core.clothing.adapters.MysticClothingAdapter;
/*     */ import blizzard.development.core.clothing.adapters.RareClothingAdapter;
/*     */ import blizzard.development.core.database.cache.PlayersCacheManager;
/*     */ import blizzard.development.core.tasks.TemperatureDecayTask;
/*     */ import com.github.stefvanschie.inventoryframework.gui.GuiItem;
/*     */ import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
/*     */ import com.github.stefvanschie.inventoryframework.pane.Pane;
/*     */ import com.github.stefvanschie.inventoryframework.pane.StaticPane;
/*     */ import com.github.stefvanschie.inventoryframework.pane.util.Slot;
/*     */ import java.util.Arrays;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class SelectInventory {
/*     */   public void open(Player player, ItemStack item) {
/*  27 */     ChestGui inventory = new ChestGui(3, "Confirma a ação?");
/*  28 */     StaticPane pane = new StaticPane(0, 0, 9, 3);
/*     */     
/*  30 */     GuiItem accept = new GuiItem(accept(), event -> {
/*     */           event.setCancelled(true);
/*     */ 
/*     */           
/*     */           if (ItemBuilder.hasPersistentData((Plugin)Main.getInstance(), item, "ativador.lendario")) {
/*     */             item.setAmount(item.getAmount() - 1);
/*     */ 
/*     */             
/*     */             LegendaryClothingAdapter legendaryClothingAdapter = new LegendaryClothingAdapter();
/*     */ 
/*     */             
/*     */             legendaryClothingAdapter.active(player);
/*     */ 
/*     */             
/*     */             PlayersCacheManager.setPlayerClothing(player, ClothingType.LEGENDARY);
/*     */ 
/*     */             
/*     */             player.closeInventory();
/*     */ 
/*     */             
/*     */             player.sendTitle("§b§lWOW!", "§bVocê ativou uma roupa da categoria lendária.", 10, 70, 20);
/*     */ 
/*     */             
/*     */             TemperatureDecayTask.stopPlayerRunnable(player);
/*     */ 
/*     */             
/*     */             TemperatureDecayTask.startPlayerRunnable(player);
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */ 
/*     */           
/*     */           if (ItemBuilder.hasPersistentData((Plugin)Main.getInstance(), item, "ativador.mistico")) {
/*     */             item.setAmount(item.getAmount() - 1);
/*     */ 
/*     */             
/*     */             MysticClothingAdapter mysticClothingAdapter = new MysticClothingAdapter();
/*     */ 
/*     */             
/*     */             mysticClothingAdapter.active(player);
/*     */ 
/*     */             
/*     */             PlayersCacheManager.setPlayerClothing(player, ClothingType.MYSTIC);
/*     */             
/*     */             player.closeInventory();
/*     */             
/*     */             player.sendTitle("§5§lWOW!", "§5Você ativou uma roupa da categoria mística.", 10, 70, 20);
/*     */             
/*     */             TemperatureDecayTask.stopPlayerRunnable(player);
/*     */             
/*     */             TemperatureDecayTask.startPlayerRunnable(player);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           if (ItemBuilder.hasPersistentData((Plugin)Main.getInstance(), item, "ativador.raro")) {
/*     */             item.setAmount(item.getAmount() - 1);
/*     */             
/*     */             RareClothingAdapter rareClothingAdapter = new RareClothingAdapter();
/*     */             
/*     */             rareClothingAdapter.active(player);
/*     */             
/*     */             PlayersCacheManager.setPlayerClothing(player, ClothingType.RARE);
/*     */             
/*     */             player.closeInventory();
/*     */             
/*     */             player.sendTitle("§a§lWOW!", "§aVocê ativou uma roupa da categoria rara.", 10, 70, 20);
/*     */             
/*     */             TemperatureDecayTask.stopPlayerRunnable(player);
/*     */             
/*     */             TemperatureDecayTask.startPlayerRunnable(player);
/*     */           } 
/*     */           
/*     */           if (ItemBuilder.hasPersistentData((Plugin)Main.getInstance(), item, "ativador.comum")) {
/*     */             item.setAmount(item.getAmount() - 1);
/*     */             
/*     */             CommonClothingAdapter commonClothingAdapter = new CommonClothingAdapter();
/*     */             
/*     */             commonClothingAdapter.active(player);
/*     */             
/*     */             PlayersCacheManager.setPlayerClothing(player, ClothingType.COMMON);
/*     */             
/*     */             player.closeInventory();
/*     */             
/*     */             player.sendTitle("§8§lWOW!", "§8Você ativou uma roupa da categoria comum.", 10, 70, 20);
/*     */             
/*     */             TemperatureDecayTask.stopPlayerRunnable(player);
/*     */             
/*     */             TemperatureDecayTask.startPlayerRunnable(player);
/*     */           } 
/*     */         });
/*     */     
/* 123 */     GuiItem instruction = new GuiItem(instruction(player), event -> event.setCancelled(true));
/*     */ 
/*     */ 
/*     */     
/* 127 */     GuiItem cancel = new GuiItem(cancel(), event -> {
/*     */           event.setCancelled(true);
/*     */           
/*     */           player.closeInventory();
/*     */         });
/*     */     
/* 133 */     pane.addItem(accept, Slot.fromIndex(10));
/* 134 */     pane.addItem(instruction, Slot.fromIndex(13));
/* 135 */     pane.addItem(cancel, Slot.fromIndex(16));
/*     */     
/* 137 */     inventory.addPane((Pane)pane);
/* 138 */     inventory.show((HumanEntity)player);
/*     */   }
/*     */   
/*     */   public ItemStack accept() {
/* 142 */     return (new ItemBuilder(Material.LIME_DYE))
/* 143 */       .setDisplayName("§aConfirmar ação.")
/* 144 */       .setLore(Arrays.asList(new String[] {
/*     */             
/*     */             "§7Você ativará o seu", "§7manto de proteção", "", "§aClique para confirmar."
/*     */ 
/*     */ 
/*     */           
/* 150 */           })).build();
/*     */   }
/*     */   
/*     */   public ItemStack instruction(Player player) {
/* 154 */     return (new ItemBuilder(Material.MINECART))
/* 155 */       .setDisplayName("§d§lATENÇÃO! §d" + player.getName() + ".")
/* 156 */       .setLore(Arrays.asList(new String[] {
/*     */             
/*     */             "§7Confirme com cautela,", "§7a ação é irreversível."
/*     */           
/* 160 */           })).build();
/*     */   }
/*     */   
/*     */   public ItemStack cancel() {
/* 164 */     return (new ItemBuilder(Material.RED_DYE))
/* 165 */       .setDisplayName("§cCancelar ação.")
/* 166 */       .setLore(Arrays.asList(new String[] {
/*     */             
/*     */             "§7Você cancelará a", "§7ativação do manto.", "", "§cClique para cancelar."
/*     */ 
/*     */ 
/*     */           
/* 172 */           })).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\inventories\SelectInventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */