package blizzard.development.spawners.inventories.drops.items;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.utils.NumberFormat;
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
                "§7Sabia que os jogadores §aVIP's",
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
        final SpawnersHandler handler = SpawnersHandler.getInstance();
        final NumberFormat format = NumberFormat.getInstance();

        SpawnersData data = manager.getSpawnerData(id);

        ItemStack item = utils.getDropsItem(utils.getSpawnerFromName(data.getType()));
        ItemMeta meta = item.getItemMeta();

        String color = utils.getSpawnerColor(utils.getSpawnerFromName(data.getType()));
        String dropsDisplay = utils.getSpawnerDrops(utils.getSpawnerFromName(data.getType()));
        String drops = format.formatNumber(data.getDrops());

        String unitValue = format.formatNumber(
                handler.getSellDropPrice(utils.getSpawnerFromName(data.getType()).toString().toLowerCase())
        );
        String totalValue = (format.formatNumber
                (data.getDrops() * handler.getSellDropPrice(utils.getSpawnerFromName(data.getType()).toString().toLowerCase()))
        );

        meta.displayName(TextAPI.parse("§f" +  drops + "x " + color + dropsDisplay));
        meta.setLore(Arrays.asList(
                "§7Verifique agora os",
                "§7drops do seu gerador.",
                "",
                color + " Drops",
                "§f  Armazenados: §7" +  drops,
                "§f  Bônus de venda: §a" + 0 + "%",
                "",
                " §7Algumas informações",
                " §7sobre este gerador.",
                "",
                "§f Valor unitário: §2§l$§a" + unitValue,
                "§f Valor total: §2§l$§a" + totalValue,
                "",
                color + "Clique para vender."
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
                "§8  ▶ §a" + "Ligado",
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
