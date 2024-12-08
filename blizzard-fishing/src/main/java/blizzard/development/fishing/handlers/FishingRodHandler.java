package blizzard.development.fishing.handlers;

import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishesUtils;
import blizzard.development.fishing.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class FishingRodHandler {

    private static final Plugin plugin = PluginImpl.getInstance().plugin;
    private static final String key = "blizzard.fishing.rod";

    public static void setRod(Player player, int slot) {
        Plugin plugin = PluginImpl.getInstance().plugin;
        RodsCacheMethod instance = RodsCacheMethod.getInstance();

        ItemStack rod = new ItemBuilder(Material.FISHING_ROD)
                .setDisplayName("§6Vara de Pesca")
                .setLore(Arrays.asList(
                        "§7Utilize esse vara para",
                        "§7pescar uma varidade de peixes.",
                        "",
                        " §6Encantamentos:",
                        "  §7XXXXXXXX §l{rod_enchant_xxxxxx}",
                        "  §7Experiente §l" + instance.getExperienced(player),
                        "  §7Sortudo §l" + instance.getLucky(player),
                        "",
                        "  §8▶ §fForça: §7" + instance.getStrength(player),
                        "  §8■ §fMaterial: §7" + instance.getBestMaterial(player).getFancyName(),
                        ""))
                .addPersistentData(plugin, key, 1)
                .build();

        ItemMeta meta = rod.getItemMeta();
        meta.setUnbreakable(true);
        rod.setItemMeta(meta);

        player.getInventory().setItem(slot, rod);
    }

    public static boolean isRod(Player player) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        return ItemBuilder.hasPersistentData(plugin, itemInMainHand, key);
    }

}
