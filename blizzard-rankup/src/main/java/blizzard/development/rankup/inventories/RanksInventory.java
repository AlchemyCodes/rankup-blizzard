package blizzard.development.rankup.inventories;

import blizzard.development.rankup.utils.NumberFormat;
import blizzard.development.rankup.utils.PluginImpl;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RanksInventory {
    private static final int[] SLOTS = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25 };
    private static final int MAX_ITEMS_PER_PAGE = SLOTS.length;

    public static void openRanksInventory(Player player) {
        YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
        Set<String> ranks = ranksConfig.getConfigurationSection("ranks").getKeys(false);
        List<String> orderedRanks = ranks.stream().sorted(Comparator.comparingInt(rank -> ranksConfig.getInt("ranks." + rank + ".order"))).collect(Collectors.toList());
        int totalPages = (int)Math.ceil(orderedRanks.size() / (double)MAX_ITEMS_PER_PAGE);
        createRanksPage(player, orderedRanks, ranksConfig, 0, totalPages);
    }

    private static void createRanksPage(Player player, List<String> orderedRanks, YamlConfiguration ranksConfig, int page, int totalPages) {
        ChestGui gui = new ChestGui(5, "Ranks - Página " + (page + 1));
        StaticPane pane = new StaticPane(0, 0, 9, 5);
        int start = page * MAX_ITEMS_PER_PAGE;
        int end = Math.min(start + MAX_ITEMS_PER_PAGE, orderedRanks.size());

        for (int i = start; i < end; i++) {
            String rank = orderedRanks.get(i);
            ItemStack rankItem = createRankItem(ranksConfig, rank);
            GuiItem guiItem = new GuiItem(rankItem, event -> event.setCancelled(true));
            pane.addItem(guiItem, Slot.fromIndex(SLOTS[i - start]));
        }

        if (page + 1 < totalPages) {
            ItemStack nextPageItem = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPageItem.getItemMeta();
            nextPageMeta.setDisplayName("§aPróxima página");
            nextPageItem.setItemMeta(nextPageMeta);

            GuiItem nextPage = new GuiItem(nextPageItem, event -> {
                event.setCancelled(true);
                createRanksPage(player, orderedRanks, ranksConfig, page + 1, totalPages);
            });
            pane.addItem(nextPage, Slot.fromIndex(44));
        }

        if (page > 0) {
            ItemStack previousPageItem = new ItemStack(Material.ARROW);
            ItemMeta previousPageMeta = previousPageItem.getItemMeta();
            previousPageMeta.setDisplayName("§cPágina anterior");
            previousPageItem.setItemMeta(previousPageMeta);

            GuiItem previousPage = new GuiItem(previousPageItem, event -> {
                event.setCancelled(true);
                createRanksPage(player, orderedRanks, ranksConfig, page - 1, totalPages);
            });
            pane.addItem(previousPage, Slot.fromIndex(36));
        }

        gui.addPane(pane);
        gui.show(player);
    }

    private static ItemStack createRankItem(YamlConfiguration config, String rank) {
        Material material = Material.valueOf(config.getString("ranks." + rank + ".item.type", "STONE").toUpperCase());
        int amount = config.getInt("ranks." + rank + ".item.amount", 1);
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            String displayName = config.getString("ranks." + rank + ".name");
            meta.setDisplayName("§6Rank §6§l" + displayName);
            List<String> lore = config.getStringList("ranks." + rank + ".lore").stream()
                    .map(line -> line.replace("{price}", NumberFormat.formatNumber(config.getInt("ranks." + rank + ".price"))))
                    .collect(Collectors.toList());
            if (!lore.isEmpty()) {
                meta.setLore(lore);
            } else {
                meta.setLore(Collections.singletonList("§7No lore available."));
            }
            item.setItemMeta(meta);
        }

        return item;
    }
}
