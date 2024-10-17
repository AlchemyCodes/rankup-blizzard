package blizzard.development.bosses.utils.items;

import alchemy.development.lobby.utils.items.apis.SkullAPI;
import alchemy.development.lobby.utils.items.apis.TextAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final PersistentDataContainer pdc;

    public ItemBuilder(Material material) {
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

    public ItemBuilder addPersistentData(Plugin plugin, String value) {
        NamespacedKey key = new NamespacedKey(plugin, value);
        pdc.set(key, PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, Integer value) {
        NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
        pdc.set(key, PersistentDataType.INTEGER, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, Boolean value) {
        NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
        pdc.set(key, PersistentDataType.BOOLEAN, value);
        return this;
    }

    public ItemBuilder addPersistentData(Plugin plugin, Double value) {
        NamespacedKey key = new NamespacedKey(plugin, String.valueOf(value));
        pdc.set(key, PersistentDataType.DOUBLE, value);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static boolean hasPersistentData(Plugin plugin, ItemStack item, String value) {
        NamespacedKey key = new NamespacedKey(plugin, value);

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

    public static String getPersistentData(ItemStack item) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            return Arrays.toString(pdc.getKeys().toArray());
        }
        return null;
    }

}
