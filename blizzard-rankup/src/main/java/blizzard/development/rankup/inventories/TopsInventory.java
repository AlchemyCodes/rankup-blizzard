package blizzard.development.rankup.inventories;

import blizzard.development.core.utils.items.SkullAPI;
import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopsInventory {
    public static void openTopInventory(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        int size = config.getInt("topsInventory.size");
        String title = config.getString("topsInventory.title");

        ChestGui inventory = new ChestGui(size, title);
        StaticPane pane = new StaticPane(0, 0, 9, size);

        List<PlayersData> topPlayers = PlayersCacheManager.getAllPlayersData().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPrestige(), p1.getPrestige()))
                .collect(Collectors.toList());

        int numberOfItems = Math.min(topPlayers.size(), 10);

        int[] itemSlots = {10, 11, 12, 13, 14, 15, 16, 21, 22, 23};

        for (int i = 0; i < numberOfItems; i++) {
            PlayersData playerData = topPlayers.get(i);

            ConfigurationSection inventoryConfig = config.getConfigurationSection("topsInventory.items.head");

            Material itemType = Material.valueOf(inventoryConfig.getString("material"));
            String displayName = inventoryConfig.getString("displayName").replace("{rank}", String.valueOf(i + 1));

            List<String> lore = new ArrayList<>();
            for (String line : inventoryConfig.getStringList("lore")) {
                lore.add(
                        line.replace("{nickname}", playerData.getNickname())
                                .replace("{prestige}", String.valueOf(playerData.getPrestige()))
                );
            }

            ItemStack ranking = SkullAPI.withName(new ItemStack(itemType), playerData.getNickname());
            ItemMeta meta = ranking.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            ranking.setItemMeta(meta);

            GuiItem rankingItem = new GuiItem(ranking, event -> {
                event.setCancelled(true);
            });

            pane.addItem(rankingItem, Slot.fromIndex(itemSlots[i]));
        }

        GuiItem informationItem = new GuiItem(informations(player), event -> {
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(back(), event -> {
            RankInventory.openRankInventory(player);
            event.setCancelled(true);
        });

        pane.addItem(informationItem, Slot.fromIndex(41));
        pane.addItem(backItem, Slot.fromIndex(27));

        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);
    }

    public static ItemStack back() {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection backConfig = config.getConfigurationSection("topsInventory.items.back");

        Material material = Material.valueOf(backConfig.getString("material"));
        String displayName = backConfig.getString("displayName");
        List<String> lore = backConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack informations(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();

        PlayersData playersData = PlayersCacheManager.getPlayerData(player);
        String currentRank = playersData.getRank();
        int prestige = playersData.getPrestige();

        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        ConfigurationSection infoConfig = config.getConfigurationSection("topsInventory.items.information");

        Material material = Material.valueOf(infoConfig.getString("material"));
        String displayName = infoConfig.getString("displayName");
        List<String> lore = infoConfig.getStringList("lore").stream()
                .map(line -> line.replace("{current_rank}", RanksUtils.getCurrentRank(ranksConfig, currentRank))
                        .replace("{next_rank}", RanksUtils.getNextRank(ranksConfig, currentRankSection) != null ? RanksUtils.getNextRank(ranksConfig, currentRankSection) : "Nenhum")
                        .replace("{prestige}", String.valueOf(prestige))
                        .replace("{next_prestige}", String.valueOf(prestige + 1))
                        .replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCoinsCostAdd(prestige))))
                .collect(Collectors.toList());

        ItemStack info = new ItemStack(material);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        info.setItemMeta(meta);

        return info;
    }
}
