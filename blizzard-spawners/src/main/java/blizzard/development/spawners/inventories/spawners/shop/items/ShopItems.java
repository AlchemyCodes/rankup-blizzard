package blizzard.development.spawners.inventories.spawners.shop.items;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.SkullAPI;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ShopItems {
    private static ShopItems instance;

    public ItemStack spawner(String material, String displayName, List<String> lore, boolean released, String cost, String dropCost, String discount) {

        if (released) {
            ItemStack item = new ItemStack(Material.valueOf(material.toUpperCase()));
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(displayName);

                lore.replaceAll(line -> line
                        .replace("{cost}", cost)
                        .replace("{drop-cost}", dropCost)
                        .replace("{discount}", discount)
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
                        "§7Novos spawners serão",
                        "§7lançadas em breve."
                ));
            }

            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);

            return item;
        }
    }

    public ItemStack informations(Player player) {
        ItemStack item = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player.getName());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§aSuas informações");
        meta.setLore(Arrays.asList(
                "§7Visualize informações suas",
                "§7para realizar compras.",
                "",
                "§a Informações:",
                "§8 ■ §fCoins: §2$§f" + NumberFormat.getInstance().formatNumber(CurrenciesAPI.getInstance().getBalance(player, Currencies.COINS)),
                //"§8 ▶ §fSeu Limite: §7" + NumberFormat.getInstance().formatNumber(LimitMethods.getMachinesLimit(player)),
                ""
        ));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack comingSoon() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextAPI.parse("<#e00000>Em breve!<#e00000>"));
        meta.setLore(Arrays.asList(
                "§7Novos spawners serão",
                "§7lançadas em breve."
        ));

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

    public static ShopItems getInstance() {
        if (instance == null) instance = new ShopItems();
        return instance;
    }
}
