package blizzard.development.spawners.inventories.slaughterhouses.views.items;

import blizzard.development.spawners.builders.skulls.SkullBuilder;
import blizzard.development.spawners.utils.TimeConverter;
import blizzard.development.spawners.utils.items.SkullAPI;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class SlaughterhouseItems {
    private static SlaughterhouseItems instance;

    public ItemStack slaughterhouse(String value, String displayName, List<String> lore, boolean released, int cooldown, int area, int looting) {

        if (released) {
            ItemStack item = SkullAPI.withBase64(new ItemStack(Material.PLAYER_HEAD), value);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(displayName);

                lore.replaceAll(line -> line
                        .replace("{cooldown}", TimeConverter.convertSecondsToTimeFormat(cooldown))
                        .replace("{area}", String.valueOf(area))
                        .replace("{looting}", String.valueOf(looting))
                );

                meta.setLore(lore);
            }

            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);

            return item;
        } else {
            ItemStack item = new ItemStack(Material.BARRIER);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.displayName(TextAPI.parse("<#e00000>Em breve!<#e00000>"));
                meta.setLore(Arrays.asList(
                        "§7Novos abatedouros serão",
                        "§7lançadas em breve."
                ));
            }

            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);

            return item;
        }
    }

    public ItemStack comingSoon() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextAPI.parse("<#e00000>Em breve!<#e00000>"));
        meta.setLore(Arrays.asList(
                "§7Novos abatedouros serão",
                "§7lançadas em breve."
        ));

        item.setItemMeta(meta);
        return item;
    }

    public static SlaughterhouseItems getInstance() {
        if (instance == null) instance = new SlaughterhouseItems();
        return instance;
    }
}
