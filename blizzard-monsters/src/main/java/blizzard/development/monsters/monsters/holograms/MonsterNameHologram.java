package blizzard.development.monsters.monsters.holograms;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MonsterNameHologram {
    private static MonsterNameHologram instance;

    public List<String> getLines(String display, Integer life) {
        return Arrays.asList(
                display,
                "§c❤" + life
        );
    }

    public void update(Player player, UUID id, String display, Integer life) {
        Hologram holo = HologramBuilder.getInstance().getHologram(id);
        if (holo != null) {
            DHAPI.setHologramLine(holo, 0, display);
            DHAPI.setHologramLine(holo, 1, "§c❤" + life);
            holo.update(player);
        }
    }


    public static MonsterNameHologram getInstance() {
        if (instance == null) instance = new MonsterNameHologram();
        return instance;
    }
}
