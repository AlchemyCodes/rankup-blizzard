package blizzard.development.excavation.database.cache.methods;

import blizzard.development.excavation.database.cache.PlayerCacheManager;
import blizzard.development.excavation.database.storage.PlayerData;
import org.bukkit.entity.Player;

import static blizzard.development.excavation.database.cache.PlayerCacheManager.playerCache;

public class PlayerCacheMethod {

    public Integer getBlocks(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerCache.put(player.getUniqueId().toString(), playerData);
            return playerData.getBlocks();
        }

        return 0;
    }

    public void setBlocks(Player player, int amount) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setBlocks(amount);
            playerCache.put(player.getUniqueId().toString(), playerData);
        }
    }

    public void setInExcavation(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setInExcavation(true);
        }
    }

    public void removeInExcavation(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setInExcavation(false);
        }
    }

    public boolean isInExcavation(Player player) {
        PlayerCacheManager playerCacheManager = new PlayerCacheManager();

        PlayerData playerData = playerCacheManager.getPlayerData(player);

        return playerData.getInExcavation();
    }


}
