package blizzard.development.fishing.database.cache;

import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.storage.PlayersData;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    public static final ConcurrentHashMap<Player, PlayersData> playerCache = new ConcurrentHashMap<>();
    public static PlayersDAO playersDAO;

    public static void cachePlayerData(Player player, PlayersData playerData) {
        playerCache.put(player, playerData);
    }

    public static PlayersData getPlayerData(Player player) {
        return playerCache.get(player);
    }

    public static void removePlayerData(Player player) {
        playerCache.remove(player);
    }

    public static Collection<PlayersData> getAllPlayersData() {
        return playerCache.values();
    }
}
