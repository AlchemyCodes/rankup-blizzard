package blizzard.development.rankup.inventories;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.utils.NumberFormat;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        GuiItem information = new GuiItem(information(player), event -> { event.setCancelled(true);});

        GuiItem confirm = new GuiItem(confirm(), event -> { event.setCancelled(true);
            processRankUp(player);});

        GuiItem deny = new GuiItem(deny(), event -> { event.setCancelled(true);
            RankInventory.openRankInventory(player);});

        pane.addItem(confirm, Slot.fromIndex(11));
        pane.addItem(information, Slot.fromIndex(13));
        pane.addItem(deny, Slot.fromIndex(15));

        gui.addPane(pane);
        gui.show(player);
    }

    public static ItemStack confirm() {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection inventoryConfig = config.getConfigurationSection("confirmationInventory.items.confirm");

        Material material = Material.valueOf(inventoryConfig.getString("material"));
        String displayName = inventoryConfig.getString("displayName");
        List<String> lore = inventoryConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack information(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();

        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        String currentRank = playersData.getRank(player);

        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        ConfigurationSection infoConfig = config.getConfigurationSection("confirmationInventory.items.information");

        assert infoConfig != null;
        Material material = Material.valueOf(infoConfig.getString("material"));
        String displayName = infoConfig.getString("displayName");
        List<String> lore = infoConfig.getStringList("lore").stream()
                .map(line -> {
                    assert currentRankSection != null;
                    return line.replace("{current_rank}", Objects.requireNonNull(RanksUtils.getCurrentRankName(ranksConfig, currentRank)))
                            .replace("{next_rank}", RanksUtils.getNextRank(ranksConfig, currentRankSection)
                                    != null ? Objects.requireNonNull(RanksUtils.getNextRank(ranksConfig, currentRankSection)) : "Nenhum");
                })
                .collect(Collectors.toList());

        ItemStack info = new ItemStack(material);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        info.setItemMeta(meta);

        return info;
    }

    public static ItemStack deny() {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection inventoryConfig = config.getConfigurationSection("confirmationInventory.items.deny");

        assert inventoryConfig != null;
        Material material = Material.valueOf(inventoryConfig.getString("material"));
        String displayName = inventoryConfig.getString("displayName");
        List<String> lore = inventoryConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }

    public static void processRankUp(Player player) {
        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        String currentRank = playersData.getRank(player);
        int prestigeLevel = playersData.getPrestige(player);

        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        ConfigurationSection nextRankSection = getNextRankSection(ranksConfig, currentRank);
        if (nextRankSection == null) {
            sendMessage(player, messagesConfig, "chat.max-rank", nextRankSection, prestigeLevel);
            return;
        }

        if (!hasMoneyForRankUp(player, nextRankSection, prestigeLevel, messagesConfig)) {
            return;
        }

        if (!hasFlakesForRankup(player, nextRankSection, prestigeLevel, messagesConfig)) {
            return;
        }

        applyRankUp(player, playersData, nextRankSection, messagesConfig, prestigeLevel);
    }

    private static ConfigurationSection getNextRankSection(YamlConfiguration ranksConfig, String currentRank) {
        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        assert currentRankSection != null;
        return RanksUtils.getNextRankSection(ranksConfig, currentRankSection);
    }

    private static boolean hasMoneyForRankUp(Player player, ConfigurationSection nextRankSection, int prestigeLevel, YamlConfiguration messagesConfig) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        double rankUpPrice = getRankUpCoinsPrice(nextRankSection, prestigeLevel);

        if (currenciesAPI.getBalance(player, Currencies.COINS) < rankUpPrice) {
            sendMessage(player, messagesConfig, "chat.no-money-for-rank-up", nextRankSection, prestigeLevel);
            return false;
        }

        currenciesAPI.removeBalance(player, Currencies.COINS , rankUpPrice);
        return true;
    }

    private static boolean hasFlakesForRankup(Player player, ConfigurationSection nextRankSection, int prestigeLevel, YamlConfiguration messagesConfig) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();

        double rankUpPrice = getRankUpFlakesPrice(nextRankSection, prestigeLevel);

        if (currenciesAPI.getBalance(player, Currencies.FLAKES) < rankUpPrice) {
            sendMessage(player, messagesConfig, "chat.no-flakes-for-rank-up", nextRankSection, prestigeLevel);
            return false;
        }

        currenciesAPI.removeBalance(player, Currencies.FLAKES , rankUpPrice);
        return true;
    }

    private static double getRankUpCoinsPrice(ConfigurationSection nextRankSection, int prestigeLevel) {
        return nextRankSection.getDouble("coinsPrice") * PrestigeUtils.prestigeCoinsCostAdd(prestigeLevel);
    }

    private static double getRankUpFlakesPrice(ConfigurationSection nextRankSection, int prestigeLevel) {
        return nextRankSection.getDouble("flakesPrice") * PrestigeUtils.prestigeFlakesCostAdd(prestigeLevel);
    }

    private static void applyRankUp(Player player, PlayersCacheMethod playersData, ConfigurationSection nextRankSection, YamlConfiguration messagesConfig, int prestigeLevel) {
        String nextRankName = nextRankSection.getString("name");
        playersData.setRank(player, nextRankName);

        executeRankUpCommand(player, nextRankSection);
        sendMessage(player, messagesConfig, "chat.rank-up", nextRankSection, prestigeLevel);
        assert nextRankName != null;
        player.sendMessage(nextRankName);
    }

    private static void executeRankUpCommand(Player player, ConfigurationSection nextRankSection) {
        String rankUpCommand = nextRankSection.getString("rankup.command");
        if (rankUpCommand != null && !rankUpCommand.isEmpty()) {
            player.performCommand(rankUpCommand);
        }
    }

    private static void sendMessage(Player player, YamlConfiguration messagesConfig, String path, ConfigurationSection nextRankSection, int prestigeLevel) {
        String message = messagesConfig.getString(path);
        if (message != null) {
            player.sendMessage(message.replace("{money}"
                    , String.valueOf(missingMoney(player, nextRankSection, prestigeLevel)))
                    .replace("{flakes}", String.valueOf(missingFlakes(player, nextRankSection, prestigeLevel))));
        }
    }

    private static String missingMoney(Player player, ConfigurationSection nextRankSection, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        Double balance = currenciesAPI.getBalance(player, Currencies.COINS);

        return NumberFormat.formatNumber(getRankUpCoinsPrice(nextRankSection, prestigeLevel) - balance);
    }

    private static String missingFlakes(Player player, ConfigurationSection nextRankSection, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        Double balance = currenciesAPI.getBalance(player, Currencies.FLAKES);

        return NumberFormat.formatNumber(getRankUpFlakesPrice(nextRankSection, prestigeLevel) - balance) ;
    }
}
