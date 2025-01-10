package blizzard.development.monsters.listeners.packets;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.handlers.packets.entity.EntityRotation;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersPacketMoveListener implements Listener {
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public final Map<UUID, Long> moveCooldowns = new HashMap<>();
    public final Map<String, Location> locationCache = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }

        long now = System.currentTimeMillis();
        if (moveCooldowns.getOrDefault(player.getUniqueId(), 0L) > now) {
            return;
        }
        moveCooldowns.put(player.getUniqueId(), now + 1000);

        EntityRotation entityRotation = EntityRotation.getInstance();

        if (MonstersWorldHandler.getInstance().containsPlayer(player)) {
            for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                Location location = getCachedLocation(monstersData.getLocation());
                if (location != null && player.getLocation().distance(location) <= 5) {
                    entityRotation.updateRotation(player, location, Integer.parseInt(monstersData.getId()), protocolManager);
                }
            }
        }
    }

    private Location getCachedLocation(String serializedLocation) {
        return locationCache.computeIfAbsent(serializedLocation, LocationUtils.getInstance()::deserializeLocation);
    }
}
