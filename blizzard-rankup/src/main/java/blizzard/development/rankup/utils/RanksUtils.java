package blizzard.development.rankup.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;
import java.util.Set;

public class RanksUtils {

    public static ConfigurationSection getCurrentRankSection(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks")).getKeys(false);

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);

            assert rankSection != null;
            String rankName = rankSection.getString("name");

            if (Objects.equals(rankName, currentRank)) {
                return rankSection;
            }
        }
        return null;
    }

    public static String getCurrentRank(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks")).getKeys(false);

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);

            assert rankSection != null;
            String rankName = rankSection.getString("name");

            if (Objects.equals(rankName, currentRank)) {
                return rankSection.getString("name");
            }
        }
        return null;
    }

    public static ConfigurationSection getNextRankSection(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
        Set<String> ranks = Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks")).getKeys(false);

        int currentOrder = currentRankSection.getInt("order");

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);

            assert rankSection != null;
            int rankOrder = rankSection.getInt("order");

            if (rankOrder == currentOrder + 1) {
                return rankSection;
            }
        }
        return null;
    }

    public static String getNextRank(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
        Set<String> ranks = Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks")).getKeys(false);

        int currentOrder = currentRankSection.getInt("order");

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);

            assert rankSection != null;
            int rankOrder = rankSection.getInt("order");

            if (rankOrder == currentOrder + 1) {
                return rankSection.getString("name");
            }
        }
        return null;
    }

    public static int getCurrentOrder(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks")).getKeys(false);

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);

            assert rankSection != null;
            String rankName = rankSection.getString("name");

            if (Objects.equals(rankName, currentRank)) {
                return rankSection.getInt("order");
            }
        }

        return 0;

    }

    public static String getRankWithMinOrder() {
        ConfigurationSection ranks = PluginImpl.getInstance().Ranks.getConfig().getConfigurationSection("ranks");
        String minRankName = null;

        assert ranks != null;
        for (String key : ranks.getKeys(false)) {
            int order = ranks.getInt(key + ".order");

            if (order == 1) {
                minRankName = ranks.getString(key + ".name");
            }
        }

        return minRankName;
    }
}
