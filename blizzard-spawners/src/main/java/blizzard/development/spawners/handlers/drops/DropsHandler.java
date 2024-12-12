package blizzard.development.spawners.handlers.drops;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class DropsHandler {
    final static Plugin plugin = PluginImpl.getInstance().plugin;

    public static void giveDropsAutoSell(Player player, int amount) {
        final String key = "blizzard.spawners-autosell";

        final ItemStack item = new ItemBuilder(Material.ENDER_EYE)
                .setDisplayName("&3Ativador de §lAuto Vender")
                .setLore(Arrays.asList(
                        "&7Utilize este ativador para",
                        "§7liberar a opção de vender",
                        "§7automaticamente drops em",
                        "§7seu gerador periodicamente.",
                        "",
                        "&3Clique no gerador e ative."
                ))
                .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .addPersistentData(plugin, key, String.valueOf(amount))
                .setAmount(amount)
                .build();

        player.getInventory().addItem(item);
    }
}
