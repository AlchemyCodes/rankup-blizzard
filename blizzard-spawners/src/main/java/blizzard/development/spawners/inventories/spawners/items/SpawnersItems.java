package blizzard.development.spawners.inventories.spawners.items;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SpawnersItems {
    private static SpawnersItems instance;

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    public ItemStack info(String id) {
        final SpawnersData data = manager.getSpawnerData(id);
        final NumberFormat format = NumberFormat.getInstance();

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextAPI.parse("§aInformações"));
        meta.setLore(Arrays.asList(
                "",
                "§f Gerais:",
                "§8 ■ §7Estado§8:§7 " + SpawnersUtils.getInstance().getSpawnerState(States.valueOf(data.getState().toUpperCase())),
                "§8 ■ §7Dono§8:§7 " + data.getNickname(),
                "§8 ■ §7Tipo§8:§7 " + SpawnersUtils.getInstance().getMobNameByData(data),
                "§8 ■ §7Spawners§8:§7 " + format.formatNumber(data.getAmount()),
                "§8 ■ §7Mobs§8:§7 " + format.formatNumber(data.getMobAmount()),
                "§8 ■ §7Drops§8:§7 " + format.formatNumber(data.getDrops()),
                "",
                "§f Encantamentos:",
                "§8 ■ §7Velocidade§8:§7 " + data.getSpeedLevel(),
                "§8 ■ §7Sortudo§8:§7 " + data.getLuckyLevel(),
                "§8 ■ §7Experiente§8:§7 " + data.getExperienceLevel(),
                "",
                "§aClique aqui para alterar o estado."
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
                "§7disponíveis para o spawner",
                "",
                "§5Clique para verificar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack drops(String id) {
        final SpawnersData data = manager.getSpawnerData(id);
        final NumberFormat format = NumberFormat.getInstance();

        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§bDrops"));
        meta.setLore(Arrays.asList(
                "§7Gerencie os drops",
                "§7do seu spawner",
                "",
                "§f Armazenados:",
                "§8 ▶ §7" + format.formatNumber(data.getDrops()),
                "",
                "§bClique para gerenciar."
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

    public static SpawnersItems getInstance() {
        if (instance == null) instance = new SpawnersItems();
        return instance;
    }
}
