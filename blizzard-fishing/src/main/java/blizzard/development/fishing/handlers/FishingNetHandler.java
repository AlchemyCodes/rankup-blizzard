package blizzard.development.fishing.handlers;

import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class FishingNetHandler {

    private static final Plugin plugin = PluginImpl.getInstance().plugin;
    private static final String key = "blizzard.fishing.net";

    public static void setNet(Player player, int slot) {
        Plugin plugin = PluginImpl.getInstance().plugin;

        ItemStack rod = new ItemBuilder(Material.COBWEB)
                .setDisplayName("Rede")
                .setLore(Arrays.asList("a",
                        "a"))
                .addPersistentData(plugin, key, 1)
                .build();

        player.getInventory().setItem(slot, rod);
    }

    public static boolean isNet (Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        return ItemBuilder.hasPersistentData(plugin, itemInMainHand, key);
    }
}
