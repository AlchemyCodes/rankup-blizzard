package blizzard.development.monsters.database.cache.methods;

import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersCacheMethods {
    private static PlayersCacheMethods instance;

    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public Integer getKilledMonsters(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            return data.getKilledMonsters();
        }
        return 0;
    }

    public void addKilledMonsters(Player player, int amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setKilledMonsters(data.getKilledMonsters() + amount);
            cache.cachePlayerData(player, data);
        }
    }

    public Integer getMonstersLimit(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            return data.getMonstersLimit();
        }
        return 0;
    }

    public void addMonstersLimit(Player player, int amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setMonstersLimit(data.getMonstersLimit() + amount);
            cache.cachePlayerData(player, data);
        }
    }

    public void removeMonstersLimit(Player player, int amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setMonstersLimit(data.getMonstersLimit() - amount);
            cache.cachePlayerData(player, data);
        }
    }

    public void addRewards(Player player, List<String> reward) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> rewards = data.getRewards();
            rewards.addAll(reward);
            data.setRewards(rewards);
            cache.cachePlayerData(player, data);
        }
    }

    public void removeRewards(Player player, List<String> reward) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> rewards = data.getRewards();
            rewards.removeAll(reward);
            data.setRewards(rewards);
            cache.cachePlayerData(player, data);
        }
    }

    public void removeReward(Player player, String reward) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            List<String> rewards = data.getRewards();
            rewards.remove(reward);
            data.setRewards(rewards);
            cache.cachePlayerData(player, data);
        }
    }

    public List<PlayersData> getTopKilledMonsters() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getKilledMonsters(), p1.getKilledMonsters()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<PlayersData> getTopMonstersLimit() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getMonstersLimit(), p1.getKilledMonsters()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static PlayersCacheMethods getInstance() {
        if (instance == null) instance = new PlayersCacheMethods();
        return instance;
    }
}
