package blizzard.development.mail.inventories;

import blizzard.development.mail.Main;
import blizzard.development.mail.database.methods.PlayerMethods;
import blizzard.development.mail.utils.MailUtils;
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
import java.util.HashSet;
import java.util.List;

public class MailInventory {
    public static void openMailInventory(Player player) {
        ChestGui inventory = new ChestGui(5, "Inventario Site");
        StaticPane pane = new StaticPane(0, 0, 9, 5);

        PlayerMethods playerMethods = PlayerMethods.getInstance();

        List<String> playerItems = playerMethods.getItemList(player);

        HashSet<String> processedItems = new HashSet<>();

        int slotIndex = 10;

        for (String itemName : playerItems) {
            if (processedItems.contains(itemName)) {
                continue;
            }

            int itemCount = getItemAmount(itemName, playerItems);

            if (slotIndex <= 16) {
                pane.addItem(createItem(player, itemName, itemCount), Slot.fromIndex(slotIndex));
                slotIndex++;

                processedItems.add(itemName);
            }
        }

        inventory.addPane(pane);
        inventory.show(player);
    }


    private static GuiItem createItem(Player player, String itemName, int amount) {
        MailUtils mailUtils = MailUtils.getInstance();

        Material material = mailUtils.getItemMaterial(itemName);
        if (material == null) {
            material = Material.STONE;
        }
        String displayName = mailUtils.getItemDisplayName(itemName) + " §7[" + amount + "]";
        List<String> lore = mailUtils.getItemLore(itemName);
        String pdc = mailUtils.getItemPdc(itemName);

        ItemStack itemStack = new ItemBuilder(material)
                .setDisplayName(displayName)
                .setLore(lore)
                .addPersistentData(PluginImpl.getInstance().plugin, pdc, pdc)
                .build();

        Material finalMaterial = material;
        return new GuiItem(itemStack, event -> {
            event.setCancelled(true);
            PlayerMethods.getInstance().removeFromList(player, itemName);

            ItemStack item = new ItemBuilder(finalMaterial)
                    .setDisplayName(mailUtils.getItemDisplayName(itemName))
                    .setLore(lore)
                    .addPersistentData(PluginImpl.getInstance().plugin, pdc, pdc)
                    .build();

            player.getInventory().addItem(item);

            openMailInventory(player);});
    }


    private static int getItemAmount(String itemName, List<String> playerItems) {
        int count = 0;
        for (String item : playerItems) {
            if (item.equals(itemName)) {
                count++;
            }
        }
        return count;
    }
}

