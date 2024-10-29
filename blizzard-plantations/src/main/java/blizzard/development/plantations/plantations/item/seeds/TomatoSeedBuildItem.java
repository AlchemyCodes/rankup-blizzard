package blizzard.development.plantations.plantations.item.seeds;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TomatoSeedBuildItem {

    public static ItemStack tomatoSeed(int amount) {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxNGI1OGYzZGY2NWEwYzZiOTBlZTE5NDY0YjI1NTdjODNhZTJjOWZhMWI1NzM4YmIxMTM2NGNkOWY1YjNlMSJ9fX0")
                .setDisplayName("§cSemente de §lTomate")
                .setLore(Arrays.asList(
                        "§7Plante esta semente de",
                        "§7tomate na sua estufa.",
                        "",
                        " §fDisponível para cultivo",
                        " §fna sua área de estufa.",
                        " §8☰ §c(/estufa)",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "semente", "semente.tomate")
                .build(amount);
    }
}
