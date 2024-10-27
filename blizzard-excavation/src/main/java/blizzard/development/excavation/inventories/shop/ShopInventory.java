package blizzard.development.excavation.inventories.shop;

import blizzard.development.excavation.api.FossilAPI;
import blizzard.development.excavation.builder.ItemBuilder;
import blizzard.development.excavation.inventories.ExcavationInventory;
import blizzard.development.excavation.utils.NumberFormat;
import blizzard.development.excavation.utils.PluginImpl;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ShopInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Loja de Itens");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        items(pane, "shop", PluginImpl.getInstance().Shop.getConfig(), player);

        GuiItem backward = new GuiItem(backwardButton(), event -> {
            ExcavationInventory excavationInventory = new ExcavationInventory();
            excavationInventory.open(player);
        });

        pane.addItem(backward, Slot.fromIndex(36));

        inventory.addPane(pane);
        inventory.show(player);

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
    }

    public ItemStack backwardButton() {
        return new ItemBuilder(Material.REDSTONE)
                .setDisplayName("§cVoltar")
                .setLore(List.of(
                        "§7Clique para voltar",
                        "§7ao menu anterior."
                ))
                .build();
    }

    public void items(StaticPane pane, String configPath, FileConfiguration config, Player player) {
        for (String item : config.getConfigurationSection(configPath).getKeys(false)) {

            boolean skull = config.getBoolean(configPath + "." + item + ".skull");
            String itemType = config.getString(configPath + "." + item + ".item");
            int itemSlot = config.getInt(configPath + "." + item + ".slot");
            String displayName = config.getString(configPath + "." + item + ".display-name").replace("&", "§");
            String command = config.getString(configPath + "." + item + ".command-executor").replace("{player}", player.getName());
            double cost = config.getDouble(configPath + "." + item + ".cost");

            List<String> lore = config.getStringList(configPath + "." + item + ".lore");
            List<String> replacedLore = lore.stream().map(s -> s.replace("&", "§").replace("{cost}", String.valueOf(NumberFormat.formatNumber(cost)))).collect(Collectors.toList());

            if (skull) {
                ItemStack itemStack = new ItemBuilder(itemType)
                        .setDisplayName(displayName)
                        .setLore(replacedLore)
                        .build();

                double balance = FossilAPI.getFossilBalance(player);

                GuiItem blockItem = new GuiItem(itemStack, event -> {

                    if (!(balance >= cost)) {
                        player.closeInventory();
                        player.sendActionBar("§c§lEI! §cVocê não possui §l" + NumberFormat.formatNumber(cost) + " §cfósseis para comprar!");
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

                        FossilAPI.removeFossil(player, cost);
                        open(player);
                    }

                    event.setCancelled(true);
                });
                pane.addItem(blockItem, Slot.fromIndex(itemSlot));

            } else {
                ItemStack itemStack = new ItemBuilder(Material.getMaterial(itemType))
                        .setDisplayName(displayName)
                        .setLore(replacedLore)
                        .build();

                double balance = FossilAPI.getFossilBalance(player);

                GuiItem blockItem = new GuiItem(itemStack, event -> {

                    if (!(balance >= cost)) {
                        player.closeInventory();
                        player.sendActionBar("§c§lEI! §cVocê não possui §l" + NumberFormat.formatNumber(cost) + " §cfósseis para comprar!");
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

                        FossilAPI.removeFossil(player, cost);
                        open(player);
                    }

                    event.setCancelled(true);
                });
                pane.addItem(blockItem, Slot.fromIndex(itemSlot));
            }
        }
    }
}
