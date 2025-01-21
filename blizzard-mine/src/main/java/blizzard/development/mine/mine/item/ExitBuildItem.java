package blizzard.development.mine.mine.item;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ExitBuildItem {

    public static ItemStack exit(Player player) {
        return new ItemBuilder(Material.OAK_DOOR)
            .setDisplayName("§eSair da mina")
            .setLore(Arrays.asList(
                "§7Clique para sair da",
                "§7sua área de mineração."
            ))
            .addPersistentData(Main.getInstance(), "blizzard.mine.exit", player.getName())
            .build(1);
    }
}
