package blizzard.development.vips.inventories;

import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.items.ItemBuilder;
import blizzard.development.vips.utils.vips.VipUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VipChangeInventory {

    private static VipChangeInventory instance;
    private final VipUtils vipUtils = VipUtils.getInstance();
    YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();

    public void changeVipInventory(Player player) {
        int rows = config.getInt("vip-inventory.rows");
        String title = config.getString("vip-inventory.title");

        ChestGui inventory = new ChestGui(rows, title);

        StaticPane pane = new StaticPane(0, 0, 9, rows);

        List<Map<?, ?>> items = config.getMapList("vip-inventory.items");

        for (Map<?, ?> itemConfig : items) {
            String vipName = (String) itemConfig.get("vip-name");
            Material material = Material.valueOf((String) itemConfig.get("material"));
            String displayName = (String) itemConfig.get("display-name");
            List<String> lore = (List<String>) itemConfig.get("lore");
            int slot = (int) itemConfig.get("slot");

            ItemStack item = new ItemBuilder(material)
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .build();

            GuiItem guiItem = new GuiItem(item, event -> {
                event.setCancelled(true);
                changeVip(player, event, vipName);
            });

            pane.addItem(guiItem, Slot.fromIndex(slot));
        }

        inventory.addPane(pane);
        inventory.show(player);
    }

    private void changeVip(Player player, InventoryClickEvent event, String vip) {
        if (!vipUtils.hasVip(vip)) {
            player.sendMessage("§cVocê não tem esse vip!");
            event.setCancelled(true);
            return;
        }

        if (vipUtils.getActiveVip(player) == null) {
            vipUtils.setActiveVip(player, vip);
            return;
        }

        changeVip(player, vip);
        event.setCancelled(true);
    }

    private void changeVip(Player player, String vipName) {
        if (vipUtils.getActiveVip(player).equalsIgnoreCase(vipName)) {
            player.sendMessage("§cVocê já tem esse vip ativo!");
            return;
        }

        vipUtils.setActiveVip(player, vipName);
        player.sendMessage("§bVocê trocou para o vip " + vipName);
    }

    public static VipChangeInventory getInstance() {
        if (instance == null) {
            instance = new VipChangeInventory();
        }
        return instance;
    }
}
