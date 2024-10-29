package blizzard.development.plantations.database.cache.methods;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.storage.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import static blizzard.development.plantations.database.cache.PlayerCacheManager.playerCache;

public class PlayerCacheMethod {

    public Integer getBlocks(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerCache.put(player.getUniqueId().toString(), playerData);
            return playerData.getPlantations();
        }

        return 0;
    }

    public void setBlocks(Player player, int amount) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setPlantations(amount);
            playerCache.put(player.getUniqueId().toString(), playerData);
        }
    }

    public void setInPlantation(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setInPlantation(true);
        }

    }

    public void removeInPlantation(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setInPlantation(false);
        }

    }

    public boolean isInPlantation(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();
        PlayerData playerData = playerCacheManager.getPlayerData(player);

        return playerData.getIsInPlantation();
    }

}
