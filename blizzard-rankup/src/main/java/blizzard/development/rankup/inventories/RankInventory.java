package blizzard.development.rankup.inventories;

import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.tasks.AutoRankup;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import blizzard.development.rankup.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RankInventory {

    public static void openRankInventory(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();

        int size = config.getInt("rankInventory.size");
        String title = config.getString("rankInventory.title", "Rank");

        ChestGui gui = new ChestGui(size, title);
        StaticPane pane = new StaticPane(0, 0, 9, size);

        pane.addItem(new GuiItem(information(player), event -> event.setCancelled(true)),
                Slot.fromIndex(config.getInt("rankInventory.items.information.slot")));

        pane.addItem(new GuiItem(ranks(), event -> {
            event.setCancelled(true);
            RanksInventory.openRanksInventory(player);
        }), Slot.fromIndex(config.getInt("rankInventory.items.ranks.slot")));

        pane.addItem(new GuiItem(tops(), event -> {
            event.setCancelled(true);
            TopsInventory.openTopInventory(player);
        }), Slot.fromIndex(config.getInt("rankInventory.items.tops.slot")));

        pane.addItem(new GuiItem(rankup(), event -> {
            event.setCancelled(true);
            ConfirmationInventory.openConfirmationInventory(player);
        }), Slot.fromIndex(config.getInt("rankInventory.items.rankup.slot")));

        pane.addItem(new GuiItem(autoRankUp(AutoRankup.autoRankUp.containsKey(player)), event -> {
            event.setCancelled(true);
            if (!AutoRankup.autoRankUp.containsKey(player)) {
                AutoRankup.autoRankUp.put(player, true);
                openRankInventory(player);
            } else {
                AutoRankup.autoRankUp.remove(player);
                openRankInventory(player);
            }
        }), Slot.fromIndex(config.getInt("rankInventory.items.auto-rankup.active.slot")));

        gui.addPane(pane);
        gui.show(player);
    }

    private static ItemStack information(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();

        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        String currentRank = playersData.getRank(player);
        int prestige = playersData.getPrestige(player);

        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        ConfigurationSection infoConfig = config.getConfigurationSection("rankInventory.items.information");

        assert infoConfig != null;

        return new ItemBuilder(Material.valueOf(infoConfig.getString("material")))
                .setDisplayName(infoConfig.getString("displayName"))
                .setLore(infoConfig.getStringList("lore").stream().map(line -> line
                        .replace("{current_rank}", Objects.requireNonNull(RanksUtils.getCurrentRankTag(ranksConfig, currentRank)))
                        .replace("{next_rank}", RanksUtils.getNextRankTag(ranksConfig, currentRankSection) != null ?
                                Objects.requireNonNull(RanksUtils.getNextRankTag(ranksConfig, currentRankSection)) : "§cMáximo!")
                        .replace("{prestige}", String.valueOf(prestige))
                        .replace("{next_prestige}", String.valueOf(prestige + 1))
                        .replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCoinsCostAdd(prestige)))
                ).collect(Collectors.toList()))
                .build();
    }

    private static ItemStack ranks() {
        return createItemFromConfig("rankInventory.items.ranks");
    }

    private static ItemStack tops() {
        return createItemFromConfig("rankInventory.items.tops");
    }

    private static ItemStack rankup() {
        return createItemFromConfig("rankInventory.items.rankup");
    }

    private static ItemStack autoRankUp(boolean isAutoRankUpActive) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection autoRankupConfig = config.getConfigurationSection("rankInventory.items.auto-rankup");

        assert autoRankupConfig != null;

        String state = isAutoRankUpActive ? "deactivated" : "active";
        ConfigurationSection stateConfig = autoRankupConfig.getConfigurationSection(state);

        return new ItemBuilder(Material.valueOf(stateConfig.getString("material")))
                .setDisplayName(stateConfig.getString("displayName"))
                .setLore(stateConfig.getStringList("lore"))
                .build();
    }

    private static ItemStack createItemFromConfig(String path) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection itemConfig = config.getConfigurationSection(path);

        assert itemConfig != null;

        return new ItemBuilder(Material.valueOf(itemConfig.getString("material")))
                .setDisplayName(itemConfig.getString("displayName"))
                .setLore(itemConfig.getStringList("lore"))
                .build();
    }
}
