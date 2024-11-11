package blizzard.development.plantations.plantations.item.seeds;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CornSeedBuildItem {

    public static ItemStack cornSeed(int amount) {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNhNmIwOTljZDQwMWUzYTBkNjRkOWExNmY0NmNkMGM1Y2E1ZjdlNDVlNmE2OWMyN2QyZTQ3Mzc3NWIyNWZlIn19fQ==")
                .setDisplayName("§eSemente de §lMilho")
                .setLore(Arrays.asList(
                        "§7Plante esta semente de",
                        "§7tomate na sua estufa.",
                        "",
                        " §fDisponível para cultivo",
                        " §fna sua área de estufa.",
                        " §8☰ §e(/estufa)",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "semente", "semente.milho")
                .build(amount);
    }
}
