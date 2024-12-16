package blizzard.development.spawners.builders.spawners;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramBuilder {
    private static final Map<UUID, Hologram> hologram = new HashMap<>();

    public static UUID hologram(Player player, Location location) {
        UUID uuid = UUID.randomUUID();

        Hologram hologram = DHAPI.createHologram(uuid.toString(), location.add(0.5, 0 ,0.5));
        HologramBuilder.hologram.put(uuid, hologram);

        hologram.setDefaultVisibleState(false);
        hologram.setShowPlayer(player);

        return uuid;
    }

    public static Hologram getHologram(UUID uuid) {
        return hologram.get(uuid);
    }

    public static void removeHologram(UUID uuid) {
        Hologram holograms = hologram.remove(uuid);
        if (holograms != null) {
            DHAPI.removeHologram(holograms.getName());
        }
    }
}
