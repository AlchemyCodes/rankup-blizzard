package blizzard.development.core.builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import blizzard.development.core.utils.items.TextUtil;
import blizzard.development.core.utils.items.skulls.SkullAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final PersistentDataContainer pdc;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
        this.pdc = this.itemMeta.getPersistentDataContainer();
    }

    public ItemBuilder(String value) {
        this.itemStack = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);
        this.itemMeta = this.itemStack.getItemMeta();
        this.pdc = this.itemMeta.getPersistentDataContainer();
    }

    public ItemBuilder(Player player) {
        this.itemStack = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player.getName());
        this.itemMeta = this.itemStack.getItemMeta();
        this.pdc = this.itemMeta.getPersistentDataContainer();
    }

    public ItemBuilder setDisplayName(String name) {
        this.itemMeta.displayName(TextUtil.parse(name.replace("&", "§")));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<Component> component = lore.stream().map(line -> TextUtil.parse(line.replace("&", "§"))).collect(Collectors.toList());
        this.itemMeta.lore(component);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level, Boolean bool) {
        this.itemMeta.addEnchant(enchantment, level, bool);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, String value) {
        NamespacedKey key = new NamespacedKey(plugin, value);
        this.pdc.set(key, PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, Integer value) {
        NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
        this.pdc.set(key, PersistentDataType.INTEGER, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, Boolean value) {
        NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
        this.pdc.set(key, PersistentDataType.BOOLEAN, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, Double value) {
        NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
        this.pdc.set(key, PersistentDataType.DOUBLE, value);
        return this;
    }

    public ItemStack build() {
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

    public ItemStack build(Player player, int amount) {
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.itemStack.setItemMeta(this.itemMeta);
        this.itemStack.setAmount(amount);

        player.getInventory().addItem(this.itemStack);
        return this.itemStack;
    }

    public static boolean hasPersistentData(Plugin plugin, ItemStack item, String value) {
        NamespacedKey key = new NamespacedKey(plugin, value);

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            return pdc.has(key, PersistentDataType.STRING) || pdc.has(key, PersistentDataType.INTEGER) || pdc.has(key, PersistentDataType.BOOLEAN) || pdc.has(key, PersistentDataType.DOUBLE);
        }

        return false;
    }

    public static String getPersistentData(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            return Arrays.toString(pdc.getKeys().toArray());
        }
        return null;
    }

    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(color);
        return this;
    }
}