package blizzard.development.spawners.inventories.enchantments;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.inventories.spawners.SpawnersInventory;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EnchantmentsInventory {
    private static EnchantmentsInventory instance;

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    public void open(Player player, String id) {
        ChestGui inventory = new ChestGui(3, "§8Encantamentos");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem speedItem = new GuiItem(speed(player, id), event -> {
            event.setCancelled(true);
        });

        GuiItem luckyItem = new GuiItem(lucky(player, id), event -> {
            event.setCancelled(true);
        });

        GuiItem experienceItem = new GuiItem(experience(player, id), event -> {
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            SpawnersInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        pane.addItem(speedItem, Slot.fromIndex(11));
        pane.addItem(luckyItem, Slot.fromIndex(13));
        pane.addItem(experienceItem, Slot.fromIndex(15));
        pane.addItem(backItem, Slot.fromIndex(18));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public ItemStack speed(Player player, String id) {
        ItemStack item = new ItemStack(Material.HOPPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§bVelocidade §l" + 1));
        meta.setLore(Arrays.asList(
                "§7Diminua a velocidade de",
                "§7geração do seu spawner.",
                "",
                "§f Nível: §b" + 1 + "/" + "X",
                "§f Tempo: §b" + 1 + "§ls",
                "",
                "§bClique para melhorar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack lucky(Player player, String id) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§6Sortudo §l" + 1));
        meta.setLore(Arrays.asList(
                "§7Aumente suas chances",
                "§7de ganhar recompensas.",
                "§7ao matar mobs do spawner",
                "",
                "§f Nível: §6" + 1 + "/" + "X",
                "§f Chance: §6" + 1 + "§l%",
                "",
                "§6Clique para melhorar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack experience(Player player, String id) {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aExperiente §l" + 1));
        meta.setLore(Arrays.asList(
                "§7Aumente o ganho de",
                "§7experiência ao matar",
                "§7os mobs do spawner.",
                "",
                "§f Nível: §a" + 1 + "/" + "X",
                "§f Ganho: §a" + 1 + "§l%",
                "",
                "§aClique para melhorar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(Arrays.asList(
                "§7Clique aqui para voltar",
                "§7ao menu anterior."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static EnchantmentsInventory getInstance() {
        if (instance == null) instance = new EnchantmentsInventory();
        return instance;
    }
}
