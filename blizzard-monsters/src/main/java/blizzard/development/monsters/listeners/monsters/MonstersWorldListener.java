package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.listeners.packets.PlayersPacketMoveListener;
import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.monsters.handlers.tools.MonstersToolHandler;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MonstersWorldListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        MonstersWorldHandler monstersWorldHandler = MonstersWorldHandler.getInstance();
        MonstersHandler monstersHandler = MonstersHandler.getInstance();

        if (monstersWorldHandler.containsPlayer(player)) {
            if (monstersHandler.monstersLocation.containsKey(player)) {

                double distance = player.getLocation().distance(monstersHandler.getMonstersLocation(player));

                String displayName = monstersHandler.getMonstersDisplay(player);

                if (distance <= 3) {
                    player.sendActionBar("§b§lMONSTROS! §bVocê encontrou o monstro §l" + displayName + "§b.");
                    monstersHandler.monstersLocation.remove(player);
                    return;
                }

                String formatedDistance = Math.round(distance) + " metro(s)";

                player.sendActionBar("§b§lMONSTROS! §bVocê está a §7" + formatedDistance + "§b de um §l" + displayName + "§b.");
            }
        }
    }

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
            MonstersHandler.getInstance().monsters.remove(player);

            int amount = 0;
            for (MonstersData data : MonstersCacheManager.getInstance().monstersCache.values()) {
                if (data.getOwner().equals(player.getName())) {
                    amount++;
                }
            }

            if (amount >= 1) {
                Arrays.asList(
                        "",
                        " §3§lMonstros! §7Capturamos os seus monstros.",
                        " §7Você pode pegá-los novamente no §f´/gaiola´§7.",
                        ""
                ).forEach(player::sendMessage);
            }

            for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                if (monstersData.getOwner().equals(player.getName())) {
                    HologramBuilder.getInstance().removeHologram(UUID.fromString(monstersData.getUuid()));
                }
            }

            MonstersHandler.getInstance().monstersLocation.remove(player);
        }
    }
}
