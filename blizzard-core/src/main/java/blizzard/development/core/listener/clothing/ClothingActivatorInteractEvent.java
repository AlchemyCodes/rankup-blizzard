package blizzard.development.core.listener.clothing;

import blizzard.development.core.Main;
import blizzard.development.core.inventories.SelectInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.core.builder.ItemBuilder.hasPersistentData;

public class ClothingActivatorInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR) return;

        String[] activators = {
                "ativador.comum",
                "ativador.raro",
                "ativador.mistico",
                "ativador.lendario"
        };

        for (String activator : activators) {
            if (hasPersistentData(Main.getInstance(), item, activator)) {
                event.setCancelled(true);

                SelectInventory selectInventory = new SelectInventory();
                selectInventory.open(player, item);
            }
        }
    }
}
