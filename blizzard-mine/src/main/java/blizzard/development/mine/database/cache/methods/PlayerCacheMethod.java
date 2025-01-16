package blizzard.development.mine.database.cache.methods;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.mine.enums.BlockEnum;
import org.bukkit.entity.Player;

import java.util.List;

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

    public String getAreaBlock(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getAreaBlock() : "§c§lERRO!";
    }

    public void setAreaBlockEnum(Player player, BlockEnum blockEnum) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setAreaBlock(blockEnum);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public void setAreaBlock(Player player, String areaBlock) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setAreaBlock(areaBlock);
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

    public void setInMine(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setInPlantation(true);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public void removeInMine(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setInPlantation(false);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public boolean isInPlantation(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null && playerData.getIsInMine();
    }

    public void addFriend(Player player, String friend) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            List<String> friends = playerData.getFriends();

            friends.add(friend);
            playerData.setFriends(friends);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }
    public List<String> getFriends(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData.getFriends();
    }

    public void removeFriend(Player player, String friend) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            List<String> friends = playerData.getFriends();

            friends.remove(friend);
            playerData.setFriends(friends);
            playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
        }
    }

    public static PlayerCacheMethod getInstance() {
        if (instance == null) {
            instance = new PlayerCacheMethod();
        }
        return instance;
    }
}
