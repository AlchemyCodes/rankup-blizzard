package blizzard.development.mine.listeners.npc;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.managers.mine.NPCManager;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.packets.NPCPacketUtils;
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

public class NPCRotationListener implements Listener {
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public final Map<UUID, Long> moveCooldowns = new HashMap<>();
    public final Map<Location, Location> locationCache = new HashMap<>();

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

        NPCPacketUtils packetUtils = NPCPacketUtils.getInstance();

        if (PlayerCacheMethods.getInstance().isInMine(player)) {
            Location npcLocation = getCachedLocation(
                    LocationUtils.getLocation(LocationEnum.NPC.getName())
            );
            if (npcLocation != null && player.getLocation().distanceSquared(npcLocation) <= 25) {
                packetUtils.NPCRotationUpdate(player, npcLocation, NPCManager.getInstance().getNPCId(player), protocolManager);
            }
        }
    }

    private Location getCachedLocation(Location location) {
        return locationCache.computeIfAbsent(location, loc -> loc);
    }
}
