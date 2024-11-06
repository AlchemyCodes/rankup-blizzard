package blizzard.development.fishing.database.cache.methods;

import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class PlayersCacheMethod {

    public static int getBacalhau(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getBacalhau() : 0;
    }

    public static int getSalmao(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getSalmao() : 0;
    }

    public static int getCaranguejo(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getCaranguejo() : 0;
    }

    public static int getLagosta(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getLagosta() : 0;
    }

    public static int getLula(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getLula() : 0;
    }

    public static int getLulaBrilhante(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getLula_brilhante() : 0;
    }

    public static int getTubarao(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getTubarao() : 0;
    }

    public static int getBaleia(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getBaleia() : 0;
    }

    public static int getFishAmount(Player player, String fishType) {
        return switch (fishType.toLowerCase()) {
            case "bacalhau" -> getBacalhau(player);
            case "salmao" -> getSalmao(player);
            case "caranguejo" -> getCaranguejo(player);
            case "lagosta" -> getLagosta(player);
            case "lula" -> getLula(player);
            case "lula_brilhante" -> getLulaBrilhante(player);
            case "tubarao" -> getTubarao(player);
            case "baleia" -> getBaleia(player);
            default -> 0;
        };
    }

    public static int getStorage(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getStorage() : 0;
    }

    public static int getTrash(Player player) {
        PlayersData data = PlayersCacheManager.getPlayerData(player);
        return (data != null) ? data.getTrash() : 0;
    }
}
