package blizzard.development.mail.inventories;

import blizzard.development.mail.database.methods.PlayerMethods;
import blizzard.development.mail.utils.PluginImpl;
import blizzard.development.mail.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MailInventory {
    public static void openMailInventory(Player player) {
        ChestGui inventory = new ChestGui(5, "Inventario Site");
        StaticPane pane = new StaticPane(0, 0, 9, 5);

        PlayerMethods playerMethods = PlayerMethods.getInstance();
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

        Set<String> itemsConfig = config.getConfigurationSection("items").getKeys(false);
        List<String> playerItems = playerMethods.getItemList(player);

        if (playerItems != null) {
            int slotIndex = 10;
            for (String playerItem : playerItems) {
                if (itemsConfig.contains(playerItem) && slotIndex <= 16) {
                    pane.addItem(createItem(playerItem), Slot.fromIndex(slotIndex));
                    slotIndex++;
                }
            }
        }

        inventory.addPane(pane);
        inventory.show(player);
    }

    private static GuiItem createItem(String itemName) {
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
        String itemPath = "items." + itemName;

        Material material = Material.getMaterial(config.getString(itemPath + ".material"));
        String displayName = config.getString(itemPath + ".displayName");
        List<String> lore = Arrays.asList(config.getStringList(itemPath + ".lore").toArray(new String[0]));

        ItemStack itemStack = new ItemBuilder(material)
                .setDisplayName(displayName)
                .setLore(lore)
                .build();

        return new GuiItem(itemStack, event -> event.setCancelled(true));
    }
}

