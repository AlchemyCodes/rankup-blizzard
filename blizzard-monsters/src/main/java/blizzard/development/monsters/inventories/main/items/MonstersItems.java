package blizzard.development.monsters.inventories.main.items;

import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MonstersItems {
    private static MonstersItems instance;

    public ItemStack manage() {
        ItemStack item = new ItemStack(Material.SNOW_GOLEM_SPAWN_EGG);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§bGerenciar monstros"));
        meta.setLore(Arrays.asList(
                "§7Gerencie seus monstros",
                "",
                "§bClique para visualizar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack rewards() {
        ItemStack item = new ItemStack(Material.HOPPER_MINECART);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aRecompensas"));
        meta.setLore(Arrays.asList(
                "§7Visualize tudo o que você",
                "§7adquiriu ao derrotar monstros",
                "",
                "§aClique para visualizar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack go(boolean state) {
        ItemStack item;

        if (state) {
            item = new ItemStack(Material.REDSTONE_TORCH);
            ItemMeta meta = item.getItemMeta();

            meta.displayName(TextAPI.parse("§cSair do mundo"));
            meta.setLore(Arrays.asList(
                    "§7Tire uma folga, saia",
                    "§7do mundo de monstros",
                    "",
                    "§cClique para sair."
            ));

            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        } else {
            item = new ItemStack(Material.COMPASS);
            ItemMeta meta = item.getItemMeta();

            meta.displayName(TextAPI.parse("§aIr ao mundo"));
            meta.setLore(Arrays.asList(
                    "§7Crie coragem e vá à",
                    "§7caça de monstros",
                    "",
                    "§aClique para ir."
            ));

            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }

        return item;
    }

    public ItemStack ranking() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§eClassificação"));
        meta.setLore(Arrays.asList(
                "§7Confira os jogadores que",
                "§7mais se destacam no servidor",
                "",
                "§eClique para visualizar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack sword() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§6Aniquiladora §l✂"));
        meta.setLore(Arrays.asList(
                "§7Use esta espada para",
                "§7aniquilar os monstros.",
                "",
                " §fDano: §c❤2.0",
                " §fExperiente: §a★1",
                "",
                "§6Clique para resgatar."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static MonstersItems getInstance() {
        if (instance == null) instance = new MonstersItems();
        return instance;
    }
}
