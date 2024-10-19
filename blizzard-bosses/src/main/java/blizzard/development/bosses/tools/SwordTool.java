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

public class SwordTool {
    public static void giveSword(Player player) {
        String id = UUID.randomUUID().toString();
        ItemStack item = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName("&fSpada de Fero")
                .setLore(List.of("&fUna spadita de Fero"))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.bosses.sword-tool", "sword")
                .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.bosses.tool-id",  id)
                .build();

        ToolsMethods.createTool(player, id, Tools.SWORD);
        player.getInventory().addItem(item);
    }
}
