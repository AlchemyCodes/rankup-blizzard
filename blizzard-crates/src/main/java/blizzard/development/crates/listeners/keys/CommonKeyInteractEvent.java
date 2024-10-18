package blizzard.development.crates.listeners.keys;

import blizzard.development.crates.adapters.CommonKeyAdapter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class CommonKeyInteractEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onHandle(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();

        CommonKeyAdapter commonKeyAdapter = new CommonKeyAdapter();
        commonKeyAdapter.handle(player, armorStand);
    }
}
