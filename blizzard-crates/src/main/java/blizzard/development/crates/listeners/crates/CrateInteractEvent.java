package blizzard.development.crates.listeners.crates;

import blizzard.development.crates.inventories.LegendaryInventory;
import blizzard.development.crates.inventories.MysticInventory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class CrateInteractEvent implements Listener {

    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent event) {
        ArmorStand armorStand = event.getRightClicked();
        Player player = event.getPlayer();

        if (!player.isSneaking()) {

            event.setCancelled(true);
        }

        if (player.isSneaking() && armorStand.hasMetadata("lendaria")) {
            LegendaryInventory legendaryInventory = new LegendaryInventory();
            legendaryInventory.open(player);

            event.setCancelled(true);

            return;
        }

        if (player.isSneaking() && armorStand.hasMetadata("mitica")) {
            MysticInventory mysticInventory = new MysticInventory();
            mysticInventory.open(player);

            event.setCancelled(true);
        }
    }
}
