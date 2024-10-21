package blizzard.development.bosses.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
    public static Boolean hasEmptySlot(Player player) {
        int emptySlots = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) emptySlots++;
        }
        if (emptySlots < 1) {
            player.sendMessage("§c§lEI! §cVocê precisa de pelo menos um slot vazio para fazer isso.");
            return false;
        } else {
            return true;
        }
    }
}
