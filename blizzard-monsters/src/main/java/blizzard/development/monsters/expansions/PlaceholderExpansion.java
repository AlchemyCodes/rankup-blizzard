package blizzard.development.monsters.expansions;

import blizzard.development.monsters.database.cache.methods.PlayersCacheMethods;
import blizzard.development.monsters.utils.NumberFormatter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "monsters";
    }

    @Override
    public @NotNull String getAuthor() {
        return "LucwsH";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
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

        PlayersCacheMethods cacheMethods = PlayersCacheMethods.getInstance();

        int limit = cacheMethods.getMonstersLimit(player);
        int killedMonsters = cacheMethods.getKilledMonsters(player);

        return switch (identifier) {
            case "limit" -> NumberFormatter.getInstance().formatNumber(limit);
            case "killed" -> NumberFormatter.getInstance().formatNumber(killedMonsters);
            default -> identifier;
        };
    }
}
