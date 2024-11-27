package blizzard.development.spawners.inventories.friends.items;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FriendsItems {
    private static FriendsItems instance;

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    public ItemStack info(String id) {
        final SpawnersData data = manager.getSpawnerData(id);

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aInformações"));
        meta.setLore(Arrays.asList(
                "",
                "§fGerais:",
                "§8■ §7Dono:" + data.getNickname(),
                "§8■ §7Spawners:" + data.getAmount(),
                "§8■ §7Mobs:" + data.getMobAmount(),
                "§8■ §7Drops:" + 1,
                "",
                "§fEncantamentos:",
                "§8■ §7Velocidade:" + data.getSpeedLevel(),
                "§8■ §7Sortudo:" + data.getLuckyLevel(),
                "§8■ §7Experiente:" + data.getExperienceLevel(),
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
                "",
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
                "",
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
                "",
                "§7Visualize os jogadores",
                "§7que mais se destacam",
                "",
                "§6Clique para visualizar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static FriendsItems getInstance() {
        if (instance == null) instance = new FriendsItems();
        return instance;
    }
}
