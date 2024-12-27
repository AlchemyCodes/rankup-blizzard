package blizzard.development.spawners.inventories.slaughterhouses.friends.items;

import blizzard.development.spawners.database.cache.getters.SlaughterhouseCacheGetters;
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
                "§7O jogador ´§f" + player + "§7´ está",
                "§7adicionado como amigo.",
                "",
                "§f Permissões:",
                "§8  §7Gerenciar estado.",
                "",
                "§eClique aqui para remover."
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
        final SlaughterhouseCacheGetters getters = SlaughterhouseCacheGetters.getInstance();

        boolean isFull = getters.getSlaughterhouseFriends(id).size() >= getters.getSlaughterhouseFriendsLimit(id);

        ItemStack item = new ItemStack(isFull ? Material.REDSTONE : Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();

        String loreField = isFull ? "§cO limite de amigos foi excedido." : "§aClique aqui para adicionar.";

        meta.displayName(TextAPI.parse(isFull ? "§cAdicionar Amigo" : "§aAdicionar Amigo"));
        meta.setLore(Arrays.asList(
                "§7Conceda permissões a um",
                "§7jogador neste gerador",
                "",
                " §fAmigos §8▶ §7" + getters.getSlaughterhouseFriends(id).size() + "/" + getters.getSlaughterhouseFriendsLimit(id),
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
