package blizzard.development.rankup.inventories;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.tasks.AutoRankup;
import blizzard.development.rankup.utils.NumberFormat;
import blizzard.development.rankup.utils.PluginImpl;
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

public class ConfirmationInventory {
    public static void openConfirmationInventory(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        int size = config.getInt("confirmationInventory.size");
        String title = config.getString("confirmationInventory.title");

        ChestGui gui = new ChestGui(size, title);
        StaticPane pane = new StaticPane(0, 0, 9, size);

        GuiItem information = new GuiItem(information(player), event -> event.setCancelled(true));
        GuiItem confirm = new GuiItem(confirm(), event -> {
            event.setCancelled(true);
            processRankUp(player);
            player.closeInventory();
        });
        GuiItem deny = new GuiItem(deny(), event -> {
            event.setCancelled(true);
            RankInventory.openRankInventory(player);
        });

        pane.addItem(confirm, Slot.fromIndex(11));
        pane.addItem(information, Slot.fromIndex(13));
        pane.addItem(deny, Slot.fromIndex(15));

        gui.addPane(pane);
        gui.show(player);
    }

    private static ItemStack confirm() {
        return buildItem("confirmationInventory.items.confirm");
    }

    private static ItemStack deny() {
        return buildItem("confirmationInventory.items.deny");
    }

    private static ItemStack information(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();

        String currentRank = playersData.getRank(player);
        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        ConfigurationSection infoConfig = config.getConfigurationSection("confirmationInventory.items.information");

        List<String> lore = infoConfig.getStringList("lore").stream()
                .map(line -> line.replace("{current_rank}", Objects.requireNonNull(RanksUtils.getCurrentRankTag(ranksConfig, currentRank)))
                        .replace("{next_rank}", RanksUtils.getNextRankTag(ranksConfig, currentRankSection) != null ?
                                Objects.requireNonNull(RanksUtils.getNextRankTag(ranksConfig, currentRankSection)) : "§cMáximo!"))
                .collect(Collectors.toList());

        return new ItemBuilder(Material.valueOf(infoConfig.getString("material")))
                .setDisplayName(infoConfig.getString("displayName"))
                .setLore(lore)
                .build();
    }

    private static ItemStack buildItem(String path) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection section = config.getConfigurationSection(path);

        return new ItemBuilder(Material.valueOf(section.getString("material")))
                .setDisplayName(section.getString("displayName"))
                .setLore(section.getStringList("lore"))
                .build();
    }

    public static void processRankUp(Player player) {
        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        String currentRank = playersData.getRank(player);
        int prestigeLevel = playersData.getPrestige(player);

        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        ConfigurationSection nextRankSection = RanksUtils.getNextRankSection(ranksConfig, RanksUtils.getCurrentRankSection(ranksConfig, currentRank));

        if (nextRankSection == null) {
            sendActionMessage(player, messagesConfig, "chat.max-rank", nextRankSection, prestigeLevel);
            return;
        }

        if (!canRankUp(player, nextRankSection, prestigeLevel)) {
            if (AutoRankup.autoRankUp.containsKey(player)) {
                return;
            }
            sendMessage(player, messagesConfig, "chat.no-quantity-for-rank-up", nextRankSection, prestigeLevel);
            return;
        }

        applyRankUp(player, playersData, nextRankSection, messagesConfig, prestigeLevel);
    }

    private static boolean canRankUp(Player player, ConfigurationSection nextRankSection, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        return currenciesAPI.getBalance(player, Currencies.COINS) >= RanksUtils.getRankUpCoinsPrice(nextRankSection, prestigeLevel) &&
                currenciesAPI.getBalance(player, Currencies.FLAKES) >= RanksUtils.getRankUpFlakesPrice(nextRankSection, prestigeLevel);
    }

    private static void applyRankUp(Player player, PlayersCacheMethod playersData, ConfigurationSection nextRankSection, YamlConfiguration messagesConfig, int prestigeLevel) {
        String nextRankName = nextRankSection.getString("name");
        playersData.setRank(player, nextRankName);
        executeRankUpCommand(player, nextRankSection);
        sendActionMessage(player, messagesConfig, "chat.rank-up", nextRankSection, prestigeLevel);
    }

    private static void executeRankUpCommand(Player player, ConfigurationSection nextRankSection) {
        String rankUpCommand = nextRankSection.getString("rankup.command");
        if (rankUpCommand != null && !rankUpCommand.isEmpty()) {
            player.performCommand(rankUpCommand);
        }
    }

    private static void sendMessage(Player player, YamlConfiguration messagesConfig, String path, ConfigurationSection nextRankSection, int prestigeLevel) {
        messagesConfig.getStringList(path).forEach(message ->
                player.sendMessage(message
                        .replace("{money}", NumberFormat.formatNumber(Math.max(0, RanksUtils.missingRankMoney(player, nextRankSection, prestigeLevel))))
                        .replace("{flakes}", NumberFormat.formatNumber(Math.max(0, RanksUtils.missingRankFlakes(player, nextRankSection, prestigeLevel))))
                        .replace("{rank}", PlayersCacheMethod.getInstance().getRank(player))));
    }

    private static void sendActionMessage(Player player, YamlConfiguration messagesConfig, String path, ConfigurationSection nextRankSection, int prestigeLevel) {
        String message = messagesConfig.getString(path);
        if (message != null) {
            player.sendActionBar(message
                    .replace("{money}", NumberFormat.formatNumber(Math.max(0, RanksUtils.missingRankMoney(player, nextRankSection, prestigeLevel))))
                    .replace("{flakes}", NumberFormat.formatNumber(Math.max(0, RanksUtils.missingRankFlakes(player, nextRankSection, prestigeLevel))))
                    .replace("{rank}", PlayersCacheMethod.getInstance().getRank(player)));
        }
    }
}
