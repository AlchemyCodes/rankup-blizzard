package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class MonstersWorldListener implements Listener {

    @EventHandler
    public void onWorldChanged(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        World from = event.getFrom();
        World to = player.getWorld();

        LocationUtils utils = LocationUtils.getInstance();

        if (utils.getLocation(Locations.ENTRY.getName()) == null) return;

        MonstersWorldHandler handler = MonstersWorldHandler.getInstance();

        if (from.equals(utils.getLocation(Locations.ENTRY.getName()).getWorld())
                && !to.equals(utils.getLocation(Locations.ENTRY.getName()).getWorld())) {
            handler.removePlayer(player);
        }
    }
}
