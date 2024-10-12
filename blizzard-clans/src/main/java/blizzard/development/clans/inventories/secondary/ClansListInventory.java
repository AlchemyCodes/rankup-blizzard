package blizzard.development.clans.inventories.secondary;

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
import blizzard.development.clans.inventories.primary.DefaultInventory;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.NumberFormat;
import blizzard.development.clans.utils.skulls.LetterSkulls;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ClansListInventory {

    private static final int ITEMS_PER_PAGE = 7;

    public static void open(Player player, int page) {
        ChestGui inventory = new ChestGui(4, "§8Clans - Lista (Página " + (page + 1) + ")");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        List<ClansData> clans = ClansCacheManager.getAllClans();

        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, clans.size());

        for (int i = startIndex; i < endIndex; i++) {
            ClansData clan = clans.get(i);
            ItemStack item = clan(clan.getClan());
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
                    DefaultInventory.open(player);
                } else {
                    ClansInventory.open(player);
                }

            }
        });

        if (hasNextPage) {
            pane.addItem(backItem, Slot.fromIndex(30));
        } else {
            pane.addItem(backItem, Slot.fromIndex(31));
        }

        if (hasNextPage) {
            GuiItem laterItem = new GuiItem(later(), event -> {
                event.setCancelled(true);
                open(player, page + 1);
            });
            pane.addItem(laterItem, Slot.fromIndex(32));
        }

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }


    public static ItemStack clan(String clan) {
        ItemStack item = LetterSkulls.getSkullByName(clan);

        ClansData data = ClansMethods.getClan(clan);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String date = data.getCreationDate();
        String tag = data.getTag();
        String name = data.getName();
        String owner = data.getOwner();
        int members = ClansMethods.getMembersCount(clan);
        int maxmembers = ClansCacheManager.getMaxClanMembers(clan);
        long money = data.getMoney();
        double kdr = Double.parseDouble(decimalFormat.format(data.getKdr()));

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§7" + name + " - " + tag);
        meta.setLore(Arrays.asList(
                "",
                "§7 Criação: §a" + date,
                "§7 Nome: §a" + name,
                "§7 Tag: §a" + tag,
                "§7 Dono: §a" + owner,
                "§7 Membros: §a" + members + "§7/§a" + maxmembers,
                "§7 Saldo: §2$§a" + NumberFormat.formatNumber(money),
                "§7 KDR: §a" + kdr,
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
}
