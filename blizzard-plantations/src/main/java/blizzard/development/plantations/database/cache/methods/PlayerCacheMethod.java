package blizzard.development.plantations.database.cache.methods;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.storage.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCacheMethod {

    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();

    public Integer getPlantations(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            return playerData.getPlantations();
        }
        return 0;
    }

    public void setPlantations(Player player, int amount) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setPlantations(amount);
            playerCacheManager.playerCache.put(player.getName(), playerData);
            Bukkit.getConsoleSender().sendMessage("§a[Debug] Plantations atualizadas para " + amount + " para o jogador " + player.getName());
        }
    }

    public void setInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setInPlantation(true);
            playerCacheManager.playerCache.put(player.getName(), playerData);
        }
    }

    public void removeInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);

        if (playerData != null) {
            playerData.setInPlantation(false);
            playerCacheManager.playerCache.put(player.getName(), playerData);
        }
    }

    public boolean isInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData.getIsInPlantation();
    }
}
