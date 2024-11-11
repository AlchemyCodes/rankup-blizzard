package blizzard.development.plantations.plantations.item.seeds;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CarrotSeedBuildItem {

    public static ItemStack carrotSeed(int amount) {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzYTZiZDk4YWMxODMzYzY2NGM0OTA5ZmY4ZDJkYzYyY2U4ODdiZGNmM2NjNWIzODQ4NjUxYWU1YWY2YiJ9fX0=")
                .setDisplayName("§6Semente de §lCenoura")
                .setLore(Arrays.asList(
                        "§7Plante esta semente de",
                        "§7tomate na sua estufa.",
                        "",
                        " §fDisponível para cultivo",
                        " §fna sua área de estufa.",
                        " §8☰ §6(/estufa)",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "semente", "semente.cenoura")
                .build(amount);
    }
}
