package blizzard.development.plantations.database.cache.methods;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.storage.PlayerData;
import blizzard.development.plantations.plantations.enums.PlantationEnum;
import org.bukkit.entity.Player;

public class PlayerCacheMethod {

    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();
    private static PlayerCacheMethod instance;

    public Integer getArea(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getArea() : 0;
    }

    public void setArea(Player player, int radius) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setArea(radius);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public String getAreaPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getAreaPlantation() : "§c§lERRO!";
    }

    public void setAreaPlantation(Player player, PlantationEnum plantationEnum) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setAreaPlantation(plantationEnum);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public Integer getBlocks(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getBlocks() : 0;
    }

    public void setBlocks(Player player, int amount) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setBlocks(amount);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public Integer getPlantations(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getPlantations() : 0;
    }

    public void setPlantations(Player player, int amount) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setPlantations(amount);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public void setInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setInPlantation(true);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public void removeInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setInPlantation(false);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public boolean isInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null && playerData.getIsInPlantation();
    }

    public static PlayerCacheMethod getInstance() {
        if (instance == null) {
            instance = new PlayerCacheMethod();
        }
        return instance;
    }
}
