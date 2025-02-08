package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.inventories.enchantments.EnchantmentInventory;
import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

public class MineInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        String pickaxeData = "blizzard.mine.tool";

        boolean isPickaxe = ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, itemStack, pickaxeData);

        String pickaxeId = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, itemStack, pickaxeData);

        if (isPickaxe && event.getAction().isRightClick()) {
            new EnchantmentInventory().open(player, pickaxeId);
        }
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (!PlayerCacheMethods.getInstance().isInMine((Player) event.getEntered())) return;

        if (event.getVehicle() instanceof Minecart && event.getEntered() instanceof Player) {
            event.setCancelled(true);
        }
    }
}
