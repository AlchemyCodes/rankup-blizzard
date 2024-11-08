package blizzard.development.spawners.handlers.mobs;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class PigMob {
    final static Plugin plugin = PluginImpl.getInstance().plugin;
    final static String spawner = Spawners.PIG.getType();
    final static String key = "blizzard.spawners-" + spawner;

    public static void give(Player player, Double amount, Integer stack) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        final ItemStack item = new ItemBuilder(Material.SPAWNER)
                .setDisplayName("&dSpawner de Proco &7(x" + formattedAmount + ")")
                .setLore(Arrays.asList(
                        "&7Isso é um definitivamente",
                        "&7um spawner de proco.",
                        "",
                        "&dClique no chão para colocar"
                ))
                .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .addPersistentData(plugin, key, String.valueOf(amount))
                .setAmount(stack)
                .build();

            player.getInventory().addItem(item);
    }
}
