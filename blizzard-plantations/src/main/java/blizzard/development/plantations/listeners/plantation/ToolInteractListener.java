package blizzard.development.plantations.listeners.plantation;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.inventories.enchantments.EnchantmentInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;

public class ToolInteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Action action = event.getAction();

        if (hasPersistentData(Main.getInstance(), item, "ferramenta-id")) {
            if (action.isRightClick()) {
                if (!player.isSneaking()) return;

                EnchantmentInventory enchantmentInventory = new EnchantmentInventory();
                enchantmentInventory.open(player);
            }
        }
    }
}
