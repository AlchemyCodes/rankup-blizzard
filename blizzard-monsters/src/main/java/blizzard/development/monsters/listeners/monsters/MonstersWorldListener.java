package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.tools.MonstersToolManager;
import blizzard.development.monsters.monsters.managers.world.MonstersWorldManager;
import blizzard.development.monsters.utils.LocationUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.UUID;

public class MonstersWorldListener implements Listener {

    private final MonstersWorldManager monstersWorldManager = MonstersWorldManager.getInstance();
    private final MonstersGeneralManager monstersManager = MonstersGeneralManager.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (monstersWorldManager.containsPlayer(player)) {
            if (monstersManager.monstersLocation.containsKey(player)) {

                double distance = player.getLocation().distance(monstersManager.getMonstersLocation(player));

                String displayName = monstersManager.getMonstersDisplay(player);

                if (distance <= 3) {
                    player.sendActionBar("§3§lMonstros! §f✧ §bVocê encontrou o monstro §l" + displayName + "§3.");
                    monstersManager.monstersLocation.remove(player);
                    return;
                }

                String formatedDistance = Math.round(distance) + " metro(s)";

                player.sendActionBar("§3§lMonstros! §f✧ §bVocê está a §7" + formatedDistance + "§3 de um §l" + displayName + "§3");
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

        MonstersWorldManager worldManager = MonstersWorldManager.getInstance();

        if (from.equals(utils.getLocation(Locations.ENTRY.getName()).getWorld())
                && !to.equals(utils.getLocation(Locations.ENTRY.getName()).getWorld())) {
            worldManager.removePlayer(player);

            MonstersToolManager.getInstance().removeRadar(player);
            monstersManager.monsters.remove(player);

            int amount = 0;
            for (MonstersData data : MonstersCacheManager.getInstance().monstersCache.values()) {
                if (data.getOwner().equals(player.getName())) {
                    amount++;
                }
            }

            if (amount >= 1) {
                Arrays.asList(
                        "",
                        " §b§lMonstros! §7Capturamos os seus monstros.",
                        " §7Você pode pegá-los novamente no §f´/jaula´§7.",
                        ""
                ).forEach(player::sendMessage);
            }

            for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                if (monstersData.getOwner().equals(player.getName())) {
                    HologramBuilder.getInstance().removeHologram(UUID.fromString(monstersData.getUuid()));
                }
            }

            monstersManager.monstersLocation.remove(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (monstersWorldManager.containsPlayer(player)
                && !player.hasPermission("blizzard.monsters.admin")) {
           event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (monstersWorldManager.containsPlayer(player)
                && !player.hasPermission("blizzard.monsters.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (monstersWorldManager.containsPlayer(player)) {
                event.setCancelled(true);
            }
        }
    }
}
