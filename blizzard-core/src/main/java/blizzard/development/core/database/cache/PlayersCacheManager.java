package blizzard.development.core.database.cache;

import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.database.storage.PlayersData;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

public class PlayersCacheManager {

    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<String, PlayersData> playersCache = new ConcurrentHashMap<>();
    private final PlayersDAO playersDAO = new PlayersDAO();

    public PlayersData getPlayerData(Player player) {
        return getPlayerDataByName(player.getName());
    }

    public void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player.getName(), playerData);
    }

    public void cachePlayerDataByName(String player, PlayersData playerData) {
        playersCache.put(player, playerData);
    }

    public PlayersData getPlayerDataByName(String playerName) {
        PlayersData data = playersCache.get(playerName);

        if (data == null) {
            data = playersDAO.findPlayerDataByName(playerName);
            if (data != null) {
                playersCache.put(playerName, data);
            }
        }

        return data;
    }

    public void setTemperature(Player player, double temperature) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            data.setTemperature(temperature);
            playersCache.put(player.getName(), data);
        }
    }

    public double getTemperature(Player player) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            return data.getTemperature();
        }
        return 0.0D;
    }

    public void setPlayerClothing(Player player, ClothingType clothingType) {
        PlayersData data = getPlayerData(player);

        if (data != null) {
            data.setClothingType(clothingType);
            playersCache.put(player.getName(), data);
        }
    }

    public String getPlayerClothing(Player player) {
        PlayersData data = getPlayerData(player);

        if (data != null) {
            return data.getClothingType().toString();
        }

        return "Inválido";
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) {
            instance = new PlayersCacheManager();
        }
        return instance;
    }
}
