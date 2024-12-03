package blizzard.development.spawners.inventories.drops.items;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DropsItems {
    private static DropsItems instance;

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    public ItemStack bonus() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextAPI.parse("§aTorne-se VIP"));
        meta.setLore(Arrays.asList(
                "",
                ""
        ));
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack drops(String id) {
        final SpawnersUtils utils = SpawnersUtils.getInstance();

        SpawnersData data = manager.getSpawnerData(id);

        ItemStack item = utils.getDropsItem(utils.getSpawnerFromName(data.getType()));
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse(utils.getSpawnerColor(utils.getSpawnerFromName(data.getType())) + "Drops"));
        meta.setLore(Arrays.asList(
                "",
                ""
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack autoSell() {
        ItemStack item = new ItemStack(Material.REPEATER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVenda Automática"));
        meta.setLore(Arrays.asList(
                "",
                ""
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cSair"));
        meta.setLore(Arrays.asList(

        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static DropsItems getInstance() {
        if (instance == null) instance = new DropsItems();
        return instance;
    }
}
