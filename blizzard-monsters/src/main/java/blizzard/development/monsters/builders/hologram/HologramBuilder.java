package blizzard.development.monsters.builders.hologram;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class HologramBuilder {
    private static HologramBuilder instance;

    private final Map<UUID, Hologram> hologram = new HashMap<>();

    public void createHologram(Player player, UUID id, Location location, String display, Integer life) {

        Hologram hologram = DHAPI.createHologram(id.toString(), location);
        this.hologram.put(id, hologram);

        hologram.setDefaultVisibleState(false);
        hologram.setShowPlayer(player);

        List<String> lines = Arrays.asList(
                display,
                "§c❤" + life
        );

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

    public static HologramBuilder getInstance() {
        if (instance == null) instance = new HologramBuilder();
        return instance;
    }
}