package blizzard.development.monsters.inventories.cage.items;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.inventories.tools.items.RadarItems;
import blizzard.development.monsters.monsters.handlers.eggs.MonstersEggHandler;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CageItems {
    private static CageItems instance;

    public ItemStack monster(String monster) {
        MonstersEggHandler eggHandler = MonstersEggHandler.getInstance();

        Material material;
        if (eggHandler.getMaterial(monster) != null) {
            material = Material.getMaterial(eggHandler.getMaterial(monster));
        } else {
            material = Material.EGG;
        }

        ItemStack item = new ItemBuilder(material).build();
        ItemMeta meta = item.getItemMeta();

        String display;
        if (eggHandler.getDisplayName(monster) != null) {
            display = eggHandler.getDisplayName(monster);
        } else {
            display = "§bMonstro corrompido";
        }

        meta.displayName(TextAPI.parse(display));
        meta.setLore(Arrays.asList(
                "§7Libere esse monstro.",
                "",
                "§f Vai ter algo aqui",
                "",
                "§7Clique para liberar."
        ));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack catchAll() {
        ItemStack item = new ItemStack(Material.HOPPER_MINECART);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aCapturar todos");
        meta.setLore(Arrays.asList(
                "§7Capture todos os monstros",
                "§7que estão presos na gaiola",
                "",
                "§aClique para capturar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack nothing() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§7Nenhum monstro"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack previous() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para voltar."));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack next() {
        ItemStack item = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aAvançar"));
        meta.setLore(List.of("§7Clique aqui para avançar."));
        item.setItemMeta(meta);
        return item;
    }

    public static CageItems getInstance() {
        if (instance == null) instance = new CageItems();
        return instance;
    }
}
