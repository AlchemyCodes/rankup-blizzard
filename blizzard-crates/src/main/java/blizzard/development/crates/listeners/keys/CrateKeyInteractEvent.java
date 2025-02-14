package blizzard.development.crates.listeners.keys;

import blizzard.development.crates.crates.adapters.CrateKeyAdapter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class CrateKeyInteractEvent implements Listener {
    
    private final CrateKeyAdapter crateKeyAdapter = CrateKeyAdapter.getInstance();

    @EventHandler(priority = EventPriority.HIGH)
    public void onHandle(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();

        if (armorStand.hasMetadata("comum")) {
            crateKeyAdapter.commonKey(
                player,
                armorStand
            );
            event.setCancelled(true);
        } else if (armorStand.hasMetadata("rara")) {
            crateKeyAdapter.rareKey(
                player,
                armorStand
            );
            event.setCancelled(true);
        } else if (armorStand.hasMetadata("mistica")) {
            crateKeyAdapter.mysticKey(
                player,
                armorStand
            );
            event.setCancelled(true);
        } else if (armorStand.hasMetadata("lendaria")) {
            crateKeyAdapter.legendaryKey(
                player,
                armorStand
            );
            event.setCancelled(true);
        } else if (armorStand.hasMetadata("blizzard")) {
            crateKeyAdapter.blizzardKey(
                player,
                armorStand
            );
            event.setCancelled(true);
        }
    }
}
