package blizzard.development.rankup.inventories;

import blizzard.development.rankup.utils.NumberFormat;
import blizzard.development.rankup.utils.PluginImpl;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;

import java.util.*;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RanksInventory {

    public static void openRanksInventory(Player player) {
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        Set<String> ranks = ranksConfig.getConfigurationSection("ranks").getKeys(false);


        Map<Integer, List<String>> ranksByPage = ranks.stream()
                .collect(Collectors.groupingBy(rank -> ranksConfig.getInt("ranks." + rank + ".gui.page")));

        createRanksPage(player, ranksByPage, ranksConfig, 0);
    }

    private static void createRanksPage(Player player, Map<Integer, List<String>> ranksByPage, YamlConfiguration ranksConfig, int currentPage) {
        ChestGui gui = new ChestGui(5, "Ranks - Página " + (currentPage + 1));
        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<String> currentRanks = ranksByPage.getOrDefault(currentPage, Collections.emptyList());

        for (String rank : currentRanks) {
            int slot = ranksConfig.getInt("ranks." + rank + ".gui.slot", -1);
            if (slot < 0 || slot >= 45) continue; // Slots inválidos são ignorados

            ItemStack rankItem = createRankItem(ranksConfig, rank);
            GuiItem guiItem = new GuiItem(rankItem, event -> event.setCancelled(true));
            pane.addItem(guiItem, Slot.fromIndex(slot));
        }

        // Botão para próxima página
        if (ranksByPage.containsKey(currentPage + 1)) {
            ItemStack nextPageItem = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPageItem.getItemMeta();
            nextPageMeta.setDisplayName("§aPróxima página");
            nextPageItem.setItemMeta(nextPageMeta);

            GuiItem nextPage = new GuiItem(nextPageItem, event -> {
                event.setCancelled(true);
                createRanksPage(player, ranksByPage, ranksConfig, currentPage + 1);
            });
            pane.addItem(nextPage, Slot.fromIndex(44));
        }

        // Botão para página anterior
        if (currentPage > 0) {
            ItemStack previousPageItem = new ItemStack(Material.ARROW);
            ItemMeta previousPageMeta = previousPageItem.getItemMeta();
            previousPageMeta.setDisplayName("§cPágina anterior");
            previousPageItem.setItemMeta(previousPageMeta);

            GuiItem previousPage = new GuiItem(previousPageItem, event -> {
                event.setCancelled(true);
                createRanksPage(player, ranksByPage, ranksConfig, currentPage - 1);
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
                    .map(line -> line.replace("{price}", NumberFormat.formatNumber(config.getInt("ranks." + rank + ".coinsPrice"))))
                    .map(line -> line.replace("{flakes}", NumberFormat.formatNumber(config.getInt("ranks." + rank + ".flakesPrice"))))
                    .collect(Collectors.toList());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }
}
