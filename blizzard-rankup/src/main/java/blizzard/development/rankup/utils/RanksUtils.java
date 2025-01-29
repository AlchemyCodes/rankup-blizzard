package blizzard.development.rankup.utils;

import java.util.Objects;
import java.util.Set;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RanksUtils {
    public static ConfigurationSection getCurrentRankSection(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

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

    public static String getCurrentRankName(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

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

    public static String getCurrentRankTag(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
            assert rankSection != null;
            String rankName = rankSection.getString("name");

            if (Objects.equals(rankName, currentRank)) {
                return rankSection.getString("tag");
            }
        }
        return "NULL";
    }

    public static String getNextRankTag(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

        int currentOrder = currentRankSection.getInt("order");

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
            assert rankSection != null;
            int rankOrder = rankSection.getInt("order");

            if (rankOrder == currentOrder + 1) {
                return rankSection.getString("tag");
            }
        }
        return null;
    }

    public static ConfigurationSection getNextRankSection(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

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

    public static String getNextRankName(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

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

    public static String getNextRank(YamlConfiguration ranksConfig, ConfigurationSection currentRankSection) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

        int currentOrder = currentRankSection.getInt("order");

        for (String rankKey : ranks) {
            ConfigurationSection rankSection = ranksConfig.getConfigurationSection("ranks." + rankKey);
            assert rankSection != null;
            int rankOrder = rankSection.getInt("order");

            if (rankOrder == currentOrder + 1) {
                return rankKey;
            }
        }
        return null;
    }

    public static int getCurrentOrder(YamlConfiguration ranksConfig, String currentRank) {
        Set<String> ranks = (Objects.requireNonNull(ranksConfig.getConfigurationSection("ranks"))).getKeys(false);

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
        ConfigurationSection ranks = (PluginImpl.getInstance()).Ranks.getConfig().getConfigurationSection("ranks");
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

    public static double getRankUpCoinsPrice(ConfigurationSection nextRankSection, int prestigeLevel) {
        return nextRankSection.getDouble("coinsPrice") * PrestigeUtils.prestigeCoinsCostAdd(prestigeLevel);
    }

    public static double getRankUpFlakesPrice(ConfigurationSection nextRankSection, int prestigeLevel) {
        return nextRankSection.getDouble("flakesPrice") * PrestigeUtils.prestigeFlakesCostAdd(prestigeLevel);
    }

    public static double missingRankMoney(Player player, ConfigurationSection nextRankSection, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        Double balance = currenciesAPI.getBalance(player, Currencies.COINS);

        return getRankUpCoinsPrice(nextRankSection, prestigeLevel) - balance;
    }

    public static double missingRankFlakes(Player player, ConfigurationSection nextRankSection, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        Double balance = currenciesAPI.getBalance(player, Currencies.FLAKES);

        return getRankUpFlakesPrice(nextRankSection, prestigeLevel) - balance;
    }
}
