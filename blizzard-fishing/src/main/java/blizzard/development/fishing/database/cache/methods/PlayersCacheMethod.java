package blizzard.development.fishing.database.cache.methods;

import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class PlayersCacheMethod {
    private static PlayersCacheMethod instance;
    
    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public int getBacalhau(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getBacalhau() : 0;
    }

    public void setBacalhau(Player player, int amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setBacalhau(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getSalmao(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getSalmao() : 0;
    }

    public void setSalmao(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setSalmao(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getCaranguejo(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getCaranguejo() : 0;
    }

    public void setCaranguejo(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setCaranguejo(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getLagosta(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getLagosta() : 0;
    }

    public void setLagosta(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setLagosta(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getLula(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getLula() : 0;
    }

    public void setLula(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setLula(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getLulaBrilhante(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getLula_brilhante() : 0;
    }

    public void setLulaBrilhante(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setLula_brilhante(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getTubarao(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getTubarao() : 0;
    }

    public void setTubarao(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setTubarao(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getBaleia(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getBaleia() : 0;
    }

    public void setBaleia(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setBaleia(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getFrozenFish(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getFrozen_fish() : 0;
    }

    public void setFrozenFish(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setFrozen_fish(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getStorage(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getStorage() : 0;
    }

    public void setStorage(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setStorage(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getTrash(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getTrash() : 0;
    }

    public void setTrash(Player player, int amount) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setTrash(amount);
            cache.cachePlayerData(player, data);
        }
    }

    public int getFishes(Player player) {
        return getBacalhau(player) + getSalmao(player) + getCaranguejo(player) + getLagosta(player)
                + getLula(player) + getLulaBrilhante(player) + getTubarao(player) + getBaleia(player);
    }

    public int getFishAmount(Player player, String fishType) {
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

    public void setFishAmount(Player player, String fishType, int amount) {
         switch (fishType.toLowerCase()) {
            case "bacalhau" -> setBacalhau(player, amount);
            case "salmao" -> setSalmao(player, amount);
            case "caranguejo" -> setCaranguejo(player, amount);
            case "lagosta" -> setLagosta(player, amount);
            case "lula" -> setLula(player, amount);
            case "lula_brilhante" -> setLulaBrilhante(player, amount);
            case "tubarao" -> setTubarao(player, amount);
            case "baleia" -> setBaleia(player, amount);
        };
    }

    public static PlayersCacheMethod getInstance() {
        if (instance == null) {
            instance = new PlayersCacheMethod();
        }
        return instance;
    }


}
