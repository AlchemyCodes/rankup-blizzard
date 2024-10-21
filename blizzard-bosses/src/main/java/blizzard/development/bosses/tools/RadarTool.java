package blizzard.development.bosses.tools;

import blizzard.development.bosses.enums.Tools;
import blizzard.development.bosses.methods.ToolsMethods;
import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class RadarTool {
    public static void give(Player player) {
        String id = UUID.randomUUID().toString();
        ItemStack item = new ItemBuilder(Material.COMPASS)
                .setDisplayName("&cRadar")
                .setLore(List.of("&7Un radar"))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.bosses.tools-radar", "radar")
                .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.bosses.tools-id",  id)
                .build();
        ToolsMethods.createTool(player, id, Tools.RADAR);
        player.getInventory().addItem(item);
    }
}
