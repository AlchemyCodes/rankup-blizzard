package blizzard.development.fishing.handlers;

import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class FurnaceItemHandler {
    private static final Plugin plugin = PluginImpl.getInstance().plugin;
    private static final String key = "blizzard.fishing.furnace";

    public static void setFurnace(Player player, int slot) {
        Plugin plugin = PluginImpl.getInstance().plugin;

        ItemStack rod = new ItemBuilder(Material.FURNACE)
                .setDisplayName("§6Fornalha")
                .setLore(Arrays.asList(
                        "§7Utilize esta fornalha inteligente",
                        "§7que descongela seus peixes e os coloca",
                        "§7diretamente no balde, sem preocupações!",
                        "",
                        "§6Clique para gerenciar."))
                .addPersistentData(plugin, key, 1)
                .build();

        player.getInventory().setItem(slot, rod);
    }

    public static boolean isFurnace(Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        return ItemBuilder.hasPersistentData(plugin, itemInMainHand, key);
    }
}
