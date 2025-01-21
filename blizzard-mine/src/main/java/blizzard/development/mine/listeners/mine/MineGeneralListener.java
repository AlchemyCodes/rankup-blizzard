package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.managers.mine.NPCManager;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import com.comphenix.protocol.PacketType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MineGeneralListener implements Listener {

    private final PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

    @EventHandler
    public void onWorldChanged(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        World from = event.getFrom();
        World to = player.getWorld();

        Location spawnLocation = LocationUtils.getLocation(LocationEnum.SPAWN.getName());

        if (spawnLocation != null && from.equals(spawnLocation.getWorld())
            && !to.equals(spawnLocation.getWorld())) {
            HologramBuilder.getInstance().removeHologram(
                NPCManager.getInstance().getNPCUUID(player)
            );
            MineAdapter.getInstance().sendToExit(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (cacheMethods.isInMine(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (cacheMethods.isInMine(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (cacheMethods.isInMine(player)) {
                event.setCancelled(true);
            }
        }

        if (event.getEntity() instanceof EnderCrystal) {
            event.setCancelled(true);
        }
    }
}
