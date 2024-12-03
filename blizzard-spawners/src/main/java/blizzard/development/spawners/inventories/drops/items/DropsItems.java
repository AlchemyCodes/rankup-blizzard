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
                "§7Sabia que os jogadores §aVIP",
                "§7possuem um bônus adicional",
                "§7ao vender seus drops?",
                "",
                "§f Visite nosso site agora",
                "§f e torne-se §aVIP §ftambém!",
                "",
                "§aClique aqui para visitar."
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

        String color = utils.getSpawnerColor(utils.getSpawnerFromName(data.getType()));

        meta.displayName(TextAPI.parse(color + "Drops"));
        meta.setLore(Arrays.asList(
                "§7Verifique os drops do gerador",
                "",
                color + " Informações:",
                "§7 Armazenados §l" + 100  ,
                "§7 Valor Unitário §l" + 1 ,
                "§7 Valor Total §l" + 100 ,
                "",
                color + "Clique para vender os drops."
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
                "§7Gerencie a venda automática",
                "§7dos drops do seu gerador",
                "",
                "§f Estado de Venda:",
                "§8 ▶ §a" + "Ligado",
                "",
                "§cClique para alternar o estado."
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

    public static DropsItems getInstance() {
        if (instance == null) instance = new DropsItems();
        return instance;
    }
}
