package blizzard.development.spawners.inventories.spawners;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.NumberFormat;
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

public class SpawnersInventory {
    private static SpawnersInventory instance;

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    public void open(Player player, String id) {
        ChestGui inventory = new ChestGui(3, "§8Gerenciar Gerador");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem infoItem = new GuiItem(info(id), event -> {
            event.setCancelled(true);
        });

        GuiItem enchantmentsItem = new GuiItem(enchantments(), event -> {
            event.setCancelled(true);
        });

        GuiItem friendsItem = new GuiItem(friends(), event -> {
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(ranking(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(10));
        pane.addItem(enchantmentsItem, Slot.fromIndex(12));
        pane.addItem(friendsItem, Slot.fromIndex(14));
        pane.addItem(rankingItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public ItemStack info(String id) {
        final SpawnersData data = manager.getSpawnerData(id);
        final NumberFormat format = NumberFormat.getInstance();

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aInformações"));
        meta.setLore(Arrays.asList(
                "",
                "§f Gerais:",
                "§8 ■ §7Dono§8:§7 " + data.getNickname(),
                "§8 ■ §7Spawners§8:§7 " + format.formatNumber(data.getAmount()),
                "§8 ■ §7Mobs§8:§7 " + format.formatNumber(data.getMobAmount()),
                "§8 ■ §7Drops§8:§7 " + format.formatNumber(1),
                "",
                "§f Encantamentos:",
                "§8 ■ §7Velocidade§8:§7 " + data.getSpeedLevel(),
                "§8 ■ §7Sortudo§8:§7 " + data.getLuckyLevel(),
                "§8 ■ §7Experiente§8:§7 " + data.getExperienceLevel(),
                ""
        ));
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack enchantments() {
        ItemStack item = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§5Encantamentos"));
        meta.setLore(Arrays.asList(
                "§7Verifique os encantamentos",
                "§7disponíveis para o spawners",
                "",
                "§5Clique para verificar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack friends() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§eAmigos"));
        meta.setLore(Arrays.asList(
                "§7Gerencie suas amizades e",
                "§7suas respectivas permissões",
                "",
                "§eClique para gerenciar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack ranking() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§6Destaques"));
        meta.setLore(Arrays.asList(
                "§7Visualize os jogadores",
                "§7que mais se destacam",
                "",
                "§6Clique para visualizar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static SpawnersInventory getInstance() {
        if (instance == null) instance = new SpawnersInventory();
        return instance;
    }
}
