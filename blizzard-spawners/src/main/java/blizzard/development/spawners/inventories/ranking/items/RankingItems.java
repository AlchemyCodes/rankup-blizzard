package blizzard.development.spawners.inventories.ranking.items;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.SkullAPI;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class RankingItems {
    private static RankingItems instance;

    public ItemStack spawners(String player, String type, int position, double amount) {
        ItemStack item = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player);
        ItemMeta meta = item.getItemMeta();

        String display = "§6Destaque #§l" + (position + 1);
        List<String> lore = Arrays.asList(
                "",
                " §7Nome: §f" + player,
                " §7" + type + ": §f" + NumberFormat.getInstance().formatNumber(amount),
                ""
        );

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack nothing(int position) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§6Destaque #§l" + (position + 1)));
        meta.setLore(List.of("§7Nenhuma informação."));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack filter(String type) {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aMudar Categoria"));
        meta.setLore(List.of("§7Clique para mudar para " + type));
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

    public static RankingItems getInstance() {
        if (instance == null) instance = new RankingItems();
        return instance;
    }
}
