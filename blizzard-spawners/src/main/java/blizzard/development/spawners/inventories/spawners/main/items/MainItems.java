package blizzard.development.spawners.inventories.spawners.main.items;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MainItems {
    private static MainItems instance;

    private final SpawnersCacheManager manager = SpawnersCacheManager.getInstance();

    public ItemStack info(String id) {
        final SpawnersData data = manager.getSpawnerData(id);

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextAPI.parse("§aInformações"));
        meta.setLore(Arrays.asList(
                "§7Confira de antemão algumas",
                "§7informações sobre o seu gerador",
                "",
                "§f Estado: " + SpawnersUtils.getInstance().getSpawnerState(States.valueOf(data.getState().toUpperCase())),
                "§f Dono: §7" + data.getNickname(),
                "§f Tipo: §7" + SpawnersUtils.getInstance().getMobNameByData(data),
                "",
                "§a Encantamentos:",
                " §7 Velocidade §l" + data.getSpeedLevel(),
                " §7 Sortudo §l" + data.getLuckyLevel(),
                " §7 Experiente §l" + data.getExperienceLevel(),
                "",
                "§aClique para alterar o estado."
        ));
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack enchantments() {
        ItemStack item = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§dEncantamentos"));
        meta.setLore(Arrays.asList(
                "§7Verifique os encantamentos",
                "§7disponíveis para o gerador",
                "",
                "§dClique para verificar."
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
                "§7do seu gerador",
                "",
                "§b Armazenados:",
                "§8  ▶ §7" + format.formatNumber(data.getDrops()) + " §fDrop(s).",
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

    public static MainItems getInstance() {
        if (instance == null) instance = new MainItems();
        return instance;
    }
}
