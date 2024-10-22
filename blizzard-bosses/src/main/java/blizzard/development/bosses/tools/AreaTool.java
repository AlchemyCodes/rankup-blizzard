package blizzard.development.bosses.tools;

import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AreaTool {
    public static void give(Player player) {
        ItemStack item = new ItemBuilder(Material.GOLDEN_AXE)
                .setDisplayName("&eArea")
                .setLore(List.of("&7Un iten de area"))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.bosses.tools-area", "area")
                .build();
        player.getInventory().addItem(item);
    }
}
