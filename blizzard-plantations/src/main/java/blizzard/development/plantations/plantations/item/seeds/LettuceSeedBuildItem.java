package blizzard.development.plantations.plantations.item.seeds;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class LettuceSeedBuildItem {

    public static ItemStack lettuceSeed(int amount) {
        return new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUxOTY5NjlhZmNjMTk0OWMzNTM4Njk3Y2RkNWIxOTE5N2ZhMzg1MTYxMzQ2OGRiZDU1ZDAzMTUzODk5YjYifX19")
                .setDisplayName("§aSemente de §lAlface")
                .setLore(Arrays.asList(
                        "§7Plante esta semente de",
                        "§7tomate na sua estufa.",
                        "",
                        " §fDisponível para cultivo",
                        " §fna sua área de estufa.",
                        " §8☰ §a(/estufa)",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "semente", "semente.alface")
                .build(amount);
    }
}
