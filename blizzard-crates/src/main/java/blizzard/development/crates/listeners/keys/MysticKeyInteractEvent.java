package blizzard.development.crates.listeners.keys;

import blizzard.development.crates.adapters.MysticKeyAdapter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class MysticKeyInteractEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onHandle(PlayerArmorStandManipulateEvent event) {

        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();

        MysticKeyAdapter mysticKeyAdapter = new MysticKeyAdapter();
        mysticKeyAdapter.handle(player, armorStand);
    }
}
