package blizzard.development.fishing.handlers;

import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class FishingRodHandler {

    private static final Plugin plugin = PluginImpl.getInstance().plugin;
    private static final String key = "blizzard.fishing.rod";

    public static void setRod(Player player) {
        Plugin plugin = PluginImpl.getInstance().plugin;

        ItemStack rod = new ItemBuilder(Material.FISHING_ROD)
                .setDisplayName("Vara")
                .setLore(Arrays.asList("a",
                        "a"))
                .addPersistentData(plugin, key, 1)
                .build();

        player.getInventory().setItem(1, rod);
    }

    public static boolean isRod (Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        return ItemBuilder.hasPersistentData(plugin, itemInMainHand, key);
    }

}
