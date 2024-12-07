package blizzard.development.spawners.inventories.friends.items;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.utils.items.SkullAPI;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class FriendsItems {
    private static FriendsItems instance;

    public ItemStack friends(String player, int position) {
        ItemStack item = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player);
        ItemMeta meta = item.getItemMeta();

        String display = "§eAmigo #§l" + (position + 1);
        List<String> lore = Arrays.asList(
                "",
                "§7Nome: §f" + player,
                "",
                "§cClique aqui para remover."
        );

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack nothing(int position) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§eAmigo #§l" + (position + 1)));
        meta.setLore(List.of("§7Nenhuma informação."));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack manage(String id) {
        final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();

        boolean isFull = getters.getSpawnerFriends(id).size() >= getters.getSpawnerFriendsLimit(id);

        ItemStack item = new ItemStack(isFull ? Material.REDSTONE : Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();

        String loreField = isFull ? "§cO limite de amigos foi excedido." : "§aClique aqui para adicionar.";

        meta.displayName(TextAPI.parse(isFull ? "§cAdicionar Amigo" : "§aAdicionar Amigo"));
        meta.setLore(Arrays.asList(
                "§7Conceda permissões a um",
                "§7jogador neste gerador",
                "",
                " §fAmigos §8▶ §7" + getters.getSpawnerFriends(id).size() + "/" + getters.getSpawnerFriendsLimit(id),
                "",
                loreField
        ));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para sair."));
        item.setItemMeta(meta);
        return item;
    }

    public static FriendsItems getInstance() {
        if (instance == null) instance = new FriendsItems();
        return instance;
    }
}
