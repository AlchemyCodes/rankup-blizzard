package blizzard.development.clans.inventories.secondary.ranking;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.skulls.LetterSkulls;

import java.util.Arrays;
import java.util.List;

public class MembersRankingInventory {

    private static final int ITEMS_PER_PAGE = 7;

    public static void open(Player player, int page) {
        ChestGui inventory = new ChestGui(4, "§8Clans - Destaques (Membros)");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        List<ClansData> clans = ClansCacheManager.getAllClansByMembers();

        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, clans.size());

        for (int i = startIndex; i < endIndex; i++) {
            ClansData clan = clans.get(i);
            ItemStack item = clan(clan.getClan(), i + 1);
            GuiItem guiItem = new GuiItem(item, event -> {
                event.setCancelled(true);
            });
            pane.addItem(guiItem, Slot.fromIndex(10 + (i - startIndex)));
        }

        boolean hasPreviousPage = page > 0;
        boolean hasNextPage = endIndex < clans.size();

        GuiItem backItem = new GuiItem(previous(), event -> {
            event.setCancelled(true);
            if (hasPreviousPage) {
                open(player, page - 1);
            } else {
                PlayersData data = ClansMethods.getUser(player);
                if (data.getClan() == null) {
                    player.getOpenInventory().close();
                } else {
                    ClansInventory.open(player);
                }
            }
        });

        GuiItem filterItem = new GuiItem(filter(), event -> {
            event.setCancelled(true);
            MoneyRankingInventory.open(player, 0);
        });

        GuiItem laterItem = new GuiItem(later(), event -> {
            event.setCancelled(true);
            open(player, page + 1);
        });

        if (hasNextPage) {
            pane.addItem(backItem, Slot.fromIndex(30));
            pane.addItem(filterItem, Slot.fromIndex(31));
            pane.addItem(laterItem, Slot.fromIndex(32));
        } else {
            pane.addItem(filterItem, Slot.fromIndex(32));
            pane.addItem(backItem, Slot.fromIndex(30));
        }

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack clan(String clan, int position) {
        ItemStack item = LetterSkulls.getSkullByName(clan);

        ClansData data = ClansMethods.getClan(clan);

        String tag = data.getTag();
        String name = data.getName();
        int members = ClansCacheManager.getMembersCount(clan);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Destaque §6#" + "§6§l" + position);
        meta.setLore(Arrays.asList(
                "",
                "§7 Clan: §f" + name + " - " + tag,
                "§7 Membros: §f" + members,
                ""
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack previous() {
        ItemStack item = new ItemStack(Material.RED_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cAnterior");
        meta.setLore(Arrays.asList(
                "§7Página anterior"
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack later() {
        ItemStack item = new ItemStack(Material.LIME_DYE);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aPróximo");
        meta.setLore(Arrays.asList(
                "§7Próxima página"
        ));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack filter() {
        ItemStack item = new ItemStack(Material.HOPPER);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eFiltrar");
        meta.setLore(Arrays.asList(
                "§7Clique para filtrar por saldo"
        ));
        item.setItemMeta(meta);

        return item;
    }
}
