package blizzard.development.crates.listeners.keys;

import blizzard.development.crates.adapters.BlizzardKeyAdapter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class BlizzardKeyInteractEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onHandle(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();

        BlizzardKeyAdapter blizzardKeyAdapter = new BlizzardKeyAdapter();
        blizzardKeyAdapter.handle(player, armorStand);
    }
}
