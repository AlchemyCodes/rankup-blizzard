package blizzard.development.bosses.tools;

import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CompassTool {
    public static ItemStack item() {
        return new ItemBuilder(Material.COMPASS)
                .setDisplayName("&fRadar")
                .setLore(List.of("&fUn radar"))
                //.addPersistentData(PluginImpl.getInstance().plugin, "blizzard.bosses.compass-tool")
                .build();
    }
}
