package blizzard.development.currencies.extensions;

import blizzard.development.currencies.Main;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.enums.Currencies;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExtension extends PlaceholderExpansion {
    private final Plugin plugin;

    PlayersCacheManager cache = PlayersCacheManager.getInstance();
    CurrenciesAPI api = CurrenciesAPI.getInstance();

    public PlaceholderAPIExtension(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "currencies";
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

        switch (identifier) {
            case "souls" -> {
                if (cache.getPlayerData(player) == null) {
                    return "0";
                }
                return api.getFormattedBalance(player, Currencies.SOULS);
            }
            case "flakes" -> {
                if (cache.getPlayerData(player) == null) {
                    return "0";
                }
                return api.getFormattedBalance(player, Currencies.FLAKES);
            }
            case "fossils" -> {
                if (cache.getPlayerData(player) == null) {
                    return "0";
                }
                return api.getFormattedBalance(player, Currencies.FOSSILS);
            }
            case "blocks" -> {
                if (cache.getPlayerData(player) == null) {
                    return "0";
                }
                return api.getFormattedBalance(player, Currencies.BLOCKS);
            }
        }
        return null;
    }
}
