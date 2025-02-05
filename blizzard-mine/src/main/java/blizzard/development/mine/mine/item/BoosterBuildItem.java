package blizzard.development.mine.mine.item;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.mine.enums.BoosterEnum;
import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BoosterBuildItem {
    public static ItemStack booster(Player player, BoosterEnum booster) {
        return new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setDisplayName(booster.getName())
                .setLore(Arrays.asList(
                        "§7Ultilize esse booster para ganhar",
                        "§7bonûs de" + booster.getMultiplier() + "na mina.",
                        "",
                        "§dPressione o b. direito."
                ))
                .addPersistentData(PluginImpl.getInstance().plugin, booster.name().toLowerCase(), player.getName())
                .build(1);
    }
}
