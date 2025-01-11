package blizzard.development.core.placeholder;

import blizzard.development.core.Main;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.utils.NumberFormat;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderRegistry extends PlaceholderExpansion {

    private final Main plugin;

    public PlaceholderRegistry(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core";
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

        PlayersCacheManager playersData = PlayersCacheManager.getInstance();

        return switch (identifier) {
            case "temperature" -> NumberFormat.getInstance().formatNumber(playersData.getTemperature(player));
            default -> null;
        };

    }
}
