package blizzard.development.rankup.commands;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import java.util.Objects;
import java.util.Set;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
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

        int prestige = PlayersCacheMethod.getInstance().getPrestige(player);

        if (!hasRankForPrestige(playersData.getRank(player), ranksConfig, prestigeConfig)) {
            sendMessage(player, messagesConfig, "chat.no-rank-for-prestige", prestige);
            return;
        }

        if (playersData.getPrestige(player) >= prestigeConfig.getInt("prestige.max")) {
            sendMessage(player, messagesConfig, "chat.max-prestige", prestige);
            return;
        }

        double prestigeCoinsPrice = PrestigeUtils.getPrestigeCoinsPrice(playersData.getPrestige(player));
        double prestigeFlakesPrice = PrestigeUtils.getPrestigeFlakesPrice(playersData.getPrestige(player));

        if (!hasCoinsForPrestige(player, prestigeCoinsPrice)) {
            sendMessage(player, messagesConfig, "chat.no-money-for-prestige", prestige);
            return;
        }

        if (!hasFlakesForPrestige(player, prestigeFlakesPrice)) {
            sendMessage(player, messagesConfig, "chat.no-flakes-for-prestige", prestige);
            return;
        }

        onPrestige(player, playersData, ranksConfig, messagesConfig, prestige);
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

    private void onPrestige(Player player, PlayersCacheMethod playersData, YamlConfiguration ranksConfig, YamlConfiguration messagesConfig, int prestige) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);
        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
            if (rankSection != null && rankSection.getInt("order") == 1) {
                playersData.setRank(player, rankSection.getString("name"));
                playersData.setPrestige(player, playersData.getPrestige(player) + 1);

                sendMessage(player, messagesConfig, "chat.prestige", prestige);
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    YamlConfiguration prestigeConfig = PluginImpl.getInstance().Prestige.getConfig();
                    player1.sendMessage(prestigeConfig.getString("prestige.messages.chat"));
                    player1.sendActionBar(prestigeConfig.getString("prestige.messages.actionbar"));
                    player1.sendTitle(prestigeConfig.getString("prestige.messages.title"), prestigeConfig.getString("prestige.messages.subtitle"));
                }
                break;
            }
        }
    }

    private void sendMessage(Player player, YamlConfiguration config, String path, int prestige) {
        String message = config.getString(path);
        if (message != null) {
            player.sendMessage(message.replace(
                    "{flakes}", String.valueOf(PrestigeUtils.getMissingPrestigeFlakes(player, prestige)))
                    .replace("{money}", String.valueOf(PrestigeUtils.getMissingPrestigeMoney(player, prestige))));
        }
    }
}
