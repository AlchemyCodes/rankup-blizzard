package blizzard.development.mysterybox.builder;

import blizzard.development.mysterybox.utils.items.SkullAPI;
import blizzard.development.mysterybox.utils.items.TextAPI;
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

import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final PersistentDataContainer pdc;

    public  ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
        this.pdc = itemMeta.getPersistentDataContainer();
    }

    public ItemBuilder(String value) {
        this.itemStack = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);
        this.itemMeta = itemStack.getItemMeta();
        this.pdc = itemMeta.getPersistentDataContainer();
    }

    public ItemBuilder(Player player) {
        this.itemStack = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player.getName());
        this.itemMeta = itemStack.getItemMeta();
        this.pdc = itemMeta.getPersistentDataContainer();
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.displayName(TextAPI.parse(name.replace("&", "§")));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<Component> component = lore.stream()
            .map(line -> TextAPI.parse(line.replace("&", "§")))
            .collect(Collectors.toList());
        itemMeta.lore(component);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level, Boolean bool) {
        itemMeta.addEnchant(enchantment, level, bool);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(color);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, String keyName, String value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        pdc.set(key, PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, String keyName, Integer value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        pdc.set(key, PersistentDataType.INTEGER, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, String keyName, Boolean value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        pdc.set(key, PersistentDataType.BOOLEAN, value);
        return this;
    }


    public ItemBuilder addPersistentData(Plugin plugin, String keyName, Double value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        pdc.set(key, PersistentDataType.DOUBLE, value);
        return this;
    }

    public ItemStack build(int amount) {
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);
        return itemStack;
    }

    public ItemStack build() {
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean hasPersistentData(Plugin plugin, ItemStack item, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            return pdc.has(key, PersistentDataType.STRING)
                || pdc.has(key, PersistentDataType.INTEGER)
                || pdc.has(key, PersistentDataType.BOOLEAN)
                || pdc.has(key, PersistentDataType.DOUBLE);
        }

        return false;
    }

    public static String getPersistentData(Plugin plugin, ItemStack item, String key) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            if (pdc.has(namespacedKey, PersistentDataType.STRING)) {
                return pdc.get(namespacedKey, PersistentDataType.STRING);
            }
        }
        return null;
    }
}