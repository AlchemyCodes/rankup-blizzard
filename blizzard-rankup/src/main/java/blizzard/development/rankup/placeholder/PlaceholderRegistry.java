package blizzard.development.rankup.placeholder;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.rankup.Main;
import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.RanksUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderRegistry extends PlaceholderExpansion {

    private final Main plugin;

    public PlaceholderRegistry(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rankup";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        YamlConfiguration config = PluginImpl.getInstance().Ranks.getConfig();

        String rank = playersData.getRank(player);
        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(config, rank);

        assert currentRankSection != null;
        String nextRank = RanksUtils.getNextRank(config, currentRankSection);

        if (nextRank == null) {
            return "";
        }

        double currentCoins = CurrenciesAPI.getInstance().getBalance(player, Currencies.COINS);
        double currentFlakes = CurrenciesAPI.getInstance().getBalance(player, Currencies.FLAKES);

        int coinsPrice = config.getInt("ranks." + nextRank + ".coinsPrice");
        int flakesPrice = config.getInt("ranks." + nextRank + ".flakesPrice");

        double coinsProgress = coinsPrice > 0 ? Math.min(currentCoins / coinsPrice, 1.0) : 1.0;
        double flakesProgress = flakesPrice > 0 ? Math.min(currentFlakes / flakesPrice, 1.0) : 1.0;

        double totalProgress = (coinsProgress + flakesProgress) / 2.0;
        int percentage = (int) (totalProgress * 100);

        return switch (identifier) {
            case "percentage" -> percentage + "%";
            case "progressbar" -> createProgressBar(percentage, 5);
            case "rank_name" -> rank;
            case "rank_tag" -> RanksUtils.getCurrentRankTag(config, rank);
            default -> null;
        };

    }

    private String createProgressBar(int percentage, int segments) {
        int progressSegments = (int) (segments * (percentage / 100.0));
        int remainingSegments = segments - progressSegments;

        return "§a" +
                "■".repeat(progressSegments) +
                "§7" +
                "■".repeat(remainingSegments);
    }

}
