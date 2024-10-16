package blizzard.development.crates.listeners.keys;

import blizzard.development.crates.adapters.RareKeyAdapter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class RareKeyInteractEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onHandle(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();

        RareKeyAdapter rareKeyAdapter = new RareKeyAdapter();
        rareKeyAdapter.handle(player, armorStand);
    }
}
