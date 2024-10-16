package blizzard.development.crates.inventories;

import blizzard.development.crates.utils.PluginImpl;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class LegendaryInventory {

    public void open(Player player) {

        ChestGui inventory = new ChestGui(3, "§8Caixa Lendária | Recompensas");
        StaticPane pane = new StaticPane(0, 0, 9, 5);

        item(pane, "crates.legendary", PluginImpl.getInstance().Crates.getConfig());

        inventory.addPane(pane);
        inventory.show(player);
    }

    private static void item(StaticPane pane, String configPath, FileConfiguration config) {
        for (String item : config.getConfigurationSection(configPath).getKeys(false)) {

            String itemType = config.getString(configPath + "." + item + ".material");
            int itemSlot = config.getInt(configPath + "." + item + ".slot");
            String displayName = config.getString(configPath + "." + item + ".display").replace("&", "§");

            List<String> lore = config.getStringList(configPath + "." + item + ".lore");
            List<String> replacedLore = lore.stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());

            ItemStack block = new ItemStack(Material.getMaterial(itemType));
            ItemMeta meta = block.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(replacedLore);
            block.setItemMeta(meta);


            GuiItem rewardItem = new GuiItem(block, event -> {
                event.setCancelled(true);
            });

            pane.addItem(rewardItem, Slot.fromIndex(itemSlot));
        }
    }
}
