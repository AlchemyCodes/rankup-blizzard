package blizzard.development.fishing.placeholder;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.fishing.Main;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.NumberFormat;
import blizzard.development.fishing.utils.fish.FishesUtils;
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
        return "fishing";
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

        PlayersCacheMethod playersCacheMethod = PlayersCacheMethod.getInstance();
        double xp = RodsCacheMethod.getInstance().getXp(player);


        return switch (identifier) {
            case "bucket_fishes" -> String.valueOf(playersCacheMethod.getFishes(player));
            case "bucket_storage" -> String.valueOf(playersCacheMethod.getStorage(player));
            case "xp" -> NumberFormat.getInstance().formatNumber(xp);
            case "strength" -> String.valueOf(RodsCacheMethod.getInstance().getStrength(player));
            default -> null;
        };
    }
}