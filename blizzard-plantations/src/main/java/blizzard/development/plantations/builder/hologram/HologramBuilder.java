package blizzard.development.plantations.builder.hologram;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramBuilder {
    private static final Map<UUID, Hologram> hologram = new HashMap<>();

    // TODO: fazer um item de preferencia no menu para desativar/ativar holograma na estufa.

    public static UUID hologram(Player player, Block block) {
        UUID uuid = UUID.randomUUID();

        Hologram hologram = DHAPI.createHologram(uuid.toString(), block.getLocation().add(0.5, 0 ,0.5));
        HologramBuilder.hologram.put(uuid, hologram);

        DHAPI.addHologramLine(hologram, "§c§lERRO!");

        hologram.setDefaultVisibleState(true); // mudar para false quando terminar o plugin.
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