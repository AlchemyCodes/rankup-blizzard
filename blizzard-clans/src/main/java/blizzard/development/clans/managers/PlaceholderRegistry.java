package blizzard.development.clans.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import blizzard.development.clans.Main;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.methods.ClansMethods;

public class PlaceholderRegistry extends PlaceholderExpansion {

    private final Main plugin;

    public PlaceholderRegistry(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "clans";
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

        String clan = ClansMethods.getUserClan(player);

        if (identifier.equals("tag")) {
            if (clan == null) {
                return "Nenhum";
            } else {
                return ClansMethods.getClan(clan).getTag();
            }
        } else if (identifier.equals("name")) {
            if (clan == null) {
                return "Nenhum";
            } else {
                return ClansMethods.getClan(clan).getName();
            }
        } else if (identifier.equals("members")) {
            if (clan == null) {
                return String.valueOf(0);
            } else {
                return String.valueOf(ClansCacheManager.getMembersCount(clan));
            }
        } else if (identifier.equals("maxmembers")) {
            if (clan == null) {
                return String.valueOf(0);
            } else {
                return String.valueOf(ClansCacheManager.getMaxClanMembers(clan));
            }
        } else if (identifier.equals("kdr")) {
            if (clan == null) {
                return String.valueOf(0);
            } else {
                return String.valueOf(ClansCacheManager.getKdr(clan));
            }
        } else if (identifier.equals("has")) {
            return String.valueOf(clan != null);
        }
        return null;
    }

}
