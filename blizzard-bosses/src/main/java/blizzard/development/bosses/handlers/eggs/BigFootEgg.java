package blizzard.development.bosses.handlers.eggs;

import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class BigFootEgg implements Listener {

    private static final String key = "blizzard.bosses.eggs-bigfoot";

    public static void give(Player player, int amount) {
        ItemStack item = new ItemBuilder(Material.TURTLE_EGG)
                .setDisplayName("&bPé Grande §7[" + amount + "]")
                .setLore(List.of("&7Derrote esse boss e ganhe recompensas!"))
                .addPersistentData(PluginImpl.getInstance().plugin, key, String.valueOf(amount))
                .build();
        player.getInventory().addItem(item);
    }
}