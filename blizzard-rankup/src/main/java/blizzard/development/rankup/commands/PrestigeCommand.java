package blizzard.development.rankup.commands;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import java.util.Objects;
import java.util.Set;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("prestigio")
public class PrestigeCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();

        YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();
        YamlConfiguration messagesConfig = (PluginImpl.getInstance()).Messages.getConfig();

        if (!hasRankForPrestige(playersData.getRank(player), ranksConfig, prestigeConfig)) {
            sendMessage(player, messagesConfig, "chat.no-rank-for-prestige");
            return;
        }

        if (playersData.getPrestige(player) >= prestigeConfig.getInt("prestige.max")) {
            sendMessage(player, messagesConfig, "chat.max-prestige");
            return;
        }

        double prestigeCoinsPrice = PrestigeUtils.prestigeCoinsPrice(playersData.getPrestige(player));
        double prestigeFlakesPrice = PrestigeUtils.prestigeFlakesPrice(playersData.getPrestige(player));

        if (!hasCoinsForPrestige(player, prestigeCoinsPrice)) {
            sendMessage(player, messagesConfig, "chat.no-money-for-prestige");
            return;
        }

        if (!hasFlakesForPrestige(player, prestigeFlakesPrice)) {
            sendMessage(player, messagesConfig, "chat.no-flakes-for-prestige");
            return;
        }

        onPrestige(player, playersData, ranksConfig, messagesConfig);
    }

    private boolean hasRankForPrestige(String currentRank, YamlConfiguration ranksConfig, YamlConfiguration prestigeConfig) {
        int currentRankOrder = RanksUtils.getCurrentOrder(ranksConfig, currentRank);
        int requiredRankOrder = prestigeConfig.getInt("prestige.need-rank");
        return (currentRankOrder >= requiredRankOrder);
    }

    private boolean hasCoinsForPrestige(Player player, double requiredCoins) {
        return CurrenciesAPI.getInstance().getBalance(player, Currencies.COINS) >= requiredCoins;
    }

    private boolean hasFlakesForPrestige(Player player, double requiredCoins) {
        return CurrenciesAPI.getInstance().getBalance(player, Currencies.FLAKES) >= requiredCoins;
    }

    private void onPrestige(Player player, PlayersCacheMethod playersData, YamlConfiguration ranksConfig, YamlConfiguration messagesConfig) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
            if (rankSection != null && rankSection.getInt("order") == 1) {
                playersData.setRank(player, rankSection.getString("name"));
                playersData.setPrestige(player, playersData.getPrestige(player) + 1);

                sendMessage(player, messagesConfig, "chat.prestige");
                player.sendMessage("§7[DEBUG] §fSeu prestígio: " + playersData.getPrestige(player));
                break;
            }
        }
    }

    private void sendMessage(Player player, YamlConfiguration config, String path) {
        String message = config.getString(path);
        if (message != null) {
            player.sendMessage(message);
        }
    }
}
