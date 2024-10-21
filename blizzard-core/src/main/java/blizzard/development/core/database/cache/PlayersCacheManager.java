package blizzard.development.core.database.cache;

import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.database.storage.PlayersData;
import blizzard.development.core.clothing.ClothingType;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    public static final ConcurrentHashMap<String, PlayersData> playersCache = new ConcurrentHashMap<>();
    private static final PlayersDAO playersDAO = new PlayersDAO();

    public static PlayersData getPlayerData(Player player) {
        return getPlayerDataByName(player.getName());
    }

    public static void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player.getName(), playerData);
    }

    public static void cachePlayerDataByName(String player, PlayersData playerData) {
        playersCache.put(player, playerData);
    }

    public static PlayersData getPlayerDataByName(String playerName) {
        PlayersData data = playersCache.get(playerName);

        if (data == null) {
            data = playersDAO.findPlayerDataByName(playerName);
            if (data != null) {
                playersCache.put(playerName, data);
            }
        }

        return data;
    }

    public static void setTemperature(Player player, double temperature) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            data.setTemperature(temperature);
            playersCache.put(player.getName(), data);

        }
    }

    public static double getTemperature(Player player) {
        PlayersData data = getPlayerData(player);
        if (data != null) {
            return data.getTemperature();
        }
        return 0;
    }

    public static void setPlayerClothing(Player player, ClothingType clothingType) {
        PlayersData data = getPlayerData(player);

        if (data != null) {
            data.setClothingType(clothingType);
            playersCache.put(player.getName(), data);
        }
    }

    public static String getPlayerClothing(Player player) {
        PlayersData data = getPlayerData(player);

        if (data != null) {
            return data.getClothingType().toString();
        }

        return "Inválido";
    }




}