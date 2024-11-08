package blizzard.development.spawners.handlers.mobs;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class CowMob {
    final static Plugin plugin = PluginImpl.getInstance().plugin;
    final static String spawner = Spawners.COW.getType();
    final static String key = "blizzard.spawners-" + spawner;

    public static void give(Player player, Double amount, Integer stack) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        final ItemStack item = new ItemBuilder(Material.SPAWNER)
                .setDisplayName("&8Spawner de Baka &7(x" + formattedAmount + ")")
                .setLore(Arrays.asList(
                        "&7Isso é um definitivamente",
                        "&7um spawner de baka.",
                        "",
                        "&8Clique no chão para colocar"
                ))
                .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .addPersistentData(plugin, key, String.valueOf(amount))
                .setAmount(stack)
                .build();

        player.getInventory().addItem(item);
    }
}
