package blizzard.development.crates.utils.item;

import blizzard.development.crates.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBTUtils {

    public static boolean hasTag(ItemStack itemStack, String tag) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return false;
        }
        NamespacedKey key = new NamespacedKey(Main.getInstance(), tag);
        return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static void setTag(ItemStack itemStack, String tag) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            NamespacedKey key = new NamespacedKey(Main.getInstance(), tag);
            meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemStack.setItemMeta(meta);
        }
    }
}
