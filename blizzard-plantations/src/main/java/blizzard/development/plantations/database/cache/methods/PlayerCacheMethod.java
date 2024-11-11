package blizzard.development.plantations.database.cache.methods;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.storage.PlayerData;
import org.bukkit.entity.Player;

public class PlayerCacheMethod {

    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();

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
}
