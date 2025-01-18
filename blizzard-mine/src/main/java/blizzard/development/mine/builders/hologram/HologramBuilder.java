package blizzard.development.mine.builders.hologram;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class HologramBuilder {
    private static final HologramBuilder instance = new HologramBuilder();

    public static HologramBuilder getInstance() {
        return instance;
    }

    private final Map<UUID, Hologram> hologram = new HashMap<>();

    public void createHologram(Player player, UUID id, Location location, List<String> lines) {
        Hologram hologram = DHAPI.createHologram(id.toString(), location);
        this.hologram.put(id, hologram);

        hologram.setDefaultVisibleState(false);
        hologram.setShowPlayer(player);

        hologram.update(player);

        lines.forEach(line -> DHAPI.addHologramLine(hologram, line));
    }

    public Hologram getHologram(UUID uuid) {
        return hologram.get(uuid);
    }

    public void removeHologram(UUID uuid) {
        Hologram holograms = hologram.remove(uuid);
        if (holograms != null) {
            DHAPI.removeHologram(holograms.getName());
        }
    }

    public void removeAllHolograms() {
        Collection<Hologram> holograms = this.hologram.values();
        for (Hologram hologram : holograms) {
            DHAPI.removeHologram(hologram.getName());
        }
    }
}