package blizzard.development.clans.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class NBTUtils {
    public static boolean hasTag(ItemStack itemStack, String tag) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return false;
        }
        NamespacedKey key = new NamespacedKey(PluginImpl.getInstance().plugin, tag);
        return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static boolean startsWith(ItemStack itemStack, String tag) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return false;
        }

        Set<NamespacedKey> keys = meta.getPersistentDataContainer().getKeys();
        for (NamespacedKey key : keys) {
            if (key.getKey().startsWith(tag)) {
                return true;
            }
        }
        return false;
    }

    public static String getTag(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return null;
        }

        Set<NamespacedKey> keys = meta.getPersistentDataContainer().getKeys();
        for (NamespacedKey key : keys) {
                return key.getKey();
        }
        return null;
    }

    public static void setTag(ItemStack itemStack, String tag) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            NamespacedKey key = new NamespacedKey(PluginImpl.getInstance().plugin, tag);
            meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            meta.setUnbreakable(true);
            itemStack.setItemMeta(meta);
        }
    }
}
