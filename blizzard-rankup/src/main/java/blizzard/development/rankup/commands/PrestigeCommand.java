package blizzard.development.rankup.commands;

import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

@CommandAlias("prestigio")
public class PrestigeCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        PlayersData playersData = PlayersCacheManager.getPlayerData(player);
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        YamlConfiguration prestigeConfig = PluginImpl.getInstance().Prestige.getConfig();
        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();

        if (!hasRankForPrestige(playersData.getRank(), ranksConfig, prestigeConfig)) {
            sendMessage(player, messagesConfig, "chat.no-rank-for-prestige");
            return;
        }

        if (playersData.getPrestige() >= 10) {
            sendMessage(player, messagesConfig, "chat.max-prestige");
            return;
        }

        double prestigePrice = PrestigeUtils.prestigePrice(playersData.getPrestige());
        if (!hasCoinsForPrestige(player, prestigePrice)) {
            sendMessage(player, messagesConfig, "chat.no-money-for-prestige");
            return;
        }

        onPrestige(player, playersData, ranksConfig, messagesConfig);
    }

    private boolean hasRankForPrestige(String currentRank, YamlConfiguration ranksConfig, YamlConfiguration prestigeConfig) {
        int currentRankOrder = RanksUtils.getCurrentOrder(ranksConfig, currentRank);
        int requiredRankOrder = prestigeConfig.getInt("prestige.need-rank");
        return currentRankOrder >= requiredRankOrder;
    }

    private boolean hasCoinsForPrestige(Player player, double requiredCoins) {
//        return CoinsMethods.getCoins(player) >= requiredCoins;
        return true;
    }

    private void onPrestige(Player player, PlayersData playersData, YamlConfiguration ranksConfig, YamlConfiguration messagesConfig) {
        Set<String> ranks = Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks")).getKeys(false);
        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
            if (rankSection != null && rankSection.getInt("order") == 1) {
                playersData.setRank(rankSection.getString("name"));
                playersData.setPrestige(playersData.getPrestige() + 1);

                sendMessage(player, messagesConfig, "chat.prestige");
                player.sendMessage("§7[DEBUG] §fSeu prestígio: " + playersData.getPrestige());
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
