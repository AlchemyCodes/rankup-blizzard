package blizzard.development.mine.listeners.mine;

import blizzard.development.core.Main;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.inventories.enchantments.EnchantmentInventory;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.mine.builders.item.ItemBuilder.hasPersistentData;

public class MineInteractListener implements Listener {

    private final PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (playerCacheMethods.isInMine(player)) {
            String exit_persistent_data = "blizzard.mine.exit";
            String enchantment_persistent_data = "blizzard.mine.enchantment";


            if (hasPersistentData(Main.getInstance(), itemStack, exit_persistent_data)) {
                MineAdapter.getInstance()
                    .sendToExit(
                        player
                    );
                event.setCancelled(true);
            }

            if (hasPersistentData(Main.getInstance(), itemStack, enchantment_persistent_data)) {
                EnchantmentInventory enchantmentInventory = new EnchantmentInventory();
                enchantmentInventory.open(player);
                event.setCancelled(true);
            }

        }
    }
}
