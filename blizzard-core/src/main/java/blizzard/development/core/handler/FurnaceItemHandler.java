package blizzard.development.core.handler;

import blizzard.development.core.builder.ItemBuilder;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class FurnaceItemHandler {
    private static final Plugin plugin = PluginImpl.getInstance().plugin;
    private static final String key = "blizzard.core.campfire";

    public static void addCampfire(Player player) {
        Plugin plugin = PluginImpl.getInstance().plugin;

        ItemStack rod = new ItemBuilder(Material.CAMPFIRE)
                .setDisplayName("§6Fogueira")
                .setLore(Arrays.asList(
                        "§7Coloque a fogueira",
                        "§7em um lugar adequado",
                        "§7para se aquecer na nevasca."))
                .addPersistentData(plugin, key)
                .build();

        player.getInventory().addItem(rod);
    }

    public static boolean isCampfire(Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        return ItemBuilder.hasPersistentData(plugin, itemInMainHand, key);
    }
}
