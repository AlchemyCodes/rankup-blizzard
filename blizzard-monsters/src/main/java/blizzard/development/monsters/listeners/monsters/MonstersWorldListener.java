package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.handlers.tools.MonstersToolHandler;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

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

            MonstersToolHandler.getInstance().removeRadar(player);

            for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                if (monstersData.getOwner().equals(player.getName())) {
                    HologramBuilder.getInstance().removeHologram(UUID.fromString(monstersData.getId()));
                }
            }
        }
    }
}
