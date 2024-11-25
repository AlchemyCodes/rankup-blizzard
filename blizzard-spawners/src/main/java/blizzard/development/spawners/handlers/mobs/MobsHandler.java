package blizzard.development.spawners.handlers.mobs;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class MobsHandler {
    final static Plugin plugin = PluginImpl.getInstance().plugin;
    final static SpawnersUtils utils = SpawnersUtils.getInstance();

    public static void giveMobSpawner(Player player, Spawners type, Double amount, Integer stack) {
        final String spawner = utils.getMobNameBySpawner(type);
        final String key = "blizzard.spawners-" + spawner;

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        final ItemStack item = new ItemBuilder(Material.SPAWNER)
                .setDisplayName("&aSpawner de " + spawner + " &7(x" + formattedAmount + ")")
                .setLore(Arrays.asList(
                        "&7Utilizar este spawner",
                        "§7pode te tornar o melhor",
                        "§7de todo o servidor",
                        "",
                        "&aClique no chão para colocar."
                ))
                .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .addPersistentData(plugin, key, String.valueOf(amount))
                .setAmount(stack)
                .build();

        player.getInventory().addItem(item);
    }
}
