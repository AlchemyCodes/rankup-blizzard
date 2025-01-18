package blizzard.development.mine.database.cache.methods;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.mine.enums.BlockEnum;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerCacheMethods {

    private static final PlayerCacheMethods instance = new PlayerCacheMethods();

    public static PlayerCacheMethods getInstance() {
        return instance;
    }

    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();

    public Integer getArea(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getArea() : 0;
    }

    public void setArea(Player player, int radius) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setArea(radius);
            playerCacheManager.cachePlayerData(player, playerData);
        }
    }

    public String getAreaBlock(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null ? playerData.getAreaBlock() : "§c§lERRO!";
    }

    public void setAreaBlock(Player player, BlockEnum areaBlock) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setAreaBlock(areaBlock);
            playerCacheManager.cachePlayerData(player, playerData);
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
            playerCacheManager.cachePlayerData(player, playerData);
        }
    }

    public void setInMine(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setInMine(true);
            playerCacheManager.cachePlayerData(player, playerData);
        }
    }

    public void removeFromMine(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            playerData.setInMine(false);
            playerCacheManager.cachePlayerData(player, playerData);
        }
    }

    public boolean isInMine(Player player) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        return playerData != null && playerData.getIsInMine();
    }

    public void addFriend(Player player, String friend) {
        PlayerData playerData = playerCacheManager.getPlayerData(player);
        if (playerData != null) {
            List<String> friends = playerData.getFriends();

            friends.add(friend);
            playerData.setFriends(friends);
            playerCacheManager.cachePlayerData(player, playerData);
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
            playerCacheManager.cachePlayerData(player, playerData);
        }
    }

    public List<PlayerData> getTopBlocks(int topCount) {
        return playerCacheManager.playerCache.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getBlocks(), p1.getBlocks()))
                .limit(topCount)
                .collect(Collectors.toList());
    }
}
