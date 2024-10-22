package blizzard.development.bosses.handlers.eggs;

import blizzard.development.bosses.utils.NumberFormat;
import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class BigFootEgg implements Listener {
    private static final String key = "blizzard.bosses.eggs-bigfoot";

    @EventHandler
    public void onEggInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack mainItem = player.getInventory().getItemInMainHand();

        if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, mainItem, key)) event.setCancelled(true);

        if (event.getAction() == Action.RIGHT_CLICK_AIR && player.isSneaking() && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, mainItem, key)) {
            int totalItems = 0;
            int totalAmount = 0;

            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;
                if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, key)) {
                    totalItems++;
                    int amount = Integer.parseInt(Objects.requireNonNull(ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, item, key)));
                    totalAmount += (amount * item.getAmount());
                }
            }

            if (totalItems <= 1) return;

            if (totalAmount > 0) {
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item == null) continue;
                    if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, key)) {
                        player.getInventory().remove(item);
                    }
                }
                give(player, totalAmount, 1);
                player.sendActionBar("§a§lYAY! §aVocê juntou todos seus bosses em um só.");
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, mainItem, key)) {
            boolean isInBossWorld = player.getWorld().equals(Bukkit.getWorld(Objects.requireNonNull(PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world"))));
            if (isInBossWorld) {
                player.sendActionBar("§c§lEI! §cAinda sem funcionalidade!");
            } else {
                player.sendActionBar("§c§lEI! §cVocê só pode invocar bosses na área de bosses. §7(/bosses ir)");
            }
        }
    }

    public static void give(Player player, int amount, int stack) {
        ItemStack item = new ItemBuilder(Material.TURTLE_EGG)
                .setDisplayName("&bPé Grande §7[" + NumberFormat.formatNumber(amount) + "]")
                .setLore(Arrays.asList(
                        "&7Derrote esse boss e ganhe",
                        "&7recompensas raras e incríveis.",
                        "",
                        "§bClique no chão para invocar."
                ))
                .setAmount(stack)
                .addPersistentData(PluginImpl.getInstance().plugin, key, String.valueOf(amount))
                .build();
        player.getInventory().addItem(item);
    }
}