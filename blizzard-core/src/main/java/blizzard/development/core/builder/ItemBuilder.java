/*     */ package blizzard.development.core.builder;
/*     */ 
/*     */ import blizzard.development.core.utils.items.SkullAPI;
/*     */ import blizzard.development.core.utils.items.TextAPI;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.NamespacedKey;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.persistence.PersistentDataContainer;
/*     */ import org.bukkit.persistence.PersistentDataType;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class ItemBuilder
/*     */ {
/*     */   private final ItemStack itemStack;
/*     */   private final ItemMeta itemMeta;
/*     */   private final PersistentDataContainer pdc;
/*     */   
/*     */   public ItemBuilder(Material material) {
/*  27 */     this.itemStack = new ItemStack(material);
/*  28 */     this.itemMeta = this.itemStack.getItemMeta();
/*  29 */     this.pdc = this.itemMeta.getPersistentDataContainer();
/*     */   }
/*     */   
/*     */   public ItemBuilder(String value) {
/*  33 */     this.itemStack = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);
/*  34 */     this.itemMeta = this.itemStack.getItemMeta();
/*  35 */     this.pdc = this.itemMeta.getPersistentDataContainer();
/*     */   }
/*     */   
/*     */   public ItemBuilder(Player player) {
/*  39 */     this.itemStack = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player.getName());
/*  40 */     this.itemMeta = this.itemStack.getItemMeta();
/*  41 */     this.pdc = this.itemMeta.getPersistentDataContainer();
/*     */   }
/*     */   
/*     */   public ItemBuilder setDisplayName(String name) {
/*  45 */     this.itemMeta.displayName(TextAPI.parse(name.replace("&", "§")));
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(List<String> lore) {
/*  52 */     List<Component> component = (List<Component>)lore.stream().map(line -> TextAPI.parse(line.replace("&", "§"))).collect(Collectors.toList());
/*  53 */     this.itemMeta.lore(component);
/*  54 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
/*  58 */     this.itemStack.addEnchantment(enchantment, level);
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
/*  63 */     this.itemStack.addUnsafeEnchantment(enchantment, level);
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addEnchant(Enchantment enchantment, int level, Boolean bool) {
/*  68 */     this.itemMeta.addEnchant(enchantment, level, bool.booleanValue());
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addItemFlags(ItemFlag... flags) {
/*  73 */     this.itemMeta.addItemFlags(flags);
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addPersistentData(Plugin plugin, String value) {
/*  78 */     NamespacedKey key = new NamespacedKey(plugin, value);
/*  79 */     this.pdc.set(key, PersistentDataType.STRING, value);
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addPersistentData(Plugin plugin, Integer value) {
/*  84 */     NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
/*  85 */     this.pdc.set(key, PersistentDataType.INTEGER, value);
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addPersistentData(Plugin plugin, Boolean value) {
/*  90 */     NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
/*  91 */     this.pdc.set(key, PersistentDataType.BOOLEAN, value);
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public ItemBuilder addPersistentData(Plugin plugin, Double value) {
/*  96 */     NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
/*  97 */     this.pdc.set(key, PersistentDataType.DOUBLE, value);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public ItemStack build() {
/* 102 */     this.itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
/* 103 */     this.itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
/* 104 */     this.itemStack.setItemMeta(this.itemMeta);
/* 105 */     return this.itemStack;
/*     */   }
/*     */   
/*     */   public ItemStack build(Player player, int amount) {
/* 109 */     this.itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
/* 110 */     this.itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
/* 111 */     this.itemStack.setItemMeta(this.itemMeta);
/* 112 */     this.itemStack.setAmount(amount);
/*     */     
/* 114 */     player.getInventory().addItem(new ItemStack[] { this.itemStack });
/* 115 */     return this.itemStack;
/*     */   }
/*     */   
/*     */   public static boolean hasPersistentData(Plugin plugin, ItemStack item, String value) {
/* 119 */     NamespacedKey key = new NamespacedKey(plugin, value);
/*     */     
/* 121 */     if (item.hasItemMeta()) {
/* 122 */       ItemMeta meta = item.getItemMeta();
/* 123 */       PersistentDataContainer pdc = meta.getPersistentDataContainer();
/* 124 */       return (pdc.has(key, PersistentDataType.STRING) || pdc
/* 125 */         .has(key, PersistentDataType.INTEGER) || pdc
/* 126 */         .has(key, PersistentDataType.BOOLEAN) || pdc
/* 127 */         .has(key, PersistentDataType.DOUBLE));
/*     */     } 
/*     */     
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   public static String getPersistentData(ItemStack item) {
/* 134 */     if (item.hasItemMeta()) {
/* 135 */       ItemMeta meta = item.getItemMeta();
/* 136 */       PersistentDataContainer pdc = meta.getPersistentDataContainer();
/* 137 */       return Arrays.toString(pdc.getKeys().toArray());
/*     */     } 
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\builder\ItemBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */