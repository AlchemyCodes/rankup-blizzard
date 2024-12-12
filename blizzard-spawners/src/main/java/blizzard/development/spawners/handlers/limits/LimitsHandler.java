package blizzard.development.spawners.handlers.limits;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class LimitsHandler {
    final static Plugin plugin = PluginImpl.getInstance().plugin;

    public static void givePurchaseLimit(Player player, Double amount, Integer stack) {
        final String key = "blizzard.spawners-purchaselimit";

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        final ItemStack item = new ItemBuilder(Material.END_CRYSTAL)
                .setDisplayName("&dLimite de §lCompra ∞ &7(x" + formattedAmount + ")")
                .setLore(Arrays.asList(
                        "&7Utilize este limite para",
                        "§7conseguir adquirir mais",
                        "§7geradores de uma só vez.",
                        "",
                        "&dClique para ativar o limite."
                ))
                .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .addPersistentData(plugin, key, String.valueOf(amount))
                .setAmount(stack)
                .build();

        player.getInventory().addItem(item);
    }

    public static void giveFriendsLimit(Player player, Double amount, Integer stack) {
        final String key = "blizzard.spawners-friendslimit";

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        final ItemStack item = new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("&aLimite de §lAmigos &7(x" + formattedAmount + ")")
                .setLore(Arrays.asList(
                        "&7Utilize este limite para",
                        "§7liberar mais espaço em",
                        "§7seu gerador para amigos.",
                        "",
                        "&aClique no gerador e ative."
                ))
                .addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS)
                .addPersistentData(plugin, key, String.valueOf(amount))
                .setAmount(stack)
                .build();

        player.getInventory().addItem(item);
    }
}
